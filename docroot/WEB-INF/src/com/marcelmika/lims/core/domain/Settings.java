package com.marcelmika.lims.core.domain;

import com.marcelmika.lims.api.entity.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 8:33 PM
 */
public class Settings {

    private Presence presence;
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
        settings.activePanelId = settingsDetails.getActivePanelId();
        settings.isMute = settingsDetails.isMute();
        settings.isChatEnabled = settingsDetails.isChatEnabled();

        // Relations
        if (settingsDetails.getPresenceDetails() != null) {
            settings.presence = Presence.fromPresenceDetails(settingsDetails.getPresenceDetails());
        }

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
        // Map data from users
        details.setActivePanelId(activePanelId);
        details.setMute(isMute);
        details.setChatEnabled(isChatEnabled);

        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
        }

        return details;
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
