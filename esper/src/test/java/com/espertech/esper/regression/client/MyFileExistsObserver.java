/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.client;

import com.espertech.esper.pattern.observer.EventObserver;
import com.espertech.esper.pattern.observer.ObserverEventEvaluator;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.schedule.ScheduleSlot;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.io.File;

public class MyFileExistsObserver implements EventObserver
{
    private final MatchedEventMap beginState;
    private final ObserverEventEvaluator observerEventEvaluator;
    private final String filename;

    public MyFileExistsObserver(MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, String filename)
    {
        this.beginState = beginState;
        this.observerEventEvaluator = observerEventEvaluator;
        this.filename = filename;
    }

    public void startObserve()
    {
        File file = new File(filename);
        if (file.exists())
        {
            observerEventEvaluator.observerEvaluateTrue(beginState);
        }
        else
        {
            observerEventEvaluator.observerEvaluateFalse();
        }
    }

    public void stopObserve()
    {
        // this is called when the subexpression quits or the pattern is stopped
        // no action required
    }
}
