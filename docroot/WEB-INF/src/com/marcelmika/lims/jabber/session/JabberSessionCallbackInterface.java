package com.marcelmika.lims.jabber.session;

import org.jivesoftware.smack.Connection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 9:24 PM
 */
public interface JabberSessionCallbackInterface {

    public void sessionDidLogin(long userId, Connection connection);
    public void sessionDidNotAuthorize(long userId, Connection connection, Exception e);
    public void sessionDidNotLogin(long userId, Connection connection, Exception e);

    public void sessionDidLogout(long userId);
    public void sessionDidNotLogout(long userId, Exception e);

}
