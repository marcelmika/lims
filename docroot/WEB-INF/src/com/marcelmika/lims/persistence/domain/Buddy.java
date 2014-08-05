package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.persistence.generated.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:55 PM
 */
public class Buddy {

    private Long buddyId;
    /**
     * @deprecated
     */
    private Long portraitId;
    private String fullName;
    private String screenName;
    private String password;
    private Presence presence;
    /**
     * @deprecated
     */
    private String status;

    /**
     * Create new user and maps data from user details
     *
     * @param buddyDetails BuddyDetails
     * @return User
     */
    public static Buddy fromBuddyDetails(BuddyDetails buddyDetails) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data to user details
        buddy.buddyId = buddyDetails.getBuddyId();
        buddy.fullName = buddyDetails.getFullName();
        buddy.portraitId = buddyDetails.getPortraitId();
        buddy.screenName = buddyDetails.getScreenName();
        buddy.password = buddyDetails.getPassword();
        buddy.status = buddyDetails.getStatus();

        // Relations
        if (buddyDetails.getPresenceDetails() != null) {
            buddy.presence = Presence.fromPresenceDetails(buddyDetails.getPresenceDetails());
        }

        return buddy;
    }


    /**
     * Factory method which creates new list of Buddies from the list of BuddyDetails
     *
     * @param detailsList list of buddy details
     * @return List<Buddy> of buddies
     */
    public static List<Buddy> fromBuddyDetailsList(List<BuddyDetails> detailsList) {
        // Create new list of buddies
        List<Buddy> buddies = new ArrayList<Buddy>();

        // Iterate through details and create buddy based on that
        for (BuddyDetails details : detailsList) {
            buddies.add(Buddy.fromBuddyDetails(details));
        }

        return buddies;
    }

    /**
     * Factory method which creates buddy from participant model
     *
     * @param participant Participant
     * @return Buddy
     */
    public static Buddy fromParticipantModel(Participant participant) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data from model
        buddy.setBuddyId(participant.getParticipantId());

        return buddy;
    }

    /**
     * Factory method which creates buddy from plain java object usually retrieved from database
     *
     * @param object       Object[] array which contains buddy data
     * @param firstElement determines first element in Object[] where the buddy serialization should start
     * @return Buddy
     */
    public static Buddy fromPlainObject(Object[] object, int firstElement) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data from object
        buddy.buddyId = (Long) object[firstElement++];
        buddy.screenName = (String) object[firstElement++];
        buddy.fullName = String.format("%s %s %s",
                object[firstElement++],
                object[firstElement++],
                object[firstElement++]);
        buddy.status = String.format("%s", object[firstElement]);

        String presence = String.format("%s", object[firstElement]);

        if (presence != null) {
            buddy.presence = Presence.fromStatus(presence);
        }

        return buddy;
    }

    /**
     * Maps user to user details
     *
     * @return UserDetails
     */
    public BuddyDetails toBuddyDetails() {
        // Create new user details
        BuddyDetails details = new BuddyDetails();
        // Map data from user
        details.setBuddyId(buddyId);
        details.setFullName(fullName);
        details.setPortraitId(portraitId);
        details.setScreenName(screenName);
        details.setPassword(password);
        details.setStatus(status);

        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
        }

        return details;
    }

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

    /**
     * @deprecated
     */
    public String getStatus() {
        return status;
    }

    /**
     * @deprecated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }
}
