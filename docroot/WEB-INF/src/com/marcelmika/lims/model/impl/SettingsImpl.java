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
package com.marcelmika.lims.model.impl;

import com.marcelmika.lims.model.json.JSONable;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * The extended model implementation for the Settings service. Represents a row
 * in the &quot;Chat_Settings&quot; database table, with each column mapped to a
 * property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun ServiceBuilder to copy their definitions
 * into the {@link com.marcelmika.lims.model.Settings} interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SettingsImpl extends SettingsBaseImpl implements JSONable {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. All methods that expect a settings model instance should use the {@link Settings} interface instead.
     */

    public SettingsImpl() {
    }

    public JSONObject toJSON() {
        JSONObject json = JSONFactoryUtil.createJSONObject();
        json.put("statusId", this.getSid());
        json.put("userId", this.getUserId());
        json.put("status", this.getStatus());        
        json.put("activePanelId", this.getActivePanelId());        
        json.put("mute", this.getMute());        

        return json;
    }
}