package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.marcelmika.lims.events.details.SettingsDetails;

import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/6/14
 * Time: 8:21 PM
 */
public class Settings {

    private static final String KEY_STATUS = "status";
    private static final String KEY_ACTIVE_ROOM_TYPE = "roomVisibility";
    private static final String KEY_ACTIVE_PANEL_ID = "activePanelId";
    private static final String KEY_MUTE = "mute";
    private static final String KEY_CHAT_ENABLED = "chatEnabled";

    private String status;
    private String activeRoomType;
    private String activePanelId;
    private boolean isMute;
    private boolean isChatEnabled;

    /**
     * Creates settings from poller request
     *
     * @param pollerRequest PollerRequest
     * @return Settings
     */
    public static Settings fromPollerRequest(PollerRequest pollerRequest) {
        // Map contains all parameters from request
        Map<String, String> parameterMap = pollerRequest.getParameterMap();
        // Create new buddy
        Settings settings = new Settings();

        // Status
        if (parameterMap.containsKey(KEY_STATUS)) {
            settings.status = GetterUtil.getString(parameterMap.get(KEY_STATUS));
        }
        // Active Room Type
        if (parameterMap.containsKey(KEY_ACTIVE_ROOM_TYPE)) {
            settings.activeRoomType = GetterUtil.getString(parameterMap.get(KEY_ACTIVE_ROOM_TYPE));
        }
        // Active Panel Id
        if (parameterMap.containsKey(KEY_ACTIVE_PANEL_ID)) {
            settings.activePanelId = GetterUtil.getString(parameterMap.get(KEY_ACTIVE_PANEL_ID));
        }
        // Mute
        if (parameterMap.containsKey(KEY_MUTE)) {
            settings.isMute = GetterUtil.getBoolean(parameterMap.get(KEY_MUTE));
        }
        // Chat Enabled
        if (parameterMap.containsKey(KEY_CHAT_ENABLED)) {
            settings.isChatEnabled = GetterUtil.getBoolean(parameterMap.get(KEY_CHAT_ENABLED));
        }


        return settings;
    }


    /**
     * Create new settings and maps data from settings details
     *
     * @param settingsDetails SettingsDetails
     * @return Settings
     */
    public static Settings fromSettingsDetails(SettingsDetails settingsDetails) {
        // Create new Settings
        Settings settings = new Settings();
        // Map data to settings details
        settings.status = settingsDetails.getStatus();
        settings.activeRoomType = settingsDetails.getActiveRoomType();
        settings.activePanelId = settingsDetails.getActivePanelId();
        settings.isMute = settingsDetails.isMute();
        settings.isChatEnabled = settingsDetails.isChatEnabled();

        return settings;
    }

    /**
     * Maps settings to settings details
     *
     * @return SettingsDetails
     */
    public SettingsDetails toSettingsDetails() {
        // Create new user details
        SettingsDetails details = new SettingsDetails();
        // Map data from user
        details.setStatus(status);
        details.setActiveRoomType(activeRoomType);
        details.setActivePanelId(activePanelId);
        details.setMute(isMute);
        details.setChatEnabled(isChatEnabled);

        return details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActiveRoomType() {
        return activeRoomType;
    }

    public void setActiveRoomType(String activeRoomType) {
        this.activeRoomType = activeRoomType;
    }

    public String getActivePanelId() {
        return activePanelId;
    }

    public void setActivePanelId(String activePanelId) {
        this.activePanelId = activePanelId;
    }

    public boolean isMute() {
        return isMute;
    }

    public void setMute(boolean isMute) {
        this.isMute = isMute;
    }

    public boolean isChatEnabled() {
        return isChatEnabled;
    }

    public void setChatEnabled(boolean isChatEnabled) {
        this.isChatEnabled = isChatEnabled;
    }
}
