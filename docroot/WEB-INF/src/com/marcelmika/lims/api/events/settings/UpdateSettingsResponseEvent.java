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

package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.entity.SettingsDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:17 PM
 */
public class UpdateSettingsResponseEvent extends ResponseEvent {

    private Status status;
    private SettingsDetails settingsDetails;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private UpdateSettingsResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static UpdateSettingsResponseEvent updateSettingsSuccess(final SettingsDetails settingsDetails) {
        UpdateSettingsResponseEvent event = new UpdateSettingsResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.settingsDetails = settingsDetails;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static UpdateSettingsResponseEvent updateSettingsFailure(final Status status) {
        UpdateSettingsResponseEvent event = new UpdateSettingsResponseEvent();

        event.success = false;
        event.status = status;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status    Status
     * @param exception Exception
     * @return ResponseEvent
     */
    public static UpdateSettingsResponseEvent updateSettingsFailure(final Status status,
                                                                    final Throwable exception) {

        UpdateSettingsResponseEvent event = new UpdateSettingsResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }
}
