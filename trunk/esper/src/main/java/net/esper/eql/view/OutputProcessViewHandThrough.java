package net.esper.eql.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.event.EventBean;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.collection.Pair;
import net.esper.collection.MultiKey;

import java.util.Set;

/**
 * Output process view that does not enforce any output policies and may simply
 * hand over events to child views.
 */
public class OutputProcessViewHandThrough extends OutputProcessView
{
	private static final Log log = LogFactory.getLog(OutputProcessViewHandThrough.class);

    /**
     * Ctor.
     * @param outputStrategy is the execution of output to sub-views or natively
     */
    public OutputProcessViewHandThrough(OutputStrategy outputStrategy)
    {
        super(null, outputStrategy);
    }

    /**
     * The update method is called if the view does not participate in a join.
     * @param newData - new events
     * @param oldData - old events
     */
    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        outputStrategy.output(false, newData, oldData, this);
    }

    /**
     * This process (update) method is for participation in a join.
     * @param newEvents - new events
     * @param oldEvents - old events
     */
    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        throw new UnsupportedOperationException("Hand-through output processing not supported for joins");
    }
}
