

package com.marcelmika.lims.jabber;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class JabberException extends Exception {

    /**
     * Creates a new instance of
     * <code>JabberException</code> without detail message.
     */
    public JabberException() {
    }

    /**
     * Constructs an instance of
     * <code>JabberException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public JabberException(String msg) {
        super(msg);
    }
}
