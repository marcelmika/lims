package com.marcelmika.lims.events.details;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:28 PM
 */
public class BuddyDetails {

    private Long buddyId;
    private Long portraitId;
    private String fullName;
    private String screenName;
    private String password;
    private String status;
    private SettingsDetails settingsDetails;

    public Long getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(Long buddyId) {
        this.buddyId = buddyId;
    }

    public Long getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(Long portraitId) {
        this.portraitId = portraitId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }

    public void setSettingsDetails(SettingsDetails settingsDetails) {
        this.settingsDetails = settingsDetails;
    }
}
