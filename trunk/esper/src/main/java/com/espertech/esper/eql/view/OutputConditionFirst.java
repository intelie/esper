/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.view;

import com.espertech.esper.eql.spec.OutputLimitSpec;
import com.espertech.esper.eql.spec.OutputLimitLimitType;
import com.espertech.esper.core.StatementContext;

/**
 * An output condition that is satisfied at the first event
 * of either a time-based or count-based batch.
 */
public class OutputConditionFirst implements OutputCondition
{
	private final OutputCallback outputCallback;
	private final OutputCondition innerCondition;
	private boolean witnessedFirst;

	/**
	 * Ctor.
     * @param outputLimitSpec specifies what kind of condition to create
     * @param statementContext supplies the services required such as for scheduling callbacks
     * @param outputCallback is the method to invoke for output
	 */
	public OutputConditionFirst(OutputLimitSpec outputLimitSpec, StatementContext statementContext, OutputCallback outputCallback)
	{
		if(outputCallback ==  null)
		{
			throw new NullPointerException("Output condition by count requires a non-null callback");
		}
		this.outputCallback = outputCallback;
		OutputLimitSpec innerSpec = new OutputLimitSpec(outputLimitSpec.getRate(), outputLimitSpec.getVariableName(), outputLimitSpec.getRateType(), OutputLimitLimitType.DEFAULT);
		OutputCallback localCallback = createCallbackToLocal();
		this.innerCondition = statementContext.getOutputConditionFactory().createCondition(innerSpec, statementContext, localCallback);
		this.witnessedFirst = false;
	}

	public void updateOutputCondition(int newEventsCount, int oldEventsCount)
	{
		if(!witnessedFirst)
		{
			witnessedFirst = true;
			boolean doOutput = true;
			boolean forceUpdate = false;
			outputCallback.continueOutputProcessing(doOutput, forceUpdate);
		}
		innerCondition.updateOutputCondition(newEventsCount, oldEventsCount);
	}

	private OutputCallback createCallbackToLocal()
	{
		return new OutputCallback()
		{
			public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
			{
				OutputConditionFirst.this.continueOutputProcessing(forceUpdate);
			}
		};
	}

	private void continueOutputProcessing(boolean forceUpdate)
	{
		boolean doOutput = !witnessedFirst;
		outputCallback.continueOutputProcessing(doOutput, forceUpdate);
		witnessedFirst = false;
	}
}
