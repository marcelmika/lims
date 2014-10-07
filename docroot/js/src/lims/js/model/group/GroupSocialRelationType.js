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

/**
 * Group Social Relation Type
 *
 * Represent a social relation type
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupSocialRelationType = Y.Base.create('groupSocialRelationType', Y.Model, [], {


    /**
     * Returns localized name of the social relation type. Empty string is returned if
     * unknown relation types is set
     *
     * @returns {string}
     */
    getLocalizedName: function () {
        // Vars
        var socialRelationType = this.get('socialRelationType');

        // Check which relation type we have and return proper localized name of the
        // social relation type
        switch (socialRelationType) {

            // Unknown
            case 'TYPE_BI_UNKNOWN':
                return Y.LIMS.Core.i18n.values.socialRelationUnknown;

            // Connections
            case 'TYPE_BI_CONNECTION':
                return Y.LIMS.Core.i18n.values.socialRelationConnection;

            // Coworkers
            case 'TYPE_BI_COWORKER':
                return Y.LIMS.Core.i18n.values.socialRelationCoworker;
            // Friends
            case 'TYPE_BI_FRIEND':
                return Y.LIMS.Core.i18n.values.socialRelationFriend;

            // Romantic partner
            case 'TYPE_BI_ROMANTIC_PARTNER':
                return Y.LIMS.Core.i18n.values.socialRelationRomanticPartner;

            // Sibling
            case 'TYPE_BI_SIBLING':
                return Y.LIMS.Core.i18n.values.socialRelationSibling;

            // Unknown relation type was set
            default:
                return '';
        }
    }


}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Type of the social relation
         *
         * {string}
         */
        socialRelationType: {
            value: null
        }
    }

});

