package net.esper.eql.view;

import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.collection.UniformPair;
import net.esper.core.StatementContext;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.spec.OutputLimitLimitType;
import net.esper.eql.spec.OutputLimitSpec;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
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
    private final boolean outputLastOnly;
    private final OutputCondition outputCondition;
    private final boolean outputSnapshot;

    // Posted events in ordered form (for applying to aggregates) and summarized per type
    private ArrayList<UniformPair<EventBean[]>> newEventsList = new ArrayList<UniformPair<EventBean[]>>();
	private ArrayList<UniformPair<Set<MultiKey<EventBean>>>> newEventsSet = new ArrayList<UniformPair<Set<MultiKey<EventBean>>>>();

	private static final Log log = LogFactory.getLog(OutputProcessViewPolicy.class);

    /**
     * Ctor.
     * @param resultSetProcessor is processing the result set for publishing it out
     * @param streamCount is the number of streams, indicates whether or not this view participates in a join
     * @param outputLimitSpec is the specification for limiting output (the output condition and the result set processor)
     * @param statementContext is the services the output condition may depend on
     * @param outputStrategy is the execution of output to sub-views or natively
     */
    public OutputProcessViewPolicy(ResultSetProcessor resultSetProcessor, OutputStrategy outputStrategy,
                                   boolean isInsertInto,
                                   int streamCount,
                                   OutputLimitSpec outputLimitSpec,
                                   StatementContext statementContext)
    {
        super(resultSetProcessor, outputStrategy, isInsertInto);
        log.debug(".ctor");

    	if(streamCount < 1)
    	{
    		throw new IllegalArgumentException("Output process view is part of at least 1 stream");
    	}

    	OutputCallback outputCallback = getCallbackToLocal(streamCount);
    	this.outputCondition = statementContext.getOutputConditionFactory().createCondition(outputLimitSpec, statementContext, outputCallback);
        this.outputLastOnly = (outputLimitSpec != null) && (outputLimitSpec.getDisplayLimit() == OutputLimitLimitType.LAST);
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
        }
        if(oldData != null)
        {
        	oldDataLength = oldData.length;
        }

        newEventsList.add(new UniformPair<EventBean[]>(newData, oldData));
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
            newEventsSize = newEvents.size();
        }

        int oldEventsSize = 0;
        if (oldEvents != null)
        {
            oldEventsSize = oldEvents.size();
        }

        Set<MultiKey<EventBean>> newEventsCopySet = new HashSet<MultiKey<EventBean>>(newEvents);
        Set<MultiKey<EventBean>> oldEventsCopySet = new HashSet<MultiKey<EventBean>>(oldEvents);

        newEventsSet.add(new UniformPair<Set<MultiKey<EventBean>>>(newEventsCopySet, oldEventsCopySet));
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
		EventBean[] newEvents = null;
		EventBean[] oldEvents = null;

		if(resultSetProcessor != null)
		{
            LinkedList<EventBean[]> oldEventPost = null;
            LinkedList<EventBean[]> newEventPost = null;

            for (UniformPair<EventBean[]> batch : newEventsList)
            {
                Pair<EventBean[], EventBean[]> pair = resultSetProcessor.processViewResult(batch.getFirst(), batch.getSecond(), false);

                if (pair == null)
                {
                    continue;
                }
                if (pair.getFirst() != null)
                {
                    if (newEventPost == null)
                    {
                        newEventPost = new LinkedList<EventBean[]>();
                    }
                    newEventPost.add(pair.getFirst());
                }
                if (pair.getSecond() != null)
                {
                    if (oldEventPost == null)
                    {
                        oldEventPost = new LinkedList<EventBean[]>();
                    }
                    oldEventPost.add(pair.getSecond());
                }
            }
            // Process the events and get the result
            if (newEventPost != null)
            {
                newEvents = EventBeanUtility.flatten(newEventPost);
            }
            if (oldEventPost != null)
            {
                oldEvents = EventBeanUtility.flatten(oldEventPost);
            }
        }
		else if(outputLastOnly)
		{
            for (int i = newEventsList.size() - 1; i >= 0; i--)
            {
                UniformPair<EventBean[]> pair = newEventsList.get(i);
                if ((newEvents == null) && (pair.getFirst() != null) && (pair.getFirst().length != 0))
                {
                    newEvents = new EventBean[] {pair.getFirst()[pair.getFirst().length - 1]};
                }
                if ((oldEvents == null) && (pair.getSecond() != null) && (pair.getSecond().length != 0))
                {
                    oldEvents = new EventBean[] {pair.getSecond()[pair.getSecond().length - 1]};
                }
            }
		}
        else if(outputSnapshot)
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
        }
        else
        {
            // flatten
            if (newEventsList.size() == 1)
            {
                UniformPair<EventBean[]> pair = newEventsList.get(0);
                newEvents = pair.getFirst();
                oldEvents = pair.getSecond();
            }
            else if (newEventsList.size() > 1)
            {
                LinkedList<EventBean[]> oldEventPost = null;
                LinkedList<EventBean[]> newEventPost = null;
                for (UniformPair<EventBean[]> batch : newEventsList)
                {
                    if (batch.getFirst() != null)
                    {
                        if (newEventPost == null)
                        {
                            newEventPost = new LinkedList<EventBean[]>();
                        }
                        newEventPost.add(batch.getFirst());
                    }
                    if (batch.getSecond() != null)
                    {
                        if (oldEventPost == null)
                        {
                            oldEventPost = new LinkedList<EventBean[]>();
                        }
                        oldEventPost.add(batch.getSecond());
                    }
                }
                // Process the events and get the result
                if (newEventPost != null)
                {
                    newEvents = EventBeanUtility.flatten(newEventPost);
                }
                if (oldEventPost != null)
                {
                    oldEvents = EventBeanUtility.flatten(oldEventPost);
                }
            }
        }

        if(doOutput)
		{
			output(forceUpdate, newEvents, oldEvents);
		}
		resetEventBatches();
	}

	private void output(boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents)
	{
        // Child view can be null in replay from named window
        if (childView != null)
        {
            outputStrategy.output(forceUpdate, newEvents, oldEvents, childView);
        }
    }

	private void resetEventBatches()
	{
		newEventsList.clear();
		newEventsSet.clear();
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

		EventBean[] newEvents = null;
		EventBean[] oldEvents = null;

        LinkedList<EventBean[]> oldEventPost = null;
        LinkedList<EventBean[]> newEventPost = null;

        for (UniformPair<Set<MultiKey<EventBean>>> batch : newEventsSet)
        {
            Pair<EventBean[], EventBean[]> pair = resultSetProcessor.processJoinResult(batch.getFirst(), batch.getSecond(), false);

            if (pair == null)
            {
                continue;
            }
            if (pair.getFirst() != null)
            {
                if (newEventPost == null)
                {
                    newEventPost = new LinkedList<EventBean[]>();
                }
                newEventPost.add(pair.getFirst());
            }
            if (pair.getSecond() != null)
            {
                if (oldEventPost == null)
                {
                    oldEventPost = new LinkedList<EventBean[]>();
                }
                oldEventPost.add(pair.getSecond());
            }
        }
        // Process the events and get the result
        if (newEventPost != null)
        {
            newEvents = EventBeanUtility.flatten(newEventPost);
        }
        if (oldEventPost != null)
        {
            oldEvents = EventBeanUtility.flatten(oldEventPost);
        }
        
        if(doOutput)
		{
			output(forceUpdate, newEvents, oldEvents);
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
