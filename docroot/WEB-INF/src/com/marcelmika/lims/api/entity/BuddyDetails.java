package com.marcelmika.lims.api.entity;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:28 PM
 */
public class BuddyDetails {

    private Long buddyId;
    private String fullName;
    private String screenName;
    private String password;
    private PresenceDetails presenceDetails;
    private SettingsDetails settingsDetails;

    public Long getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(Long buddyId) {
        this.buddyId = buddyId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PresenceDetails getPresenceDetails() {
        return presenceDetails;
    }

    public void setPresenceDetails(PresenceDetails presenceDetails) {
        this.presenceDetails = presenceDetails;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }

    public void setSettingsDetails(SettingsDetails settingsDetails) {
        this.settingsDetails = settingsDetails;
    }

    @Override
    public String toString() {
        return "BuddyDetails{" +
                "buddyId=" + buddyId +
                ", fullName='" + fullName + '\'' +
                ", screenName='" + screenName + '\'' +
                ", password='" + password + '\'' +
                ", presenceDetails=" + presenceDetails +
                ", settingsDetails=" + settingsDetails +
                '}';
    }
}
