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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.web.internal.portlet.util.FragmentsImporter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.File;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/import"
	},
	service = MVCActionCommand.class
)
public class ImportMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String successMessage = LanguageUtil.get(
			_portal.getHttpServletRequest(actionRequest),
			"the-files-were-imported-correctly");

		SessionMessages.add(actionRequest, "requestProcessed", successMessage);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		long fragmentCollectionId = ParamUtil.getLong(
			uploadPortletRequest, "fragmentCollectionId");

		boolean overwrite = ParamUtil.getBoolean(
			actionRequest, "overwrite", true);

		File file = uploadPortletRequest.getFile("file");

		try {
			importFile(actionRequest, file, fragmentCollectionId, overwrite);

			SessionMessages.add(actionRequest, "success");
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass(), e);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	protected void importFile(
			ActionRequest actionRequest, File file, long fragmentCollectionId,
			boolean overwrite)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		long userId = serviceContext.getUserId();

		List<String> invalidFragmentEntriesNames =
			_fragmentsImporter.importFile(
				userId, serviceContext.getScopeGroupId(), fragmentCollectionId,
				file, overwrite);

		if (ListUtil.isNotEmpty(invalidFragmentEntriesNames)) {
			SessionMessages.add(
				actionRequest, "invalidFragmentEntriesNames",
				invalidFragmentEntriesNames);
		}
	}

	@Reference
	private FragmentsImporter _fragmentsImporter;

	@Reference
	private Portal _portal;

}