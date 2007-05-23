package net.esper.eql.core;

import net.esper.collection.MultiKey;
import net.esper.collection.MultiKeyUntyped;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.spec.OutputLimitType;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Result set processor for the simplest case: no aggregation functions used in the select clause, and no group-by.
 * <p>
 * The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
 */
public class ResultSetProcessorSimple implements ResultSetProcessor
{
    private static final Log log = LogFactory.getLog(ResultSetProcessorSimple.class);

    private final SelectExprProcessor optionalSelectExprProcessor;
    private final OrderByProcessor orderByProcessor;
    private final OutputLimitType outputLimitType;

    private LinkedList<ResultSetProcessorResult> outputLimitBatch = new LinkedList<ResultSetProcessorResult>();

    // TODO: try a simple processor with having, with and without wildcard
    
    /**
     * Ctor.
     * @param optionalSelectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting the outgoing events according to the order-by clause
     */
    public ResultSetProcessorSimple(SelectExprProcessor optionalSelectExprProcessor,
                                    OrderByProcessor orderByProcessor,
                                    OutputLimitType outputLimitType)
    {
        this.optionalSelectExprProcessor = optionalSelectExprProcessor;
        this.orderByProcessor = orderByProcessor;
        this.outputLimitType = outputLimitType;
    }

    public EventType getResultEventType()
    {
        // the type depends on whether we are post-processing a selection result, or not
        if (optionalSelectExprProcessor != null)
        {
            return optionalSelectExprProcessor.getResultEventType();
        }
        return null;
    }

    public ResultSetProcessorResult processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        ResultSetSelect resultOld = getSelectEventsNoHaving(optionalSelectExprProcessor, orderByProcessor, oldEvents, false);
        ResultSetSelect resultNew = getSelectEventsNoHaving(optionalSelectExprProcessor, orderByProcessor, newEvents, true);

        ResultSetProcessorResult result = new ResultSetProcessorResult();
        result.setNewOut(resultNew.getEvents());
        result.setOldOut(resultOld.getEvents());
        result.setNewOrderKey(resultNew.getOrderKeys());
        result.setOldOrderKey(resultOld.getOrderKeys());

