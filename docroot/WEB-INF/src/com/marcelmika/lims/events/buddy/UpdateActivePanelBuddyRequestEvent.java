package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:27 PM
 */
public class UpdateActivePanelBuddyRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final String activePanel;

    public UpdateActivePanelBuddyRequestEvent(Long buddyId, String activePanel) {
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
