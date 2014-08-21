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

package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.PaginationDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 8:15 PM
 */
public class Pagination {

    private Integer firstMessageId;
    private Integer lastMessageId;
    private Action action;

    public enum Action {
        NEXT("next"),
        PREV("prev");

        private String action;

        private Action(String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }
    }

    public static Pagination fromPaginationDetails(PaginationDetails details) {
        Pagination pagination = new Pagination();

        pagination.firstMessageId = details.getFirstMessageId();
        pagination.lastMessageId = details.getLastMessageId();

        if (details.getAction() == PaginationDetails.ActionDetails.NEXT) {
            pagination.setAction(Action.NEXT);
        } else if (details.getAction() == PaginationDetails.ActionDetails.PREV) {
            pagination.setAction(Action.PREV);
        }

        return pagination;
    }

    public Integer getFirstMessageId() {
        return firstMessageId;
    }

    public void setFirstMessageId(Integer firstMessageId) {
        this.firstMessageId = firstMessageId;
    }

    public Integer getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(Integer lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
