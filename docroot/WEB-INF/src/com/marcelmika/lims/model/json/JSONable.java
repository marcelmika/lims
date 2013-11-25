/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcelmika.lims.model.json;

import com.liferay.portal.kernel.json.JSONObject;

/**
 *
 * @author Bc. Marcel Mika <email@marcelmika.com>
 */
public interface JSONable {
    public JSONObject toJSON();
}
