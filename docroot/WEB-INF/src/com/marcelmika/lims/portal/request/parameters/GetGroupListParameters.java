package com.marcelmika.lims.portal.request.parameters;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/12/14
 * Time: 8:14 PM
 */
public class GetGroupListParameters {

    private Integer etag;

    public Integer getEtag() {
        return etag;
    }

    public void setEtag(Integer etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        return "GetGroupListParameters{" +
                "etag=" + etag +
                '}';
    }
}
