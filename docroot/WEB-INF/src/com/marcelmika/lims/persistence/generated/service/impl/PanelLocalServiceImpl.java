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

package com.marcelmika.lims.persistence.generated.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.lims.persistence.generated.model.Panel;
import com.marcelmika.lims.persistence.generated.service.base.PanelLocalServiceBaseImpl;

/**
 * The implementation of the panel local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.service.PanelLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.base.PanelLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.PanelLocalServiceUtil
 */
public class PanelLocalServiceImpl extends PanelLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.persistence.generated.service.PanelLocalServiceUtil} to access the panel local service.
	 */

    /**
     * Returns panel. Creates new if not found. Null on error
     */
    public Panel getPanelByUser(long userId) {
        try {
            Panel panel = panelPersistence.fetchByUserId(userId);
            // No panel found => create a new one
            if (panel == null) {
                long pid = counterLocalService.increment();
                panel = panelPersistence.create(pid);
                panel.setUserId(userId);
                panelPersistence.update(panel, true);
            }
            return panel;

        } catch (SystemException e) {
            // Return null on error
            return null;
        }
    }

    public void updateActivePanel(long userId, String activePanel) throws Exception {
        // Get user settings
        Panel panel = getPanelByUser(userId);

        if (panel != null) {
            panel.setActivePanelId(activePanel);
            panelPersistence.update(panel, true);
        }
    }

}