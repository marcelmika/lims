package com.marcelmika.lims.api.entity;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 7:52 PM
 */
public class PaginationDetails {

    private Integer firstMessageId;
    private Integer lastMessageId;
    private ActionDetails action;

    public enum ActionDetails {
        NEXT("next"),
        PREV("prev");

        private String action;

        private ActionDetails(String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }
    }

    public Integer getFirstMessageId() {
        return firstMessageId;
    }

    public void setFirstMessageId(Integer firstMessageId) {
        this.firstMessageId = firstMessageId;
    }

    public Integer getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(Integer lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public ActionDetails getAction() {
        return action;
    }

    public void setAction(ActionDetails action) {
        this.action = action;
    }
}
