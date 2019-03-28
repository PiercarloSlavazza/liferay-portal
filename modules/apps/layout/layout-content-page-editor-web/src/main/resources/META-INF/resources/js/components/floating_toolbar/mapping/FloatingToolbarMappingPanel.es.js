import 'clay-dropdown';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import {COMPATIBLE_TYPES} from '../../../utils/constants';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarMappingPanel.soy';
import {openAssetBrowser} from '../../../utils/FragmentsEditorDialogUtils';
import {ADD_MAPPED_ASSET_ENTRY, UPDATE_EDITABLE_VALUE} from '../../../actions/actions.es';

const SOURCE_TYPE_IDS = {
	content: 'specific_content',
	structure: 'structure'
};

/**
 * FloatingToolbarMappingPanel
 */
class FloatingToolbarMappingPanel extends PortletBase {

	/**
	 * @param {!string} subtypeLabel
	 * @return {{id: string, label: string}[]} Source types
	 * @private
	 * @static
	 * @review
	 */
	static _getSourceTypes(subtypeLabel) {
		return [
			{
				id: SOURCE_TYPE_IDS.structure,
				label: Liferay.Util.sub(
					Liferay.Language.get('x-default'),
					subtypeLabel
				)
			},
			{
				id: SOURCE_TYPE_IDS.content,
				label: Liferay.Language.get('specific-content')
			}
		];
	}

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		nextState = setIn(
			nextState,
			['mappedAssetEntries'],
			nextState.mappedAssetEntries.map(encodeAssetId)
		);

		nextState = setIn(
			nextState,
			['_sourceTypeIds'],
			SOURCE_TYPE_IDS
		);

		if (
			nextState.mappingFieldsURL &&
			nextState.selectedMappingTypes &&
			nextState.selectedMappingTypes.type
		) {
			nextState = setIn(
				nextState,
				['_sourceTypes'],
				FloatingToolbarMappingPanel._getSourceTypes(
					nextState.selectedMappingTypes.subtype ?
						nextState.selectedMappingTypes.subtype.label :
						nextState.selectedMappingTypes.type.label
				)
			);
		}

		if (
			nextState.mappedAssetEntries &&
			nextState.item.editableValues.classNameId &&
			nextState.item.editableValues.classPK
		) {
			const mappedAssetEntry = nextState.mappedAssetEntries.find(
				assetEntry => (
					(nextState.item.editableValues.classNameId === assetEntry.classNameId) &&
					(nextState.item.editableValues.classPK === assetEntry.classPK)
				)
			);

			if (mappedAssetEntry) {
				nextState = setIn(
					nextState,
					['item', 'editableValues', 'title'],
					mappedAssetEntry.title
				);

				nextState = setIn(
					nextState,
					['item', 'editableValues', 'encodedId'],
					mappedAssetEntry
				);
			}
		}

