package net.esper.eql.view;

import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.eql.core.ResultSetProcessor;
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
     * @param isJoin is true for join statements
     */
    public OutputProcessViewDirect(ResultSetProcessor resultSetProcessor, boolean isJoin)
    {
        super(resultSetProcessor, isJoin);

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

        Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.processViewResult(newData, oldData);

        EventBean[] newEventArr = newOldEvents != null ? newOldEvents.getFirst() : null;
        EventBean[] oldEventArr = newOldEvents != null ? newOldEvents.getSecond() : null;

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

        Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.processJoinResult(newEvents, oldEvents);

        if (newOldEvents == null)
        {
            return;
        }
        EventBean[] newEventArr = newOldEvents.getFirst();
        EventBean[] oldEventArr = newOldEvents.getSecond();

        if (newEventArr != null || oldEventArr != null)
        {
            updateChildren(newEventArr, oldEventArr);
        }
    }
}
