package com.marcelmika.lims.events.details;

import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:35 PM
 */
public class MessageDetails {

    private BuddyDetails from;
    private BuddyDetails to;
    private Date createdAt;
    private String body;

    public BuddyDetails getFrom() {
        return from;
    }

    public void setFrom(BuddyDetails from) {
        this.from = from;
    }

    public BuddyDetails getTo() {
        return to;
    }

    public void setTo(BuddyDetails to) {
        this.to = to;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
