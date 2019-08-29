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

package com.liferay.segments.experiment.web.internal.notifications;

import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
	service = UserNotificationDefinition.class
)
public class SegmentsExperimentUpdateStatusUserNotificationDefinition
	extends UserNotificationDefinition {

	public SegmentsExperimentUpdateStatusUserNotificationDefinition() {
		super(
			SegmentsPortletKeys.SEGMENTS_EXPERIMENT, 0,
			SegmentsExperimentConstants.NOTIFICATION_TYPE_UPDATE_STATUS,
			"receive-a-notification-when-someone-changes-the-status-of-your-" +
				"ab-tests");

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"email", UserNotificationDeliveryConstants.TYPE_EMAIL, true,
				true));
		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				true));
	}

}