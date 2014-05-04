package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.SettingsDetails;
import com.marcelmika.lims.model.Panel;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/4/14
 * Time: 4:26 PM
 */
public class Settings {

    private String presence;
    private String activePanel;
    private boolean isMute;
    private boolean isChatEnabled;


    public static Settings fromServiceBuilderModel(com.marcelmika.lims.model.Panel panelModel,
                                                   com.marcelmika.lims.model.Settings settingsModel) {

        // Create new settings
        Settings settings = new Settings();
        // Map Panel
        if (panelModel != null) {
            settings.activePanel = panelModel.getActivePanelId();
        }
        // Map Settings
        if (settingsModel != null) {
            settings.presence = settingsModel.getStatus();
            settings.isMute = settingsModel.getMute();
            settings.isChatEnabled = settingsModel.getChatEnabled();
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
        settings.presence = settingsDetails.getStatus();
        settings.activePanel = settingsDetails.getActivePanelId();
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
        details.setStatus(presence);
        details.setActivePanelId(activePanel);
        details.setMute(isMute);
        details.setChatEnabled(isChatEnabled);

        return details;
    }



    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getActivePanel() {
        return activePanel;
    }

    public void setActivePanel(String activePanel) {
        this.activePanel = activePanel;
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
