package com.marcelmika.lims.events;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:42 PM
 */
public class ResponseEvent {

    protected String result;
    protected boolean success;
    protected Throwable exception;

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public Throwable getException() {
        return exception;
    }
}
