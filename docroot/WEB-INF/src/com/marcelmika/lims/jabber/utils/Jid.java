package com.marcelmika.lims.jabber.utils;

import com.liferay.portal.kernel.util.StringPool;
import com.marcelmika.lims.portal.properties.PortletPropertiesValues;
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
     * Returns full Jid from user name in the form of user@host/resource
     *
     * @param user name
     * @return Lower cased full jid
     */
    public static String getFullJid(String user) {
        // Check input
        if (user == null) {
            return null;
        }
        // Parse the jid
        String name = getJid(user);
        // Add resource
        return name.concat(StringPool.SLASH).concat(PortletPropertiesValues.JABBER_RESOURCE);
    }

    /**
     * Returns Jid from user name in the form of user@host
     *
     * @param user name
     * @return Lower cased jid
     */
    public static String getJid(String user) {
        // Check input
        if (user == null) {
            return null;
        }
        // Add host
        return user.concat(StringPool.AT).concat(PortletPropertiesValues.JABBER_SERVICE_NAME);
    }

    /**
     * Returns the resource portion of a XMPP address. For example, for the address "matt@jivesoftware.com/Smack",
     * "Smack" would be returned. If no resource is present in the address, the empty string will be returned.
     *
     * @return Lower cased resource or <code>null</code> for <code>null</code> argument.
     */
    public static String getResource(String user) {
        return user == null ? null : StringUtils.parseResource(user.toLowerCase(Locale.US));
    }

    /**
     * Returns the server portion of a XMPP address. For example, for the address "matt@jivesoftware.com/Smack",
     * "jivesoftware.com" would be returned. If no server is present in the address, the empty string will be returned.
     *
     * @return Lower cased server name or <code>null</code> for <code>null</code> argument.
     */
    public static String getServer(String user) {
        return user == null ? null : StringUtils.parseServer(user.toLowerCase(Locale.US));
    }

    /**
     * Returns the name portion of a XMPP address. For example, for the address "matt@jivesoftware.com/Smack", "matt"
     * would be returned. If no username is present in the address, the empty string will be returned.
     *
     * @return Lower cased user name part or <code>null</code> for <code>null</code> argument.
     */
    public static String getName(String user) {
        return user == null ? null : StringUtils.parseName(user.toLowerCase(Locale.US));
    }

    /**
     * Returns the XMPP address with any resource information removed. For example, for the address
     * "matt@jivesoftware.com/Smack", "matt@jivesoftware.com" would be returned.
     *
     * @return Lower cased bare address or <code>null</code> for <code>null</code> argument.
     */
    public static String getBareAddress(String user) {
        return user == null ? null : StringUtils.parseBareAddress(user.toLowerCase(Locale.US));
    }

    /**
     * Gets lower cased address.
     */
    public static String getStringPrep(String user) {
        return user == null ? null : user.toLowerCase(Locale.US);
    }
}
