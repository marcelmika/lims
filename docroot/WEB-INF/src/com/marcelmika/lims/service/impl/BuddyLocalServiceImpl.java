/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.marcelmika.lims.service.impl;

import com.marcelmika.lims.service.base.BuddyLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.lims.model.Buddy;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the buddy local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link com.marcelmika.lims.service.BuddyLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.service.base.BuddyLocalServiceBaseImpl
 * @see com.marcelmika.lims.service.BuddyLocalServiceUtil
 */
public class BuddyLocalServiceImpl extends BuddyLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link BuddyLocalServiceUtil} to access the buddy local service.
     */

    public List<Buddy> findByQuery(String query) {
        // Create dynamic query
        DynamicQuery dq = DynamicQueryFactoryUtil.forClass(User.class);
        // Trim query a split by any number of spaces
        String[] tags = query.trim().split("\\s+");

        Criterion criterion = null;        
        // Create chain of criterions
        for (String tag : tags) {        
            tag = StringPool.PERCENT + tag + StringPool.PERCENT;
            if (criterion == null) {
                criterion = RestrictionsFactoryUtil.ilike("firstName", tag);
                criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.ilike("lastName", tag));
            } else {
                criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.ilike("firstName", tag));
                criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.ilike("lastName", tag));
            }
        }        
                
        dq.add(criterion);        
        dq.setLimit(0, 8);

        List<User> users;
        List<Buddy> buddies = new ArrayList<Buddy>();

        // Fetch
        try {
            users = userLocalService.dynamicQuery(dq);
        } catch (SystemException ex) {
//            System.out.println(ex.getMessage());
            return null;
        }

        // Map buddies from users
        for (User user : users) {
            buddies.add(com.marcelmika.lims.service.BuddyLocalServiceUtil.mapBuddyFromUser(user));
        }

        return buddies;
    }

    public Buddy mapBuddyFromUser(User user) {
        Buddy buddy = buddyLocalService.createBuddy(0);
        buddy.setPortraitId(user.getPortraitId());
        buddy.setUserId(user.getUserId());
        buddy.setFullName(ContactConstants.getFullName(user.getFirstName(), user.getMiddleName(), user.getLastName()));
        buddy.setScreenName(user.getScreenName());
        buddy.setCompanyId(user.getCompanyId());

        /* @todo implement better */
        buddy.setAwake(true);

        return buddy;
    }

    public Buddy getBuddyByScreenName(long companyId, String screenName) {
        Buddy buddy;
        User user;

        try {
            user = UserLocalServiceUtil.getUserByScreenName(companyId, screenName);
            buddy = mapBuddyFromUser(user);
        } catch (PortalException ex) {
//            System.out.println(ex);
            return null;
        } catch (SystemException ex) {            
            return null;
        }

        return buddy;
    }

    public Buddy getBuddyByUserId(long userId) {
        Buddy buddy;
        User user;

        try {
            user = UserLocalServiceUtil.getUserById(userId);
            buddy = mapBuddyFromUser(user);
        } catch (PortalException ex) {
//            System.out.println(ex.getMessage());
            return null;
        } catch (SystemException ex) {
//            System.out.println(ex.getMessage());
            return null;
        }

        return buddy;
    }
}