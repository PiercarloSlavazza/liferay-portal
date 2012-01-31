/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.assetpublisher.mbthreadmessage.viewportletmaximumitems5mbcategorymessage6ap;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPortletAPTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletMaximumItems5MBCategoryMessage6APTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAPTest.class);
		testSuite.addTestSuite(AddPortletAPTest.class);
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(AddMBCategoryTest.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage1Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage2Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage3Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage4Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage5Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage6Test.class);
		testSuite.addTestSuite(ViewMBCategoryThreadMessage6APTest.class);
		testSuite.addTestSuite(ConfigurePortletMaximumItemsToDisplay5Test.class);
		testSuite.addTestSuite(ViewPortletMaximumItems5MBCategoryMessage6APTest.class);
		testSuite.addTestSuite(TearDownMBMessageTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}