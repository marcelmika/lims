package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:22 PM
 */
public interface BuddyCoreService {

    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event);
}
