package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.PaginationDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 7:26 PM
 */
public class Pagination {

    private Integer firstMessageId;
    private Integer lastMessageId;
    private String action;


    public PaginationDetails toPaginationDetails() {
        PaginationDetails details = new PaginationDetails();

        details.setFirstMessageId(firstMessageId);
        details.setLastMessageId(lastMessageId);

        if (action.equals("next")) {
            details.setAction(PaginationDetails.ActionDetails.NEXT);
        } else if (action.equals("prev")) {
            details.setAction(PaginationDetails.ActionDetails.PREV);
        }

        return details;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "firstMessageId='" + firstMessageId + '\'' +
                ", lastMessageId='" + lastMessageId + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
