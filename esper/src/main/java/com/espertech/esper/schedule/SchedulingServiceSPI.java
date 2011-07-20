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

package com.espertech.esper.schedule;

import java.util.Map;
import java.util.Set;

/**
 * Service provider interface for scheduling service.
 */
public interface SchedulingServiceSPI extends SchedulingService
{
    /**
     * Take a statement's schedules out of the currently active set of schedules.
     * @param statementId statements to take out
     * @return schedules
     */
    public ScheduleSet take(Set<String> statementId);

    /**
     * Apply the set of schedules.
     * @param scheduleSet to apply
     */
    public void apply(ScheduleSet scheduleSet);

    public Long getNearestTimeHandle();

    public Map<String, Long> getStatementSchedules();    
}
