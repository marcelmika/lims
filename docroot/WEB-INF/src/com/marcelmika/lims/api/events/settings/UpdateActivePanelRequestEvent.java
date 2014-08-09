package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:27 PM
 */
public class UpdateActivePanelRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final String activePanel;

    public UpdateActivePanelRequestEvent(Long buddyId, String activePanel) {
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
