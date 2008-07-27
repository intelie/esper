package com.espertech.esper.epl.view;

import com.espertech.esper.epl.spec.OutputLimitSpec;
import com.espertech.esper.epl.expression.ExprValidationException;
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
     * @throws  ExprValidationException if validation of the output expressions fails
     */
	public OutputCondition createCondition(OutputLimitSpec outputLimitSpec,
										   StatementContext statementContext,
										   OutputCallback outputCallback)
            throws ExprValidationException;
}
