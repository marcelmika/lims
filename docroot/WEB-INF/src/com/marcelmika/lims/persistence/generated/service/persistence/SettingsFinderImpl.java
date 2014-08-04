package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

    public static final String FIND_ALL_USERS = SettingsFinder.class.getName() + ".findAllUsers";

    /**
     * Returns all users except the one given in the parameter and their settings
     *
     * @param userId Excluded user
     * @return List of users in object
     */
    public List<Object[]> findAllGroups(Long userId, int start, int end) throws Exception {

        Session session = null;

        try {

            // Open database session
            session = openSession();
            // Get custom query (check /src/custom-sql/default.xml)
            String sql = CustomSQLUtil.get(FIND_ALL_USERS);
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

            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);
        } catch (Exception e) {
            // Log
            log.error("----PERSISTENCE ERROR");
            log.error(e);
            log.error("----PERSISTENCE ERROR");
            throw e;
        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }
}
