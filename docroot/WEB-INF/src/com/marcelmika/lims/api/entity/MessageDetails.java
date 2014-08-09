package com.marcelmika.lims.api.entity;

import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:35 PM
 */
public class MessageDetails {

    private BuddyDetails from;
    private String body;
    private Date createdAt;


    public BuddyDetails getFrom() {
        return from;
    }

    public void setFrom(BuddyDetails from) {
        this.from = from;
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

    @Override
    public String toString() {
        return "MessageDetails{" +
                "from=" + from +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
