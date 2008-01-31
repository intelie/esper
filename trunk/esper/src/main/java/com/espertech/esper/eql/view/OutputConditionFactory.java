package com.espertech.esper.eql.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.eql.spec.OutputLimitSpec;
import com.espertech.esper.core.StatementContext;

/**
 * Factory for output condition instances.
 */
public interface OutputConditionFactory
{
    /**
     * Creates an output condition instance.
     * @param outputLimitSpec specifies what kind of condition to create
     * @param statementContext supplies the services required such as for scheduling callbacks
     * @param outputCallback is the method to invoke for output
     * @return instance for performing output
     */
	public OutputCondition createCondition(OutputLimitSpec outputLimitSpec,
										   StatementContext statementContext,
										   OutputCallback outputCallback);
}
