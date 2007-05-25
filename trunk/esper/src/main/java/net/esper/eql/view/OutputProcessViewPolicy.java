package net.esper.eql.view;

import net.esper.collection.MultiKey;
import net.esper.collection.MultiKeyUntyped;
import net.esper.collection.TransformEventMethod;
import net.esper.core.StatementContext;
import net.esper.eql.core.OrderBySorter;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.core.ResultSetProcessorResult;
import net.esper.eql.spec.OutputLimitSpec;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * A view that prepares output events, batching incoming
 * events and invoking the result set processor as necessary.
 * <p>
 * Handles output rate limiting or stabilizing.
 */
public class OutputProcessViewPolicy extends OutputProcessView
{
    private static final Log log = LogFactory.getLog(OutputProcessViewPolicy.class);
    
    private final OutputCondition outputCondition;

    /**
     * Ctor.
     * @param resultSetProcessor is processing the result set for publishing it out
     * @param streamCount is the number of streams, indicates whether or not this view participates in a join
     * @param outputLimitSpec is the specification for limiting output (the output condition and the result set processor)
     * @param statementContext is the services the output condition may depend on
     */
    public OutputProcessViewPolicy(ResultSetProcessor resultSetProcessor,
                                   OrderBySorter orderBySorter,
                                   int streamCount,
                                   OutputLimitSpec outputLimitSpec,
                                   StatementContext statementContext)
    {
        super(resultSetProcessor, orderBySorter);
        log.debug(".ctor");

    	if(streamCount < 1)
    	{
    		throw new IllegalArgumentException("Output process view is part of at least 1 stream");
    	}

    	OutputCallback outputCallback = getCallbackToLocal(streamCount);
    	this.outputCondition = OutputConditionFactory.createCondition(outputLimitSpec, statementContext, outputCallback);
    }

    /**
     * The update method is called if the view does not participate in a join.
     * @param newData - new events
     * @param oldData - old events
     */
    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        boolean hasNewData = resultSetProcessor.addViewResult(newData, oldData);

        int newDataLength = 0;
        int oldDataLength = 0;
        if(newData != null)
        {
        	newDataLength = newData.length;
        }
        if(oldData != null)
        {
        	oldDataLength = oldData.length;
        }

        outputCondition.updateOutputCondition(hasNewData, newDataLength, oldDataLength);
    }

    /**
     * This process (update) method is for participation in a join.
     * @param newEvents - new events
     * @param oldEvents - old events
     */
    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".process Received update, " +
                    "  newData.length==" + ((newEvents == null) ? 0 : newEvents.size()) +
                    "  oldData.length==" + ((oldEvents == null) ? 0 : oldEvents.size()));
        }

        boolean hasNewData = resultSetProcessor.addJoinResult(newEvents, oldEvents);

		outputCondition.updateOutputCondition(hasNewData, newEvents.size(), oldEvents.size());
    }

	/**
	 * Called once the output condition has been met.
	 * Invokes the result set processor.
	 * Used for non-join event data.
	 * @param doOutput - true if the batched events should actually be output as well as processed, false if they should just be processed
	 * @param forceUpdate - true if output should be made even when no updating events have arrived
	 * */
	protected void continueOutputProcessingView(boolean doOutput, boolean forceUpdate)
	{
		log.debug(".continueOutputProcessingView");

        ResultSetProcessorResult result = resultSetProcessor.generateResult();

        EventBean[] newEvents = null;
        EventBean[] oldEvents = null;

        if (result != null)
        {
            newEvents = result.getNewOut();
            oldEvents = result.getOldOut();

            // ordering
            if (orderBySorter != null)
            {
                MultiKeyUntyped[] newOrderKeys = result.getNewOrderKey();
                MultiKeyUntyped[] oldOrderKeys = result.getOldOrderKey();
                newEvents = orderBySorter.sort(newEvents, newOrderKeys);
                oldEvents = orderBySorter.sort(oldEvents, oldOrderKeys);
            }
        }
        
        if(doOutput)
		{
			output(forceUpdate, newEvents, oldEvents);
		}
	}

	private void output(boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents)
	{
		if(newEvents != null || oldEvents != null)
		{
			updateChildren(newEvents, oldEvents);
		}
		else if(forceUpdate)
		{
			updateChildren(null, null);
		}
	}

	/**
	 * Called once the output condition has been met.
	 * Invokes the result set processor.
	 * Used for join event data.
	 * @param doOutput - true if the batched events should actually be output as well as processed, false if they should just be processed
	 * @param forceUpdate - true if output should be made even when no updating events have arrived
	 */
	protected void continueOutputProcessingJoin(boolean doOutput, boolean forceUpdate)
	{
        this.continueOutputProcessingView(doOutput, forceUpdate);
    }

    private OutputCallback getCallbackToLocal(int streamCount)
    {
        // single stream means no join
        // multiple streams means a join
        if(streamCount == 1)
        {
            return new OutputCallback()
            {
                public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
                {
                    OutputProcessViewPolicy.this.continueOutputProcessingView(doOutput, forceUpdate);
                }
            };
        }
        else
        {
            return new OutputCallback()
            {
                public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
                {
                    OutputProcessViewPolicy.this.continueOutputProcessingJoin(doOutput, forceUpdate);
                }
            };
        }
    }

    /**
     * Method to transform an event based on the select expression.
     */
    public static class OutputProcessTransform implements TransformEventMethod
    {
        private final ResultSetProcessor resultSetProcessor;
        private final EventBean[] newData;

        /**
         * Ctor.
         * @param resultSetProcessor is applying the select expressions to the events for the transformation
         */
        public OutputProcessTransform(ResultSetProcessor resultSetProcessor) {
            this.resultSetProcessor = resultSetProcessor;
            newData = new EventBean[1];
        }

        public EventBean transform(EventBean event)
        {
            newData[0] = event;
            ResultSetProcessorResult pair = resultSetProcessor.processViewResult(newData, null);
            return pair.getNewOut()[0];
        }
    }
}
