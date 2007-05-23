package net.esper.eql.view;

import net.esper.collection.MultiKey;
import net.esper.collection.MultiKeyUntyped;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.core.OrderBySorter;
import net.esper.eql.core.ResultSetProcessorResult;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * Output process view that does not enforce any output policies and may simply
 * hand over events to child views.
 */
public class OutputProcessViewDirect extends OutputProcessView
{
	private static final Log log = LogFactory.getLog(OutputProcessViewDirect.class);

    /**
     * Ctor.
     * @param resultSetProcessor is processing the result set for publishing it out
     */
    public OutputProcessViewDirect(ResultSetProcessor resultSetProcessor,
                                   OrderBySorter orderBySorter)
    {
        super(resultSetProcessor, orderBySorter);

        log.debug(".ctor");
        if (resultSetProcessor == null)
        {
            throw new IllegalArgumentException("Null result set processor, no output processor required");
        }
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

        ResultSetProcessorResult result = resultSetProcessor.processViewResult(newData, oldData);

        if (result == null)
        {
            return;
        }

        EventBean[] newEventArr = result.getNewOut();
        EventBean[] oldEventArr = result.getOldOut();

        if (orderBySorter != null)
        {
            MultiKeyUntyped[] newOrderKeys = result.getNewOrderKey();
            MultiKeyUntyped[] oldOrderKeys = result.getOldOrderKey();
            newEventArr = orderBySorter.sort(newEventArr, newOrderKeys);
            oldEventArr = orderBySorter.sort(oldEventArr, oldOrderKeys);
        }

        if(newEventArr != null || oldEventArr != null)
        {
            updateChildren(newEventArr, oldEventArr);
        }        
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

        log.debug(".continueOutputProcessingJoin");

        ResultSetProcessorResult result = resultSetProcessor.processJoinResult(newEvents, oldEvents);

        if (result == null)
        {
            return;
        }

        EventBean[] newEventArr = result.getNewOut();
        EventBean[] oldEventArr = result.getOldOut();

        if (orderBySorter != null)
        {
            MultiKeyUntyped[] newOrderKeys = result.getNewOrderKey();
            MultiKeyUntyped[] oldOrderKeys = result.getOldOrderKey();
            newEventArr = orderBySorter.sort(newEventArr, newOrderKeys);
            oldEventArr = orderBySorter.sort(oldEventArr, oldOrderKeys);
        }

        if (newEventArr != null || oldEventArr != null)
        {
            updateChildren(newEventArr, oldEventArr);
        }
    }
}
