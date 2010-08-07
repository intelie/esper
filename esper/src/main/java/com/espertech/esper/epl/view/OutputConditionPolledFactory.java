/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.view;

import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.OutputLimitRateType;
import com.espertech.esper.epl.spec.OutputLimitSpec;
import com.espertech.esper.epl.variable.VariableReader;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for output condition instances that are polled/queried only.
 */
public class OutputConditionPolledFactory
{
	private static final Log log = LogFactory.getLog(OutputConditionPolledFactory.class);

    /**
     * Creates an output condition instance.
     * @param outputLimitSpec specifies what kind of condition to create
     * @param statementContext supplies the services required such as for scheduling callbacks
     * @return instance for handling output condition
     */
	public static OutputConditionPolled createCondition(OutputLimitSpec outputLimitSpec,
										 	  	  StatementContext statementContext)
            throws ExprValidationException
    {
		if(outputLimitSpec == null)
		{
			throw new NullPointerException("Output condition by count requires a non-null callback");
		}

        // Check if a variable is present
        VariableReader reader = null;
        if (outputLimitSpec.getVariableName() != null)
        {
            reader = statementContext.getVariableService().getReader(outputLimitSpec.getVariableName());
            if (reader == null)
            {
                throw new IllegalArgumentException("Variable named '" + outputLimitSpec.getVariableName() + "' has not been declared");
            }
        }

        if(outputLimitSpec.getRateType() == OutputLimitRateType.CRONTAB)
        {
            return new OutputConditionPolledCrontab(outputLimitSpec.getCrontabAtSchedule(), statementContext);
        }
        else if(outputLimitSpec.getRateType() == OutputLimitRateType.WHEN_EXPRESSION)
        {
            return new OutputConditionPolledExpression(outputLimitSpec.getWhenExpressionNode(), outputLimitSpec.getThenExpressions(), statementContext);
        }
        else if(outputLimitSpec.getRateType() == OutputLimitRateType.EVENTS)
		{
            if (log.isDebugEnabled())
            {
			    log.debug(".createCondition creating OutputConditionCount with event rate " + outputLimitSpec);
            }

            if ((reader != null) && (!JavaClassHelper.isNumericNonFP(reader.getType())))
            {
                throw new IllegalArgumentException("Variable named '" + outputLimitSpec.getVariableName() + "' must be type integer, long or short");
            }

            int rate = -1;
            if (outputLimitSpec.getRate() != null)
            {
                rate = outputLimitSpec.getRate().intValue();
            }
            return new OutputConditionPolledCount(rate, reader);
		}
		else
		{
            if ((reader != null) && (!JavaClassHelper.isNumeric(reader.getType())))
            {
                throw new IllegalArgumentException("Variable named '" + outputLimitSpec.getVariableName() + "' must be of numeric type");
            }

            return new OutputConditionPolledTime(outputLimitSpec.getTimePeriodExpr(), statementContext);
		}
	}
}