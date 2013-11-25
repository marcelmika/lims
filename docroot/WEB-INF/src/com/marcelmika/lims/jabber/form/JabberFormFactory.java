
package com.marcelmika.lims.jabber.form;

import org.jivesoftware.smackx.Form;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class JabberFormFactory {
   
    /**
     * Returns configuration for multi user chat
     * @link http://xmpp.org/extensions/xep-0045.html#roomconfig
     * @return Configuration form for multi user chat
     */
    public static Form getMUCConfigurationForm() {
        Form form = new Form(Form.TYPE_SUBMIT);
        // Make room persistent
        form.addField(new PersistentRoomFormField(true));

        return form;
    }
   
}
