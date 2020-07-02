/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

CKEDITOR.on('dialogDefinition', (event) => {
	if (event.editor === ckEditor) {
		var boundingWindow = event.editor.window;

		var dialogDefinition = event.data.definition;

		var dialog = event.data.dialog;

		var onShow = dialogDefinition.onShow;

		dialogDefinition.onShow = function() {

			if (typeof onShow === 'function') {
				onShow.apply(this, arguments);
			}

			centerDialog();
		};

		var centerDialog = function () {
			var editorElement = dialog.getParentEditor().container;

			var documentPosition = editorElement
				.getLast()
				.getDocumentPosition();

			var dialogSize = dialog.getSize();

			var x =
				documentPosition.x +
				((editorElement.getLast().getSize('width', true) -
					dialogSize.width) /
					2 -
					window.scrollX);
			var y =
				documentPosition.y +
				((editorElement.getLast().getSize('height', true) -
					dialogSize.height) /
					2 -
					window.scrollY);

			dialog.move(x, y, false);
		}

		AUI().use('aui-debounce', A => {
			boundingWindow.on(
				'resize',
				A.debounce(() => {
					centerDialog();
				}, 250)
			);
		});

		var clearEventHandler = function() {
			Liferay.detach('resize', boundingWindow);
		};

		Liferay.once('destroyPortlet', clearEventHandler);
	}
});
