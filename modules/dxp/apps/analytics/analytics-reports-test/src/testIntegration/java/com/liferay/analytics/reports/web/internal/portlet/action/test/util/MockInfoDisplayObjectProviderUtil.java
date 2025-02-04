/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.portlet.action.test.util;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Locale;

/**
 * @author Cristina González
 */
public class MockInfoDisplayObjectProviderUtil {

	public static InfoDisplayObjectProvider<Object>
		getInfoDisplayObjectProvider(
			InfoDisplayContributorTracker infoDisplayContributorTracker,
			Portal portal) {

		return new InfoDisplayObjectProvider() {

			@Override
			public long getClassNameId() {
				List<InfoDisplayContributor<?>> infoDisplayContributors =
					infoDisplayContributorTracker.getInfoDisplayContributors();

				InfoDisplayContributor<?> infoDisplayContributor =
					infoDisplayContributors.get(0);

				return portal.getClassNameId(
					infoDisplayContributor.getClassName());
			}

			@Override
			public long getClassPK() {
				return 0;
			}

			@Override
			public long getClassTypeId() {
				return 0;
			}

			@Override
			public String getDescription(Locale locale) {
				return null;
			}

			@Override
			public Object getDisplayObject() {
				return null;
			}

			@Override
			public long getGroupId() {
				return 0;
			}

			@Override
			public String getKeywords(Locale locale) {
				return null;
			}

			@Override
			public String getTitle(Locale locale) {
				return null;
			}

			@Override
			public String getURLTitle(Locale locale) {
				return null;
			}

		};
	}

}