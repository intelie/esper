package net.esper.eql.db;

import net.esper.view.View;
import net.esper.view.HistoricalEventViewable;
import net.esper.event.EventType;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventBean;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.PropertyResolutionDescriptor;
import net.esper.eql.core.StreamTypesException;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.PollResultIndexingStrategy;
import net.esper.client.EPException;
import java.util.*;

/**
 * Implements a poller viewable that uses a polling strategy, a cache and
 * some input parameters extracted from event streams to perform the polling.
 */
public class PollingViewable implements HistoricalEventViewable
{
    private final int myStreamNumber;
    private final PollExecStrategy pollExecStrategy;
    private final List<String> inputParameters;
    private final DataCache dataCache;
    private final EventType eventType;

    private EventPropertyGetter[] getters;
    private int[] getterStreamNumbers;

    /**
     * Ctor.
     * @param myStreamNumber is the stream number of the view
     * @param inputParameters are the event property names providing input parameter keys
     * @param pollExecStrategy is the strategy to use for retrieving results
     * @param dataCache is looked up before using the strategy
     * @param eventType is the type of events generated by the view
     */
    public PollingViewable(int myStreamNumber,
                           List<String> inputParameters,
                           PollExecStrategy pollExecStrategy,
                           DataCache dataCache,
                           EventType eventType)
    {
        this.myStreamNumber = myStreamNumber;
        this.inputParameters = inputParameters;
        this.pollExecStrategy = pollExecStrategy;
        this.dataCache = dataCache;
        this.eventType = eventType;
    }

    public void stop()
    {
        pollExecStrategy.destroy();
    }

    public void validate(StreamTypeService streamTypeService) throws ExprValidationException
    {
        getters = new EventPropertyGetter[inputParameters.size()];
        getterStreamNumbers = new int[inputParameters.size()];

        int count = 0;
        for (String inputParam : inputParameters)
        {
            PropertyResolutionDescriptor desc = null;

            // try to resolve the property name alone
            try
            {
                desc = streamTypeService.resolveByStreamAndPropName(inputParam);
            }
            catch (StreamTypesException ex)
            {
                throw new ExprValidationException("Property '" + inputParam + "' failed to resolve, reason: " + ex.getMessage());
            }

            // hold on to getter and stream number for each stream
            int streamId = desc.getStreamNum();
            if (streamId == myStreamNumber)
            {
                throw new ExprValidationException("Invalid property '" + inputParam + "' resolves to the historical data itself");
            }
            String propName = desc.getPropertyName();
            getters[count] = streamTypeService.getEventTypes()[streamId].getGetter(propName);
            getterStreamNumbers[count] = streamId;

            count++;
        }
    }

    public EventTable[] poll(EventBean[][] lookupEventsPerStream, PollResultIndexingStrategy indexingStrategy)
    {
        pollExecStrategy.start();

        EventTable[] resultPerInputRow = new EventTable[lookupEventsPerStream.length];

        // Get input parameters for each row
        for (int row = 0; row < lookupEventsPerStream.length; row++)
        {
            Object[] lookupValues = new Object[inputParameters.size()];

            // Build lookup keys
            for (int valueNum = 0; valueNum < inputParameters.size(); valueNum++)
            {
                int streamNum = getterStreamNumbers[valueNum];
                EventBean streamEvent = lookupEventsPerStream[row][streamNum];
                Object lookupValue = getters[valueNum].get(streamEvent);
                lookupValues[valueNum] = lookupValue;
            }

            // Get the result from cache
            EventTable result = dataCache.getCached(lookupValues);
            if (result != null)     // found in cache
            {
                resultPerInputRow[row] = result;
            }
            else        // not found in cache, get from actual polling (db query)
            {
                try
                {
                    // Poll using the polling execution strategy and lookup values
                    List<EventBean> pollResult = pollExecStrategy.poll(lookupValues);

                    // index the result, if required, using an indexing strategy
                    EventTable indexTable = indexingStrategy.index(pollResult, dataCache.isActive());

                    // assign to row
                    resultPerInputRow[row] = indexTable;

                    // save in cache
                    dataCache.put(lookupValues, indexTable);
                }
                catch (EPException ex)
                {
                    pollExecStrategy.done();
                    throw ex;
                }
            }
        }

        pollExecStrategy.done();

        return resultPerInputRow;
    }

    public View addView(View view)
    {
        return view;
    }

    public List<View> getViews()
    {
        return new LinkedList<View>();
    }

    public boolean removeView(View view)
    {
        throw new UnsupportedOperationException("Subviews not supported");
    }

    public boolean hasViews()
    {
        return false;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        throw new UnsupportedOperationException("Iterator not supported");
    }
}
