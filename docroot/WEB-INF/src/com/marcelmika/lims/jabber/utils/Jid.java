package com.marcelmika.lims.jabber.utils;

import org.jivesoftware.smack.util.StringUtils;

import java.util.Locale;

/**
 * Provides methods to process Jabber Identifier.
 * Warning: Implementation should be review, methods renamed according to
 * http://xmpp.org/rfcs/rfc6122.html
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:41 PM
 */
public class Jid {

    /**
     * @return Lower cased resource or <code>null</code> for <code>null</code>
     * argument.
     */
    public static String getResource(String user) {
        return user == null ? null : StringUtils.parseResource(user
                .toLowerCase(Locale.US));
    }

    /**
     * @return Lower cased server name or <code>null</code> for
     * <code>null</code> argument.
     */
    public static String getServer(String user) {
        return user == null ? null : StringUtils.parseServer(user
                .toLowerCase(Locale.US));
    }

    /**
     * @return Lower cased user name part or <code>null</code> for
     * <code>null</code> argument.
     */
    public static String getName(String user) {
        return user == null ? null : StringUtils.parseName(user
                .toLowerCase(Locale.US));
    }

    /**
     * @return Lower cased bare address or <code>null</code> for
     * <code>null</code> argument.
     */
    public static String getBareAddress(String user) {
        return user == null ? null : StringUtils.parseBareAddress(user
                .toLowerCase(Locale.US));
    }

    /**
     * Gets lower cased address.
     */
    public static String getStringPrep(String user) {
        return user == null ? null : user.toLowerCase(Locale.US);
    }

}