		return nextState;
	}

	/**
	 * @inheritdoc
	 * @param {boolean} firstRender
	 * @review
	 */
	rendered(firstRender) {
		if (firstRender) {
			this._selectedSourceTypeId = SOURCE_TYPE_IDS.content;

			if (
				this.item &&
				this.mappingFieldsURL &&
				!this.item.editableValues.classNameId
			) {
				this._selectedSourceTypeId = SOURCE_TYPE_IDS.structure;
			}

			this._loadFields();
		}
	}

	/**
	 * Clears editable values
	 * @private
	 * @review
	 */
	_clearEditableValues() {
		this._updateEditableValues('classNameId', '');
		this._updateEditableValues('classPK', '');
		this._updateEditableValues('fieldId', '');
		this._updateEditableValues('mappedField', '');
	}

	/**
	 * Clears fields
	 * @private
	 * @review
	 */
	_clearFields() {
		this._fields = [];
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleAssetBrowserLinkClick(event) {
		const {assetBrowserUrl, assetBrowserWindowTitle} = event.delegateTarget.dataset;

		openAssetBrowser(
			assetBrowserUrl,
			assetBrowserWindowTitle,
			this.portletNamespace,
			selectedAssetEntry => {
				this._selectAssetEntry(selectedAssetEntry);

				this.store.dispatchAction(
					ADD_MAPPED_ASSET_ENTRY,
					selectedAssetEntry
				);

				requestAnimationFrame(
					() => {
						this.refs.panel.focus();
					}
				);
			}
		);
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleAssetEntryLinkClick(event) {
		const data = event.delegateTarget.dataset;

		this._selectAssetEntry(
			{
				classNameId: data.classNameId,
				classPK: data.classPk
			}
		);

		requestAnimationFrame(
			() => {
				this.refs.panel.focus();
			}
		);
	}

	/**
	 * Handle field option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleFieldOptionChange(event) {
		const fieldId = event.delegateTarget.value;

		if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.content) {
			this._updateEditableValues('fieldId', fieldId);
		}
		else if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.structure) {
			this._updateEditableValues('mappedField', fieldId);
		}
	}

	/**
	 * Handle source option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleSourceTypeChange(event) {
		this._selectedSourceTypeId = event.delegateTarget.value;

		this._clearEditableValues();
		this._loadFields();
	}

	/**
	 * Load the list of fields
	 * @private
	 * @review
	 */
	_loadFields() {
		let promise;

		this._clearFields();

		if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.structure) {
			const data = {
				classNameId: this.selectedMappingTypes.type.id
			};

			if (this.selectedMappingTypes.subtype) {
				data.classTypeId = this.selectedMappingTypes.subtype.id;
			}

			promise = this.fetch(this.mappingFieldsURL, data);
		}
		else if (
			this._selectedSourceTypeId === SOURCE_TYPE_IDS.content &&
			this.item.editableValues.classNameId &&
			this.item.editableValues.classPK
		) {
			promise = this.fetch(
				this.getAssetMappingFieldsURL,
				{
					classNameId: this.item.editableValues.classNameId,
					classPK: this.item.editableValues.classPK
				}
			);
		}

		if (promise) {
			promise
				.then(
					response => response.json()
				)
				.then(
					response => {
						this._fields = response.filter(
							field => COMPATIBLE_TYPES[this.item.type]
								.indexOf(field.type) !== -1
						);
					}
				);
		}
		else if (this._fields.length) {
			this._clearFields();
		}
	}

	/**
	 * @param {object} assetEntry
	 * @param {string} assetEntry.classNameId
	 * @param {string} assetEntry.classPK
	 * @private
	 * @review
	 */
	_selectAssetEntry(assetEntry) {
		this._updateEditableValues('classNameId', assetEntry.classNameId);
		this._updateEditableValues('classPK', assetEntry.classPK);
		this._updateEditableValues('fieldId', '');

		this.store.done(
			() => {
				this._loadFields();
			}
		);
	}

	/**
	 * Dispatches action to update editable value
	 * @param {!string} key
	 * @param {!string} value
	 */
	_updateEditableValues(key, value) {
		this.store
			.dispatchAction(
				UPDATE_EDITABLE_VALUE,
				{
					editableId: this.item.editableId,
					editableValue: value,
					editableValueId: key,
					fragmentEntryLinkId: this.item.fragmentEntryLinkId
				}
			);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarMappingPanel.STATE = {

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!object}
	 */
	item: Config
		.required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required(),

	/**
	 * @default []
	 * @memberOf FloatingToolbarMappingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_fields: Config
		.array()
		.internal()
		.value([]),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	_selectedSourceTypeId: Config
		.oneOf(Object.values(SOURCE_TYPE_IDS))
		.internal()
};

const ConnectedFloatingToolbarMappingPanel = getConnectedComponent(
	FloatingToolbarMappingPanel,
	[
		'assetBrowserLinks',
		'getAssetMappingFieldsURL',
		'mappedAssetEntries',
		'mappingFieldsURL',
		'portletNamespace',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFloatingToolbarMappingPanel, templates);

export {ConnectedFloatingToolbarMappingPanel, FloatingToolbarMappingPanel};
export default ConnectedFloatingToolbarMappingPanel;