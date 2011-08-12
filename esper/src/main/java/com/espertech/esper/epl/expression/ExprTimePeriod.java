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

package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.datetime.eval.TimePeriod;

/**
 * Expression representing a time period.
 * <p>
 * Child nodes to this expression carry the actual parts and must return a numeric value.
 */
public interface ExprTimePeriod extends ExprNode, ExprEvaluator
{
    public boolean hasVariable();
    public TimePeriod evaluateGetTimePeriod(EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context);
    
    /**
     * Indicator whether the time period has a day part child expression.
     * @return true for part present, false for not present
     */
    public boolean isHasDay();

    /**
     * Indicator whether the time period has a hour part child expression.
     * @return true for part present, false for not present
     */
    public boolean isHasHour();

    /**
     * Indicator whether the time period has a minute part child expression.
     * @return true for part present, false for not present
     */
    public boolean isHasMinute();

    /**
     * Indicator whether the time period has a second part child expression.
     * @return true for part present, false for not present
     */
    public boolean isHasSecond();

    /**
     * Indicator whether the time period has a millisecond part child expression.
     * @return true for part present, false for not present
     */
    public boolean isHasMillisecond();

    /**
     * Indicator whether the time period has a year part child expression.
     * @return true for part present, false for not present
     */
    public boolean isHasYear();

    /**
     * Indicator whether the time period has a month part child expression.
     * @return true for part present, false for not present
     */
    public boolean isHasMonth();

    /**
     * Indicator whether the time period has a week part child expression.
     * @return true for part present, false for not present
     */
    public boolean isHasWeek();
}
