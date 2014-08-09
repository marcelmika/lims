package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.PaginationDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 8:15 PM
 */
public class Pagination {

    private Integer firstMessageId;
    private Integer lastMessageId;
    private Action action;

    public enum Action {
        NEXT("next"),
        PREV("prev");

        private String action;

        private Action(String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }
    }

    public static Pagination fromPaginationDetails(PaginationDetails details) {
        Pagination pagination = new Pagination();

        pagination.firstMessageId = details.getFirstMessageId();
        pagination.lastMessageId = details.getLastMessageId();

        if (details.getAction() == PaginationDetails.ActionDetails.NEXT) {
            pagination.setAction(Action.NEXT);
        } else if (details.getAction() == PaginationDetails.ActionDetails.PREV) {
            pagination.setAction(Action.PREV);
        }

        return pagination;
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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
