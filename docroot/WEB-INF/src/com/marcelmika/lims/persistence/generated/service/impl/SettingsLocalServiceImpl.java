/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.marcelmika.lims.persistence.generated.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.lims.persistence.generated.model.Settings;
import com.marcelmika.lims.persistence.generated.service.base.SettingsLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the settings local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.service.SettingsLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.base.SettingsLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil
 */
public class SettingsLocalServiceImpl extends SettingsLocalServiceBaseImpl {

    /*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil} to access the settings local service.
	 */

    /**
     * Returns settings related to the user whose id is given in the parameter
     *
     * @param userId id of the user whose setting you are requesting
     * @return Settings
     * @throws Exception
     */
    @Override
    public Settings getSettingsByUser(long userId) throws Exception {
        // Find user settings
        Settings settings = settingsPersistence.fetchByUserId(userId);

        // No settings found => create a new one
        if (settings == null) {
            long sid = counterLocalService.increment();
            settings = settingsPersistence.create(sid);
            settings.setUserId(userId);
            settings.setChatEnabled(true);
            settings.setPresence("presence.online");
            settingsPersistence.update(settings, true);
        }

        return settings;
    }

    /**
     * Saves settings object to persistence
     *
     * @param settings Settings model
     * @return Updated Settings
     * @throws SystemException
     */
    @Override
    public Settings saveSettings(Settings settings) throws SystemException {
        return settingsPersistence.update(settings, true);
    }

    /**
     * Updates user presence
     *
     * @param userId   id of the user whose presence should be updated
     * @param presence new value of the presence
     * @throws Exception
     */
    @Override
    public void changePresence(long userId, String presence) throws Exception {
        // Get user settings
        Settings settings = getSettingsByUser(userId);
        // Change presence
        if (settings != null) {
            // Update presence
            settings.setPresence(presence);
            // Save the time of change
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            settings.setPresenceUpdatedAt(now.getTime());

            settingsPersistence.update(settings, true);
        }
    }

    /**
     * Updates chat enabled value. If set to true the portlet is fully functional. If set to
     * false the chat will be disabled.
     *
     * @param userId  id of the user whose chat should be enabled/disabled
     * @param enabled if set to true the chat will be enabled. If set to false it will be disabled.
     * @throws Exception
     */
    @Override
    public void setChatEnabled(long userId, boolean enabled) throws Exception {
        // Get user settings
        Settings settings = getSettingsByUser(userId);
        // Change value
        if (settings != null) {
            settings.setChatEnabled(enabled);
            settingsPersistence.update(settings, true);
        }
    }

    /**
     * Returns all buddies in the system
     *
     * @param userId                of excluded user
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param start                 value of the list
     * @param end                   value of the list
     * @return List of objects where each object contains user info
     * @throws Exception
     */
    @Override
    public List<Object[]> getAllGroups(Long userId,
                                       boolean ignoreDefaultUser,
                                       boolean ignoreDeactivatedUser,
                                       int start,
                                       int end) throws Exception {
        // Find via settings finder
        return settingsFinder.findAllGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );
    }

    /**
     * Returns all groups where the user participates
     *
     * @param userId                of the user whose groups are we looking for
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param excludedSites         list of names of sites which should be excluded
     * @param start                 value of the list
     * @param end                   value of the list
     * @return List of objects where each object contains group name and user info
     * @throws Exception
     */
    @Override
    public List<Object[]> getSitesGroups(Long userId,
                                         boolean ignoreDefaultUser,
                                         boolean ignoreDeactivatedUser,
                                         String[] excludedSites,
                                         int start,
                                         int end) throws Exception {
        // Find via settings finder
        return settingsFinder.findSitesGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
        );
    }

    /**
     * Returns all user's social relations
     *
     * @param userId                of the user whose social relations are we looking for
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param relationTypes         an array of relation type codes that we are looking for
     * @param start                 value of the list
     * @param end                   value of the list
     * @return List objects where each object contains relation type and user info
     * @throws Exception
     */
    @Override
    public List<Object[]> getSocialGroups(Long userId,
                                          boolean ignoreDefaultUser,
                                          boolean ignoreDeactivatedUser,
                                          int[] relationTypes,
                                          int start,
                                          int end) throws Exception {
        // Find via settings finder
        return settingsFinder.findSocialGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, relationTypes, start, end
        );
    }

    /**
     * Returns a list of user's groups
     *
     * @param userId                of the user whose groups are we looking for
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param excludedGroups        list of names of groups which should be excluded
     * @param start                 value of the list
     * @param end                   value of the list
     * @return List of objects where each object contains group name and user info
     * @throws Exception
     */
    @Override
    public List<Object[]> getUserGroups(Long userId,
                                        boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser,
                                        String[] excludedGroups,
                                        int start,
                                        int end) throws Exception {
        // Find via settings finder
        return settingsFinder.findUserGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, excludedGroups, start, end
        );
    }

    /**
     * Returns all buddies in sites where the user participates based on the search query
     *
     * @param userId                of excluded user
     * @param searchQuery           search query string
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param excludedSites         list of names of sites which should be excluded
     * @param start                 value of the list
     * @param end                   value of the list
     * @return List of objects where each object contains user info
     * @throws Exception
     */
    @Override
    public List<Object[]> searchSitesBuddies(Long userId,
                                             String searchQuery,
                                             boolean ignoreDefaultUser,
                                             boolean ignoreDeactivatedUser,
                                             String[] excludedSites,
                                             int start,
                                             int end) throws Exception {

        // Search via settings finder
        return settingsFinder.searchSitesBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
        );
    }

    /**
     * Returns all buddies in the system based on the search query
     *
     * @param userId                of excluded user
     * @param searchQuery           search string
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param start                 value of the list
     * @param end                   value of the list
     * @return List of objects where each object contains user info
     * @throws Exception
     */
    @Override
    public List<Object[]> searchAllBuddies(Long userId,
                                           String searchQuery,
                                           boolean ignoreDefaultUser,
                                           boolean ignoreDeactivatedUser,
                                           int start,
                                           int end) throws Exception {
        // Search via settings finder
        return settingsFinder.searchAllBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );
    }

    /**
     * Returns all user's social relations based on the search query
     *
     * @param userId                of the user whose social relations are we looking for
     * @param searchQuery           search query string
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param relationTypes         an array of relation type codes that we are looking for
     * @param start                 value of the list
     * @param end                   value of the list
     * @return List of objects where each object contains user info
     * @throws Exception
     */
    @Override
    public List<Object[]> searchSocialBuddies(Long userId,
                                              String searchQuery,
                                              boolean ignoreDefaultUser,
                                              boolean ignoreDeactivatedUser,
                                              int[] relationTypes,
                                              int start,
                                              int end) throws Exception {

        // Search via settings finder
        return settingsFinder.searchSocialBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, relationTypes, start, end
        );
    }

    /**
     * Returns a list of buddies. This list is made of all buddies based on the search query that are
     * in the same user group as the user.
     *
     * @param userId                which should be excluded from the list
     * @param searchQuery           search query string
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param excludedGroups        names of groups that should be excluded from the list of buddies
     * @param start                 of the list
     * @param end                   of the list
     * @return a list of buddies
     * @throws Exception
     */
    @Override
    public List<Object[]> searchUserGroupsBuddies(Long userId,
                                                  String searchQuery,
                                                  boolean ignoreDefaultUser,
                                                  boolean ignoreDeactivatedUser,
                                                  String[] excludedGroups,
                                                  int start,
                                                  int end) throws Exception {

        // Search via settings finder
        return settingsFinder.searchUserGroupsBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, excludedGroups, start, end
        );
    }
}