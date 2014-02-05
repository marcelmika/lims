package com.marcelmika.lims.events.buddy;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:27 PM
 */
public class BuddyUpdateActivePanelRequestEvent {

    private final Long buddyId;
    private final String activePanel;

    public BuddyUpdateActivePanelRequestEvent(Long buddyId, String activePanel) {
        this.buddyId = buddyId;
        this.activePanel = activePanel;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public String getActivePanel() {
        return activePanel;
    }
}
