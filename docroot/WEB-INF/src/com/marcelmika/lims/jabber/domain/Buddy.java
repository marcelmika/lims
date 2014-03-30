package com.marcelmika.lims.jabber.domain;

import com.marcelmika.lims.events.details.BuddyDetails;
import com.marcelmika.lims.jabber.utils.Jid;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:21 PM
 */
public class Buddy {

    private Long buddyId;
    private Long portraitId;
    private String fullName;
    private String screenName;
    private String password;
    private String status;

    /**
     * Create new Buddy from RosterEntry.
     *
     * @param rosterEntry Smack's RosterEntry.
     * @return Buddy
     */
    public static Buddy fromRosterEntry(RosterEntry rosterEntry) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map properties
        buddy.fullName = rosterEntry.getName();
        buddy.screenName = rosterEntry.getUser();

        return buddy;
    }

    /**
     * Creates buddy from chat and roster
     *
     * @param chat   Chat
     * @param roster Roster
     * @return Buddy
     */
    public static Buddy fromChat(Chat chat, Roster roster) {
        // CREATE NEW BUDDY
        Buddy buddy = new Buddy();
        // Map properties
        // TODO: Take name from roster
        buddy.fullName = chat.getParticipant();
        buddy.screenName = Jid.getBareAddress(chat.getParticipant());
//        buddy.setStatus(new Status(roster.getPresence(buddy.username)));
        return buddy;
    }

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
        buddy.setBuddyId(buddyDetails.getBuddyId());
        buddy.setFullName(buddyDetails.getFullName());
        buddy.setPortraitId(buddyDetails.getPortraitId());
        buddy.setScreenName(buddyDetails.getScreenName());
        buddy.setPassword(buddyDetails.getPassword());
        buddy.setStatus(buddyDetails.getStatus());

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
