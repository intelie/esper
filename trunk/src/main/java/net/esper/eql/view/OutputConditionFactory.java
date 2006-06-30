package net.esper.eql.view;

import net.esper.eql.expression.OutputLimitSpec;
import net.esper.view.ViewServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for output condition instances.
 */
public final class OutputConditionFactory {
	private static final Log log = LogFactory.getLog(OutputConditionFactory.class);

    /**
     * Creates an output condition instance.
     * @param outputLimitSpec specifies what to create
     * @param viewContext supplies the services required such as for scheduling callbacks
     * @param outputCallback is the method to invoke for output
     * @return instance for performing output
     */
	public static OutputCondition createCondition(OutputLimitSpec outputLimitSpec,
										 	  	  ViewServiceContext viewContext, 
										 	      OutputCallback outputCallback)
	{
		if(outputLimitSpec == null)
		{
			return new OutputConditionNull(outputCallback);
		}
				
		if (outputLimitSpec.isEventLimit())
		{
			log.debug(".createCondition creating OutputConditionCount with event rate " + outputLimitSpec.getEventRate());
			return new OutputConditionCount(outputLimitSpec.getEventRate(), outputCallback);
		}
		else 
		{
			log.debug(".createCondition creating OutputConditionTime with interval length " + outputLimitSpec.getTimeRate());
			return new OutputConditionTime(outputLimitSpec.getTimeRate(), viewContext, outputCallback);
		}
	}
}