        return result;
    }

    public ResultSetProcessorResult processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        ResultSetSelect resultOld = getSelectEventsNoHaving(optionalSelectExprProcessor, orderByProcessor, oldData, false);
        ResultSetSelect resultNew = getSelectEventsNoHaving(optionalSelectExprProcessor, orderByProcessor, newData, true);

        ResultSetProcessorResult result = new ResultSetProcessorResult();
        result.setNewOut(resultNew.getEvents());
        result.setOldOut(resultOld.getEvents());
        result.setNewOrderKey(resultNew.getOrderKeys());
        result.setOldOrderKey(resultOld.getOrderKeys());

        return result;
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * @param optExprProcessor - processes each input event and returns output event
     * @param orderByProcessor - orders the outgoing events according to the order-by clause
     * @param events - input events
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static ResultSetSelect getSelectEventsNoHaving(SelectExprProcessor optExprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, boolean isNewData)
    {
        if (events == null)
        {
            return new ResultSetSelect();
        }

        EventBean[] result = new EventBean[events.length];
        MultiKeyUntyped[] orderKeys = null;
        if(orderByProcessor != null)
        {
            orderKeys = new MultiKeyUntyped[events.length];
        }

        EventBean[] eventsPerStream = new EventBean[1];
        for (int i = 0; i < events.length; i++)
        {
            eventsPerStream[0] = events[i];

            // Wildcard select case
            if(optExprProcessor == null)
            {
                result[i] = events[i];
            }
            else
            {
                result[i] = optExprProcessor.process(eventsPerStream, isNewData);
            }

            if (orderByProcessor != null)
            {
                orderKeys[i] = orderByProcessor.getSortKey(eventsPerStream, isNewData);
            }
        }

        ResultSetSelect resultSetSelect = new ResultSetSelect();
        resultSetSelect.setEvents(result);
        resultSetSelect.setOrderKeys(orderKeys);
        return resultSetSelect;
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * @param optExprProcessor - processes each input event and returns output event
     * @param orderByProcessor - for sorting output events according to the order-by clause
     * @param events - input events
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static ResultSetSelect getSelectEventsNoHaving(SelectExprProcessor optExprProcessor, OrderByProcessor orderByProcessor, Set<MultiKey<EventBean>> events, boolean isNewData)
    {
        int length = events.size();
        if (length == 0)
        {
            return new ResultSetSelect();
        }

        EventBean[] result = new EventBean[length];
        MultiKeyUntyped[] orderKeys = null;
        if(orderByProcessor != null)
        {
            orderKeys = new MultiKeyUntyped[length];
        }

        int count = 0;
        for (MultiKey<EventBean> key : events)
        {
            EventBean[] eventsPerStream = key.getArray();
            if (optExprProcessor != null)
            {
                result[count] = optExprProcessor.process(eventsPerStream, isNewData);
            }
            else
            {
                result[count] = eventsPerStream[0];
            }
            if(orderByProcessor != null)
            {
                orderKeys[count] = orderByProcessor.getSortKey(eventsPerStream, isNewData);
            }
            count++;
        }

        ResultSetSelect resultSetSelect = new ResultSetSelect();
        resultSetSelect.setEvents(result);
        resultSetSelect.setOrderKeys(orderKeys);
        return resultSetSelect;
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * <p>
     * Also applies a having clause.
     * @param optExprProcessor - processes each input event and returns output event
     * @param orderByProcessor - for sorting output events according to the order-by clause
     * @param events - input events
     * @param optionalHavingNode - supplies the having-clause expression
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static ResultSetSelect getSelectEventsHaving(SelectExprProcessor optExprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, ExprNode optionalHavingNode, boolean isNewData)
    {
        if (events == null)
        {
            return new ResultSetSelect();
        }

        List<EventBean> result = new ArrayList<EventBean>();
        List<MultiKeyUntyped> orderKeys = null;
        if(orderByProcessor != null)
        {
            orderKeys = new ArrayList<MultiKeyUntyped>();
        }

        EventBean[] eventsPerStream = new EventBean[1];
        for (int i = 0; i < events.length; i++)
        {
            eventsPerStream[0] = events[i];

            Boolean passesHaving = (Boolean) optionalHavingNode.evaluate(eventsPerStream, isNewData);
            if (!passesHaving)
            {
                continue;
            }

            if (optExprProcessor != null)
            {
                result.add(optExprProcessor.process(eventsPerStream, isNewData));
            }
            else
            {
                result.add(eventsPerStream[0]);
            }
            if(orderByProcessor != null)
            {
                orderKeys.add(orderByProcessor.getSortKey(eventsPerStream, isNewData));
            }
        }

        ResultSetSelect resultSetSelect = new ResultSetSelect();
        if (!result.isEmpty())
        {
            resultSetSelect.setEvents(result.toArray(new EventBean[0]));

            if (orderByProcessor != null)
            {
                resultSetSelect.setOrderKeys(orderKeys.toArray(new MultiKeyUntyped[0]));
            }
            return resultSetSelect;
        }
        else
        {
            return resultSetSelect;
        }
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * <p>
     * Also applies a having clause.
     * @param optExprProcessor - processes each input event and returns output event
     * @param orderByProcessor - for sorting output events according to the order-by clause
     * @param events - input events
     * @param optionalHavingNode - supplies the having-clause expression
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static ResultSetSelect getSelectEventsHaving(SelectExprProcessor optExprProcessor, OrderByProcessor orderByProcessor, Set<MultiKey<EventBean>> events, ExprNode optionalHavingNode, boolean isNewData)
    {
        LinkedList<EventBean> result = new LinkedList<EventBean>();
        List<MultiKeyUntyped> orderKeys = null;
        if(orderByProcessor != null)
        {
            orderKeys = new ArrayList<MultiKeyUntyped>();
        }

        for (MultiKey<EventBean> key : events)
        {
            EventBean[] eventsPerStream = key.getArray();

            Boolean passesHaving = (Boolean) optionalHavingNode.evaluate(eventsPerStream, isNewData);
            if (!passesHaving)
            {
                continue;
            }

            if (optExprProcessor != null)
            {
                EventBean resultEvent = optExprProcessor.process(eventsPerStream, isNewData);
                result.add(resultEvent);
            }
            else
            {
                result.add(eventsPerStream[0]);
            }
            if(orderByProcessor != null)
            {
                orderKeys.add(orderByProcessor.getSortKey(eventsPerStream, isNewData));
            }
        }

        ResultSetSelect resultSetSelect = new ResultSetSelect();
        if (!result.isEmpty())
        {
            resultSetSelect.setEvents(result.toArray(new EventBean[0]));
            if (orderByProcessor != null)
            {
                resultSetSelect.setOrderKeys(orderKeys.toArray(new MultiKeyUntyped[0]));
            }
            return resultSetSelect;
        }
        else
        {
            return resultSetSelect;
        }
    }

    public boolean addViewResult(EventBean[] newData, EventBean[] oldData)
    {
        ResultSetProcessorResult result = processViewResult(newData, oldData);
        if (outputLimitType == OutputLimitType.ALL)
        {
            outputLimitBatch.add(result);
        }
        else if (outputLimitType == OutputLimitType.LAST)
        {
            outputLimitBatch.add(result);
            if (outputLimitBatch.size() > 1)
            {
                outputLimitBatch.removeFirst();
            }
        }
        else
        {
            throw new UnsupportedOperationException("Adding results for output limit first not supported");
        }
        return true;
    }

    public boolean addJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        ResultSetProcessorResult result = processJoinResult(newEvents, oldEvents);

        if (outputLimitType == OutputLimitType.ALL)
        {
            outputLimitBatch.add(result);
        }
        else if (outputLimitType == OutputLimitType.LAST)
        {
            outputLimitBatch.add(result);
            if (outputLimitBatch.size() > 1)
            {
                outputLimitBatch.removeFirst();
            }
        }
        else
        {
            throw new UnsupportedOperationException("Adding results for output limit first not supported");
        }
        return true;
    }

    public ResultSetProcessorResult generateResult()
    {
        ResultSetProcessorResult result = ResultSetProcessorSimple.flatten(outputLimitBatch, orderByProcessor);
        outputLimitBatch.clear();
        return result;
    }

    protected static ResultSetProcessorResult flatten(List<ResultSetProcessorResult> resultVector, OrderByProcessor orderByProcessor)
    {
        if (resultVector.isEmpty())
        {
            return null;
        }

        if (resultVector.size() == 1)
        {
            return resultVector.get(0);
        }

        int totalNewEvents = 0;
        int totalOldEvents = 0;
        for (ResultSetProcessorResult entry : resultVector)
        {
            EventBean[] oldEvents = entry.getOldOut();
            if (oldEvents != null)
            {
                totalOldEvents += oldEvents.length;
            }
            EventBean[] newEvents = entry.getNewOut();
            if (newEvents != null)
            {
                totalNewEvents += newEvents.length;
            }
        }

        if ((totalNewEvents == 0) && (totalOldEvents == 0))
        {
            return null;
        }

        EventBean[] newEventsOut = null;
        EventBean[] oldEventsOut = null;
        if (totalNewEvents > 0)
        {
            newEventsOut = new EventBean[totalNewEvents];
        }
        if (totalOldEvents > 0)
        {
            oldEventsOut = new EventBean[totalOldEvents];
        }

        MultiKeyUntyped[] newOrderKeys = null;
        MultiKeyUntyped[] oldOrderKeys = null;
        if (orderByProcessor != null)
        {
            if (totalNewEvents > 0)
            {
                newOrderKeys = new MultiKeyUntyped[totalNewEvents];
            }
            if (totalOldEvents > 0)
            {
                oldOrderKeys = new MultiKeyUntyped[totalOldEvents];
            }
        }

        int destPosNew = 0;
        int destPosOld = 0;
        for (ResultSetProcessorResult entry : resultVector)
        {
            EventBean[] oldEvents = entry.getOldOut();
            if (oldEvents != null)
            {
                System.arraycopy(oldEvents, 0, oldEventsOut, destPosOld, oldEvents.length);
                if (orderByProcessor != null)
                {
                    System.arraycopy(entry.getOldOrderKey(), 0, oldOrderKeys, destPosOld, oldEvents.length);
                }
                destPosOld += oldEvents.length;
            }

            EventBean[] newEvents = entry.getNewOut();
            if (newEvents != null)
            {
                System.arraycopy(newEvents, 0, newEventsOut, destPosNew, newEvents.length);
                if (orderByProcessor != null)
                {
                    System.arraycopy(entry.getNewOrderKey(), 0, newOrderKeys, destPosNew, newEvents.length);
                }
                destPosNew += newEvents.length;
            }
        }

        ResultSetProcessorResult sum = new ResultSetProcessorResult();
        sum.setNewOut(newEventsOut);
        sum.setOldOut(oldEventsOut);
        sum.setNewOrderKey(newOrderKeys);
        sum.setOldOrderKey(oldOrderKeys);
        return sum;
    }    
}
