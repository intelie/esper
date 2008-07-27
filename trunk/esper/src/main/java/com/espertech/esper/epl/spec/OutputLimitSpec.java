/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.util.List;

/**
 * Spec for defining an output rate
 */
public class OutputLimitSpec implements MetaDefItem
{
	private final OutputLimitLimitType displayLimit;
    private final OutputLimitRateType rateType;
    private final Double rate;
    private final String variableName;
    private ExprNode whenExpressionNode;
    private final List<OnTriggerSetAssignment> thenExpressions;    
    private final Object[] crontabAtSchedule;

    /**
	 * Ctor.
	 * 	 For batching events by event count.
     * @param rate is the fixed output rate, or null if by variable
	 * @param displayLimit - indicates whether to output only the first, only the last, or all events
     * @param variableForRate - an optional variable name instead of the rate
     * @param rateType - type of the rate
     * @param whenExpressionNode - for controlling output by a boolean expression
     * @param thenExpressions variable assignments, if null if none
     * @param crontabAtSchedule - crontab parameters
     */
    public OutputLimitSpec(Double rate, String variableForRate, OutputLimitRateType rateType, OutputLimitLimitType displayLimit, ExprNode whenExpressionNode, List<OnTriggerSetAssignment> thenExpressions, Object[] crontabAtSchedule)
	{
		this.rate = rate;
		this.displayLimit = displayLimit;
        this.variableName = variableForRate;
        this.rateType = rateType;
        this.crontabAtSchedule = crontabAtSchedule;
        this.whenExpressionNode = whenExpressionNode;
        this.thenExpressions = thenExpressions;
    }

    /**
     * Returns the type of output limit.
     * @return limit
     */
    public OutputLimitLimitType getDisplayLimit()
    {
        return displayLimit;
    }

    /**
     * Returns the type of rate.
     * @return rate type
     */
    public OutputLimitRateType getRateType()
    {
        return rateType;
    }

    /**
     * Returns the rate, or null or -1 if a variable is used instead
     * @return rate if set
     */
    public Double getRate()
    {
        return rate;
    }

    /**
     * Returns the variable name if set, or null if a fixed rate
     * @return variable name
     */
    public String getVariableName()
    {
        return variableName;
    }

    /**
     * Returns the when-keyword trigger expression, or null if not using when.
     * @return expression
     */
    public ExprNode getWhenExpressionNode()
    {
        return whenExpressionNode;
    }

    /**
     * Returns crontab parameters, or null if not using crontab-at output.
     * @return schedule parameters
     */
    public Object[] getCrontabAtSchedule()
    {
        return crontabAtSchedule;
    }

    /**
     * Sets a new when-keyword trigger expression.
     * @param whenExpressionNode to set
     */
    public void setWhenExpressionNode(ExprNode whenExpressionNode)
    {
        this.whenExpressionNode = whenExpressionNode;
    }

    /**
     * Returns a list of variable assignments, or null if none made.
     * @return variable assignments
     */
    public List<OnTriggerSetAssignment> getThenExpressions()
    {
        return thenExpressions;
    }
}
