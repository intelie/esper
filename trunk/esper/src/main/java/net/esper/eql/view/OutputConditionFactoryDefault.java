package net.esper.eql.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.eql.spec.OutputLimitSpec;
import net.esper.eql.spec.OutputLimitLimitType;
import net.esper.eql.spec.OutputLimitRateType;
import net.esper.eql.variable.VariableReader;
import net.esper.core.StatementContext;
import net.esper.util.JavaClassHelper;

/**
 * Factory for output condition instances.
 */
public class OutputConditionFactoryDefault implements OutputConditionFactory
{
	private static final Log log = LogFactory.getLog(OutputConditionFactoryDefault.class);

    /**
     * Creates an output condition instance.
     * @param outputLimitSpec specifies what kind of condition to create
     * @param statementContext supplies the services required such as for scheduling callbacks
     * @param outputCallback is the method to invoke for output
     * @return instance for performing output
     */
	public OutputCondition createCondition(OutputLimitSpec outputLimitSpec,
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
        
        if(outputLimitSpec.getDisplayLimit() == OutputLimitLimitType.FIRST)
		{
			log.debug(".createCondition creating OutputConditionFirst");
			return new OutputConditionFirst(outputLimitSpec, statementContext, outputCallback);
		}
        
        if(outputLimitSpec.getRateType() == OutputLimitRateType.EVENTS)
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
            return new OutputConditionCount(rate, reader, outputCallback);
		}
		else
		{
            if (log.isDebugEnabled())
            {
                log.debug(".createCondition creating OutputConditionTime with interval length " + outputLimitSpec.getRate());
            }
            if ((reader != null) && (!JavaClassHelper.isNumeric(reader.getType())))
            {
                throw new IllegalArgumentException("Variable named '" + outputLimitSpec.getVariableName() + "' must be of numeric type");
            }

            boolean isMinutesUnit = outputLimitSpec.getRateType() == OutputLimitRateType.TIME_MIN;
            return new OutputConditionTime(outputLimitSpec.getRate(), isMinutesUnit, reader, statementContext, outputCallback);
		}
	}
}
