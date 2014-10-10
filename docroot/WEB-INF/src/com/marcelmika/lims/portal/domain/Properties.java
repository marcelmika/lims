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

package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.lims.api.environment.Environment.BuddyListSocialRelation;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 08/10/14
 * Time: 21:39
 */
public class Properties {

    private BuddyListStrategy buddyListStrategy;
    private BuddyListSocialRelation[] buddyListSocialRelations;
    private boolean buddyListIgnoreDefaultUser;
    private boolean buddyListIgnoreDeactivatedUser;
    private int buddyListMaxBuddies;
    private int buddyListMaxSearch;
    private int conversationListMaxMessages;

    /**
     * Factory method that creates an instance of properties from the environment properties
     *
     * @return Properties
     */
    public static Properties fromEnvironment() {
        // Create new instance
        Properties properties = new Properties();

        // Map properties
        properties.buddyListStrategy = Environment.getBuddyListStrategy();
        properties.buddyListSocialRelations = Environment.getBuddyListSocialRelations();
        properties.buddyListIgnoreDefaultUser = Environment.getBuddyListIgnoreDefaultUser();
        properties.buddyListIgnoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        properties.buddyListMaxBuddies = Environment.getBuddyListMaxBuddies();
        properties.buddyListMaxSearch = Environment.getBuddyListMaxSearch();
        properties.conversationListMaxMessages = Environment.getConversationListMaxMessages();

        return properties;
    }

    public BuddyListStrategy getBuddyListStrategy() {
        return buddyListStrategy;
    }

    public void setBuddyListStrategy(BuddyListStrategy buddyListStrategy) {
        this.buddyListStrategy = buddyListStrategy;
    }

    public BuddyListSocialRelation[] getBuddyListSocialRelations() {
        return buddyListSocialRelations;
    }

    public void setBuddyListSocialRelations(BuddyListSocialRelation[] buddyListSocialRelations) {
        this.buddyListSocialRelations = buddyListSocialRelations;
    }

    public boolean isBuddyListIgnoreDefaultUser() {
        return buddyListIgnoreDefaultUser;
    }

    public void setBuddyListIgnoreDefaultUser(boolean buddyListIgnoreDefaultUser) {
        this.buddyListIgnoreDefaultUser = buddyListIgnoreDefaultUser;
    }

    public boolean isBuddyListIgnoreDeactivatedUser() {
        return buddyListIgnoreDeactivatedUser;
    }

    public void setBuddyListIgnoreDeactivatedUser(boolean buddyListIgnoreDeactivatedUser) {
        this.buddyListIgnoreDeactivatedUser = buddyListIgnoreDeactivatedUser;
    }

    public int getBuddyListMaxBuddies() {
        return buddyListMaxBuddies;
    }

    public void setBuddyListMaxBuddies(int buddyListMaxBuddies) {
        this.buddyListMaxBuddies = buddyListMaxBuddies;
    }

    public int getBuddyListMaxSearch() {
        return buddyListMaxSearch;
    }

    public void setBuddyListMaxSearch(int buddyListMaxSearch) {
        this.buddyListMaxSearch = buddyListMaxSearch;
    }

    public int getConversationListMaxMessages() {
        return conversationListMaxMessages;
    }

    public void setConversationListMaxMessages(int conversationListMaxMessages) {
        this.conversationListMaxMessages = conversationListMaxMessages;
    }
}
