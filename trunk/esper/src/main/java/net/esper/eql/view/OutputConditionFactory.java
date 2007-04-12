/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.view;

import net.esper.eql.spec.OutputLimitSpec;
import net.esper.core.StatementContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for output condition instances.
 */
public final class OutputConditionFactory {
	private static final Log log = LogFactory.getLog(OutputConditionFactory.class);

    /**
     * Creates an output condition instance.
     * @param outputLimitSpec specifies what kind of condition to create
     * @param statementContext supplies the services required such as for scheduling callbacks
     * @param outputCallback is the method to invoke for output
     * @return instance for performing output
     */
	public static OutputCondition createCondition(OutputLimitSpec outputLimitSpec,
										 	  	  StatementContext statementContext,
										 	      OutputCallback outputCallback)
	{
		if(outputCallback ==  null)
		{
			throw new NullPointerException("Output condition by count requires a non-null callback");
		}

		if(outputLimitSpec == null)
		{
			return new OutputConditionNull(outputCallback);
		}
		else if(outputLimitSpec.isDisplayFirstOnly())
		{
			log.debug(".createCondition creating OutputConditionFirst");
			return new OutputConditionFirst(outputLimitSpec, statementContext, outputCallback);
		}
		if(outputLimitSpec.isEventLimit())
		{
			log.debug(".createCondition creating OutputConditionCount with event rate " + outputLimitSpec.getEventRate());
			return new OutputConditionCount(outputLimitSpec.getEventRate(), outputCallback);
		}
		else
		{
			log.debug(".createCondition creating OutputConditionTime with interval length " + outputLimitSpec.getTimeRate());
			return new OutputConditionTime(outputLimitSpec.getTimeRate(), statementContext, outputCallback);
		}
	}
}