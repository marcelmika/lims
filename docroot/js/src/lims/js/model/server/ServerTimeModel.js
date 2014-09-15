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
 * Server Time Model
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ServerTimeModel = Y.Base.create('settingsModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    // Custom sync layer
    sync: function (action, options, callback) {

        var response,           // Response from server
            timeBeforeRequest,  // Current time before the request
            timeAfterResponse,  // Current time after the response
            networkDelayOffset, // Delta between before request and after response time
            instance = this;    // Save scope

        switch (action) {
            case 'read':

                // Remember the time before the request. Thanks to that we can
                // count the network delay in.
                timeBeforeRequest = new Date().getTime();

                // Do the request
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "GetServerTime"
                    },
                    on: {
                        success: function (id, o) {
                            // Remember current time
                            timeAfterResponse = new Date().getTime();
                            // Count the delay offset
                            networkDelayOffset = timeAfterResponse - timeBeforeRequest;

                            // Deserialize response
                            response = Y.JSON.parse(o.response);

                            // Update attributes
                            instance.setAttrs(response);

                            // Count the network delay in
                            instance.set('time', instance.get('time') + networkDelayOffset);

                            if (callback) {
                                callback(null, instance);
                            }
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            if (callback) {
                                callback("Cannot read server time", o.response);
                            }
                        }
                    }
                });

                break;

            case 'create':
            case 'update':
            case 'delete':
                break;
        }
    }

}, {
    ATTRS: {

        time: {
            // to be set
        }
    }
});
