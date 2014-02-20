

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

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public JabberException(String message, Throwable cause) {
        super(message, cause);
    }
}
