/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.matchmaker.monitor;

import java.util.HashSet;

import com.espertech.esper.client.*;
import com.espertech.esper.example.matchmaker.eventbean.MobileUserBean;
import com.espertech.esper.example.matchmaker.eventbean.MatchAlertBean;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class MatchMakingMonitor
{
    public static final double PROXIMITY_RANGE = 1;

    private static HashSet<Integer> existingUsers = new HashSet<Integer>();
    private static EPServiceProvider epService;

    private int mobileUserId;
    private EPStatement locateOther;
    private MatchAlertListener matchAlertListener;

    public MatchMakingMonitor(final MatchAlertListener matchAlertListener)
    {
        this.matchAlertListener = matchAlertListener;
        
        // This code runs as part of the automated regression test suite; Therefore disable internal timer theading to safe resources
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);

        // Get called for any user showing up
        EPStatement factory = epService.getEPAdministrator().createPattern("every user=" + MobileUserBean.class.getName());

        factory.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                MobileUserBean user = (MobileUserBean) newEvents[0].get("user");

                // No action if user already known
                if (existingUsers.contains(user.getUserId()))
                {
                    return;
                }

                log.debug(".update New user encountered, user=" + user.getUserId());

                existingUsers.add(user.getUserId());
                new MatchMakingMonitor(user, matchAlertListener);
            }
        });
    }

    public MatchMakingMonitor(MobileUserBean mobileUser, MatchAlertListener matchAlertListener)
    {
        this.matchAlertListener = matchAlertListener;
        this.mobileUserId = mobileUser.getUserId();

        // Create patterns that listen to other users
        setupPatterns(mobileUser);

        // Listen to my own location changes so my data is up-to-date
        EPStatement locationChange = epService.getEPAdministrator().createPattern(
                "every myself=" + MobileUserBean.class.getName() +
                "(userId=" + mobileUser.getUserId() + ")");

        locationChange.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                // When my location changed, re-establish pattern
                locateOther.removeAllListeners();
                MobileUserBean myself = (MobileUserBean) newEvents[0].get("myself");
                MatchMakingMonitor.this.setupPatterns(myself);
            }
        });
    }

    private void setupPatterns(MobileUserBean mobileUser)
    {
        double locXLow = mobileUser.getLocationX() - PROXIMITY_RANGE;
        double locXHigh = mobileUser.getLocationX() + PROXIMITY_RANGE;
        double locYLow = mobileUser.getLocationY() - PROXIMITY_RANGE;
        double locYHigh = mobileUser.getLocationY() + PROXIMITY_RANGE;

        this.locateOther = epService.getEPAdministrator().createPattern(
                "every other=" + MobileUserBean.class.getName() +
                "(locationX in [" + locXLow + ":" + locXHigh + "]," +
                "locationY in [" + locYLow + ":" + locYHigh + "]," +
                "myGender='" + mobileUser.getPreferredGender() + "'," +
                "myAgeRange='" + mobileUser.getPreferredAgeRange() + "'," +
                "myHairColor='" + mobileUser.getPreferredHairColor() + "'," +
                "preferredGender='" + mobileUser.getMyGender() + "'," +
                "preferredAgeRange='" + mobileUser.getMyAgeRange() + "'," +
                "preferredHairColor='" + mobileUser.getMyHairColor() + "'" +
                ")");

        locateOther.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                MobileUserBean other = (MobileUserBean) newEvents[0].get("other");
                MatchAlertBean alert = new MatchAlertBean(other.getUserId(), MatchMakingMonitor.this.mobileUserId);
                matchAlertListener.emitted(alert);
            }
        });
    }

    private static final Log log = LogFactory.getLog(MatchMakingMonitor.class);
}
