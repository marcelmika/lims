
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.jabber.domain;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import java.util.Date;

import org.jivesoftware.smackx.packet.DelayInformation;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 */
public class MessageDeprecated {

    private org.jivesoftware.smack.packet.Message smackMessage;
    private long companyId;
    private Date created;

    public MessageDeprecated(org.jivesoftware.smack.packet.Message smackMesasge) {
        this.smackMessage = smackMesasge;
        this.created = getMessageTimestamp(smackMesasge);                        
    }

    private Date getMessageTimestamp(org.jivesoftware.smack.packet.Message message) {
        // Message creation date can be retreived just from the offline messages
        DelayInformation inf = null;      
        
        try {
            inf = (DelayInformation) message.getExtension("x", "jabber:x:delay");
        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }

        // Return offline message timestamp
        if (inf != null) {
            Date date = inf.getStamp();
            return date;
        }

        // Message is not offline -> return current timestamp
        return new Date();
    }

    public void setFrom(String from) {
        smackMessage.setFrom(from);
    }

    public String getFrom() {
        return smackMessage.getFrom();
    }

    public String getTo() {
        return smackMessage.getTo();
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public void setBody(String body) {
        smackMessage.setBody(body);
    }

    public String getBody() {
        return smackMessage.getBody();
    }
    
//    public Buddy getBuddy() {
////        return com.marcelmika.lims.service.BuddyLocalServiceUtil.getBuddyByScreenName(getCompanyId(), getFrom());
//    }

//    public Buddy getParticipant() {
//        String screenName = JabberUtil.getScreenName(getTo());
//        return com.marcelmika.lims.service.BuddyLocalServiceUtil.getBuddyByScreenName(getCompanyId(), screenName);
//    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Message From: " + getFrom() + " To: " + getTo() + " = " + getBody();
    }

    public JSONObject toJSON() {
        JSONObject jsonMessage = JSONFactoryUtil.createJSONObject();

        // Find buddy            
//        Buddy buddy = com.marcelmika.lims.service.BuddyLocalServiceUtil.getBuddyByScreenName(getCompanyId(), getFrom());
//        if (buddy != null) {
//            jsonMessage.put("from", buddy.toJSON());
//        }
        // Content
        jsonMessage.put("content", getBody());

        // Crated timestamp            
        jsonMessage.put("created", getCreated().getTime());

        return jsonMessage;
    }
}
