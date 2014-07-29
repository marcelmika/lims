package com.marcelmika.lims.portal.processor.parameters;

import com.marcelmika.lims.portal.domain.Pagination;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 7:25 PM
 */
public class ReadConversationParameters {

    private String conversationId;
    private Pagination pagination;
    private Integer etag;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Integer getEtag() {
        return etag;
    }

    public void setEtag(Integer etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        return "ReadConversationParameters{" +
                "conversationId='" + conversationId + '\'' +
                ", pagination=" + pagination +
                ", etag=" + etag +
                '}';
    }
}
