package com.espertech.esper.eql.view;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.eql.core.ResultSetProcessor;
import com.espertech.esper.eql.spec.OutputLimitLimitType;
import com.espertech.esper.eql.spec.OutputLimitSpec;
import com.espertech.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * A view that prepares output events, batching incoming
 * events and invoking the result set processor as necessary.
 * <p>
 * Handles output rate limiting or stabilizing.
 */
public class OutputProcessViewPolicy extends OutputProcessView
{
    private final OutputCondition outputCondition;
    private final boolean outputSnapshot;

    // Posted events in ordered form (for applying to aggregates) and summarized per type
    private List<EventBean> newEventsList = new ArrayList<EventBean>();
	private List<EventBean> oldEventsList = new ArrayList<EventBean>();
	private Set<MultiKey<EventBean>> newEventsSet = new LinkedHashSet<MultiKey<EventBean>>();
	private Set<MultiKey<EventBean>> oldEventsSet = new LinkedHashSet<MultiKey<EventBean>>();

	private static final Log log = LogFactory.getLog(OutputProcessViewPolicy.class);

    /**
     * Ctor.
     * @param resultSetProcessor is processing the result set for publishing it out
     * @param streamCount is the number of streams, indicates whether or not this view participates in a join
     * @param outputLimitSpec is the specification for limiting output (the output condition and the result set processor)
     * @param statementContext is the services the output condition may depend on
     * @param isInsertInto is true if the statement is a insert-into
     * @param outputStrategy is the method to use to produce output
     */
    public OutputProcessViewPolicy(ResultSetProcessor resultSetProcessor,
                          OutputStrategy outputStrategy,
                          boolean isInsertInto,
                          int streamCount,
    					  OutputLimitSpec outputLimitSpec,
    					  StatementContext statementContext)
    {
        super(resultSetProcessor, outputStrategy, isInsertInto, statementContext.getStatementResultService());
        log.debug(".ctor");

    	if(streamCount < 1)
    	{
    		throw new IllegalArgumentException("Output process view is part of at least 1 stream");
    	}

    	OutputCallback outputCallback = getCallbackToLocal(streamCount);
    	this.outputCondition = statementContext.getOutputConditionFactory().createCondition(outputLimitSpec, statementContext, outputCallback);
        this.outputSnapshot = (outputLimitSpec != null) && (outputLimitSpec.getDisplayLimit() == OutputLimitLimitType.SNAPSHOT);
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

        // add the incoming events to the event batches
        int newDataLength = 0;
        int oldDataLength = 0;
        if(newData != null)
        {
        	newDataLength = newData.length;
        	for(EventBean event : newData)
        	{
        		newEventsList.add(event);
        	}
        }
        if(oldData != null)
        {
        	oldDataLength = oldData.length;
        	for(EventBean event : oldData)
        	{
        		oldEventsList.add(event);
        	}
        }

        outputCondition.updateOutputCondition(newDataLength, oldDataLength);
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

        int newEventsSize = 0;
        if (newEvents != null)
        {
            // add the incoming events to the event batches
            newEventsSize = newEvents.size();
            for(MultiKey<EventBean> event : newEvents)
            {
                newEventsSet.add(event);
            }
        }

        int oldEventsSize = 0;
        if (oldEvents != null)
        {
            oldEventsSize = oldEvents.size();
            for(MultiKey<EventBean> event : oldEvents)
            {
                oldEventsSet.add(event);
            }
        }

        outputCondition.updateOutputCondition(newEventsSize, oldEventsSize);
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

		// Get the arrays of new and old events, or null if none
		EventBean[] newEvents = !newEventsList.isEmpty() ? newEventsList.toArray(new EventBean[0]) : null;
		EventBean[] oldEvents = !oldEventsList.isEmpty() ? oldEventsList.toArray(new EventBean[0]) : null;

        boolean isGenerateSynthetic = statementResultService.isMakeSynthetic();
        boolean isGenerateNatural = statementResultService.isMakeNatural();

        // Process the events and get the result
        Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.processViewResult(newEvents, oldEvents, isGenerateSynthetic);

        if ((!isGenerateSynthetic) && (!isGenerateNatural))
        {
            return;
        }

        if (outputSnapshot)
        {
            Iterator<EventBean> it = this.iterator();
            if (it.hasNext())
            {
                ArrayList<EventBean> snapshot = new ArrayList<EventBean>();
                for (EventBean bean : this)
                {
                    snapshot.add(bean);
                }
                newEvents = snapshot.toArray(new EventBean[0]);
                oldEvents = null;
            }
            else
            {
                newEvents = null;
                oldEvents = null;
            }
            newOldEvents = new Pair<EventBean[], EventBean[]>(newEvents, oldEvents); 
        }

        if(doOutput)
		{
			output(forceUpdate, newOldEvents);
		}
		resetEventBatches();
	}

	private void output(boolean forceUpdate, Pair<EventBean[], EventBean[]> results)
	{
        // Child view can be null in replay from named window
        if (childView != null)
        {
            outputStrategy.output(forceUpdate, results, childView);
        }
	}

	private void resetEventBatches()
	{
		newEventsList.clear();
		oldEventsList.clear();
		newEventsSet.clear();
		oldEventsSet.clear();
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
		log.debug(".continueOutputProcessingJoin");

        boolean isGenerateSynthetic = statementResultService.isMakeSynthetic();
        boolean isGenerateNatural = statementResultService.isMakeNatural();

        // Process the events and get the result
        Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.processJoinResult(newEventsSet, oldEventsSet, isGenerateSynthetic);

        if ((!isGenerateSynthetic) && (!isGenerateNatural))
        {
            return;
        }

		if(doOutput)
		{
			output(forceUpdate, newOldEvents);
		}
		resetEventBatches();
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
}
