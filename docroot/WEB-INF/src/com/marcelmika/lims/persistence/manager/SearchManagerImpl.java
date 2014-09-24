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

package com.marcelmika.lims.persistence.manager;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/09/14
 * Time: 10:03
 */
public class SearchManagerImpl implements SearchManager {

    // Log
    private static Log log = LogFactoryUtil.getLog(SearchManagerImpl.class);

    /**
     * Returns a list of buddies based on the search query. The search will be performed
     * in first name, middle name, last name, screen name and email.
     *
     * @param userId      Long
     * @param searchQuery String
     * @param start       of the list
     * @param end         of the list
     * @return List of buddies
     * @throws Exception
     */
    @Override
    public List<Buddy> searchBuddies(Long userId, String searchQuery, int start, int end) throws Exception {
        // Get selected list strategy
        Environment.BuddyListStrategy strategy = Environment.getBuddyListStrategy();
        // Get the info if default user should be ignored
        boolean ignoreDefaultUser = Environment.getBuddyListIgnoreDefaultUser();
        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        // Some sites or groups may be excluded
        String[] excludedSites = Environment.getBuddyListSiteExcludes();
        String[] excludedGroups = Environment.getBuddyListGroupExcludes();
        // Relation types
        Environment.BuddyListSocialRelation[] relationTypes = Environment.getBuddyListAllowedSocialRelationTypes();

        // All buddies
        if (strategy == Environment.BuddyListStrategy.ALL) {
            return searchAllBuddies(
                    userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, start, end
            );
        }
        // Buddies from sites
        else if (strategy == Environment.BuddyListStrategy.SITES) {
            return searchSitesBuddies(
                    userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
            );
        }
        // Unknown
        else {
            throw new Exception("Unknown buddy list strategy");
        }
    }


    /**
     * Returns a list of buddies related to the user based on the search query
     *
     * @param userId                Long
     * @param searchQuery           String
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param start                 of the list
     * @param end                   of the list
     * @return List of buddies
     * @throws Exception
     */
    private List<Buddy> searchAllBuddies(Long userId,
                                         String searchQuery,
                                         boolean ignoreDefaultUser,
                                         boolean ignoreDeactivatedUser,
                                         int start,
                                         int end) throws Exception {

        // Get from persistence
        List<Object[]> users = SettingsLocalServiceUtil.searchAllGroups(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );

        // Deserialize user info in plain object to buddy
        List<Buddy> buddies = new LinkedList<Buddy>();
        for (Object[] userObject : users) {
            // Deserialize
            Buddy buddy = Buddy.fromPlainObject(userObject, 0);
            // Add to list
            buddies.add(buddy);
        }

        return buddies;
    }

    /**
     * Returns a list of buddies. The list is made of all buddies based on the search query in the sites
     * where the user participates
     *
     * @param userId                which should be excluded from the list
     * @param searchQuery           search query string
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param excludedSites         names of sites (groups) that should be excluded from the group collection
     * @param start                 of the list
     * @param end                   of the list
     * @return GroupCollection
     * @throws Exception
     */
    private List<Buddy> searchSitesBuddies(Long userId,
                                           String searchQuery,
                                           boolean ignoreDefaultUser,
                                           boolean ignoreDeactivatedUser,
                                           String[] excludedSites,
                                           int start,
                                           int end) throws Exception {

        // Get from persistence
        List<Object[]> users = SettingsLocalServiceUtil.searchSitesBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
        );

        // Deserialize user info in plain objects to buddy
        List<Buddy> buddies = new LinkedList<Buddy>();
        for (Object[] userObject : users) {
            // Deserialize
            Buddy buddy = Buddy.fromPlainObject(userObject, 0);
            // Add to list
            buddies.add(buddy);
        }

        return buddies;
    }
}