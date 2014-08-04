package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.marcelmika.lims.persistence.generated.model.Settings;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/4/14
 * Time: 10:19 AM
 */
public class SettingsFinderImpl extends BasePersistenceImpl<Settings> implements SettingsFinder {

    // Log
    private static Log log = LogFactoryUtil.getLog(SettingsFinderImpl.class);

    // Find all users SQL
    private static final String FIND_ALL_USERS = SettingsFinder.class.getName() + ".findAllUsers";
    private static final String FIND_BY_USERS_GROUPS = SettingsFinder.class.getName() + ".findByUsersGroups";

    // Placeholders
    private static final String PLACEHOLDER_DEFAULT_USER = "[$DEFAULT_USER$]";
    private static final String PLACEHOLDER_USERS_GROUPS_JOIN = "[$USERS_GROUPS_JOIN$]";
    private static final String PLACEHOLDER_USERS_GROUPS_WHERE = "[$USERS_GROUPS_WHERE$]";

    /**
     * Returns all users except the one given in the parameter and their settings
     *
     * @param userId Excluded user
     * @return List of users in object
     */
    public List<Object[]> findAllGroups(Long userId, boolean ignoreDefaultUser, int start, int end) throws Exception {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getFindAllUsersSQL(ignoreDefaultUser);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("status", Type.STRING);


            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Returns
     *
     * @param userId            of excluded user
     * @param ignoreDefaultUser true if default users should be ignored
     * @param excludedSties     list of names of sites which should be excluded
     * @param start             value of the list
     * @param end               value of the list
     * @return List of objects where each object contains group where each group contains users
     * @throws Exception
     */
    public List<Object[]> findSitesGroups(Long userId,
                                          boolean ignoreDefaultUser,
                                          String[] excludedSties,
                                          int start,
                                          int end) throws Exception {
        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getFindByUsersGroupsSQL(ignoreDefaultUser, excludedSties);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("groupName", Type.STRING);
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("status", Type.STRING);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            if (excludedSties.length > 0) {
                queryPos.add(excludedSties);
            }
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Generates SQL for find all users query
     *
     * @param ignoreDefaultUser determines if the default user should be ignored
     * @return SQL string for find all users query
     */
    private String getFindAllUsersSQL(boolean ignoreDefaultUser) {
        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(FIND_ALL_USERS);

        // Add ignore default user query if needed
        if (ignoreDefaultUser) {
            sql = StringUtil.replace(sql, PLACEHOLDER_DEFAULT_USER, "AND (User_.defaultUser != true)");
        } else {
            sql = StringUtil.replace(sql, PLACEHOLDER_DEFAULT_USER, StringPool.BLANK);
        }

        return sql;
    }

    /**
     * Generates SQL for find by users query
     *
     * @param ignoreDefaultUser true if default users should be ignored
     * @param excludedGroups names of groups that should be excluded from the query
     * @return SQL string for find by users groups query
     */
    private String getFindByUsersGroupsSQL(boolean ignoreDefaultUser, String[] excludedGroups) {
        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(FIND_BY_USERS_GROUPS);

        // Add ignore default user query if needed
        if (ignoreDefaultUser) {
            sql = StringUtil.replace(sql, PLACEHOLDER_DEFAULT_USER, "AND (User_.defaultUser != true)");
        } else {
            sql = StringUtil.replace(sql, PLACEHOLDER_DEFAULT_USER, StringPool.BLANK);
        }

        // If no excluded groups are set clear placeholders and return custom sql
        if (excludedGroups.length == 0) {
            return StringUtil.replace(sql,
                    new String[]{PLACEHOLDER_USERS_GROUPS_JOIN, PLACEHOLDER_USERS_GROUPS_WHERE},
                    new String[]{StringPool.BLANK, StringPool.BLANK});
        }

        // Otherwise, we need to build a query which will excluded proper sites
        StringBundler sb = new StringBundler(excludedGroups.length * 2 - 1);
        for (int i = 0; i < excludedGroups.length; i++) {
            // Add question mark so we can add parameters
            sb.append(StringPool.QUESTION);
            // Add comma if not at the end
            if ((i + 1) < excludedGroups.length) {
                sb.append(StringPool.COMMA);
            }
        }

        return StringUtil.replace(sql,
                new String[]{PLACEHOLDER_USERS_GROUPS_JOIN, PLACEHOLDER_USERS_GROUPS_WHERE},
                new String[]{
                        "INNER JOIN Group_ ON Group_.groupId = Users_Groups.groupId",
                        "AND Group_.name NOT IN (" + sb.toString() + ")"
                });
    }
}
