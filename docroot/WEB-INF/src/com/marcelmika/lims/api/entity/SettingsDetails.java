package com.marcelmika.lims.api.entity;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 8:41 PM
 */
public class SettingsDetails {

    private PresenceDetails presenceDetails;
    private String activePanelId;
    private boolean isMute;
    private boolean isChatEnabled;

    public PresenceDetails getPresenceDetails() {
        return presenceDetails;
    }

    public void setPresenceDetails(PresenceDetails presenceDetails) {
        this.presenceDetails = presenceDetails;
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
