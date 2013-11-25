/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
package com.marcelmika.lims.service.impl;

import com.marcelmika.lims.model.Settings;
import com.marcelmika.lims.service.base.SettingsLocalServiceBaseImpl;

/**
 * The implementation of the settings local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link com.marcelmika.lims.service.SettingsLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.service.base.SettingsLocalServiceBaseImpl
 * @see com.marcelmika.lims.service.SettingsLocalServiceUtil
 */
public class SettingsLocalServiceImpl extends SettingsLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link SettingsLocalServiceUtil} to access the settings local service.
     */

    public Settings getSettings(long userId) throws Exception {
        // Find user settings
        Settings settings = settingsPersistence.fetchByUserId(userId);

        // No settings found => create a new one
        if (settings == null) {            
            long sid = counterLocalService.increment();
            settings = settingsPersistence.create(sid);
            settings.setUserId(userId);
            settings.setChatEnabled(true);
            settingsPersistence.update(settings, true);            
        }

        return settings;
    }

    public void changeStatus(long userId, String status) throws Exception {
        // Get user settings
        Settings settings = getSettings(userId);
        // Change status
        if (settings != null) {            
            settings.setStatus(status);            
            settingsPersistence.clearCache();
            settingsPersistence.update(settings, true);            
        }
    }
    
    public void setChatEnabled(long userId, boolean enabled) throws Exception {
        // Get user settings
        Settings settings = getSettings(userId);
        // Change value
        if (settings != null) {
            settings.setChatEnabled(enabled);
            settingsPersistence.clearCache();
            settingsPersistence.update(settings, true);  
        }
    }
    
    public void changeActiveRoomType(long userId, String roomType) throws Exception {
        // Get user settings
        Settings settings = getSettings(userId);
        // Change value
        if (settings != null) {
            settings.setActiveRoomType(roomType);
            settingsPersistence.clearCache();
            settingsPersistence.update(settings, true);  
        }
    }
    
    
}