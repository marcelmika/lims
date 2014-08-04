/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class SettingsFinderUtil {
	public static java.util.List<java.lang.Object[]> findAllGroups(
		java.lang.Long userId, boolean ignoreDefaultUser, int start, int end)
		throws java.lang.Exception {
		return getFinder().findAllGroups(userId, ignoreDefaultUser, start, end);
	}

	public static java.util.List<java.lang.Object[]> findSitesGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		java.lang.String[] excludedSties, int start, int end)
		throws java.lang.Exception {
		return getFinder()
				   .findSitesGroups(userId, ignoreDefaultUser, excludedSties,
			start, end);
	}

	public static SettingsFinder getFinder() {
		if (_finder == null) {
			_finder = (SettingsFinder)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
					SettingsFinder.class.getName());

			ReferenceRegistry.registerReference(SettingsFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(SettingsFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(SettingsFinderUtil.class, "_finder");
	}

	private static SettingsFinder _finder;
}