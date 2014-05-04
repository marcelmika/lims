package com.marcelmika.lims.api.entity;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 8:41 PM
 */
public class SettingsDetails {

    private String status;
    private String activePanelId;
    private boolean isMute;
    private boolean isChatEnabled;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
