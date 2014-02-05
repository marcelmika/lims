package com.marcelmika.lims.core.domain;

import com.marcelmika.lims.events.details.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 8:33 PM
 */
public class Settings {

    private String status;
    private String activeRoomType;
    private String activePanelId;
    private boolean isMute;
    private boolean isChatEnabled;


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
        settings.setStatus(settingsDetails.getStatus());
        settings.setActiveRoomType(settingsDetails.getActiveRoomType());
        settings.setActivePanelId(settingsDetails.getActivePanelId());
        settings.setMute(settingsDetails.isMute());
        settings.setChatEnabled(settingsDetails.isChatEnabled());

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
