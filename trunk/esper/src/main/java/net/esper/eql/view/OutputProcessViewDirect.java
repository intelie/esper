package net.esper.eql.view;

import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.event.EventBean;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.core.StatementResultService;
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
     * @param outputStrategy is the execution of output to sub-views or natively
     * @param isInsertInto is true if the statement is a insert-into
     * @param statementResultService service for managing listener/subscribers and result generation needs
     */
    public OutputProcessViewDirect(ResultSetProcessor resultSetProcessor, OutputStrategy outputStrategy, boolean isInsertInto, StatementResultService statementResultService)
    {
        super(resultSetProcessor, outputStrategy, isInsertInto, statementResultService);

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
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        boolean isGenerateSynthetic = statementResultService.isMakeSynthetic();
        boolean isGenerateNatural = statementResultService.isMakeNatural();

        Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.processViewResult(newData, oldData, isGenerateSynthetic);

        if ((!isGenerateSynthetic) && (!isGenerateNatural))
        {
            return;
        }

        boolean forceOutput = false;
        if ((newData == null) && (oldData == null) &&
                ((newOldEvents == null) || (newOldEvents.getFirst() == null && newOldEvents.getSecond() == null)))
        {
            forceOutput = true;
        }

        // Child view can be null in replay from named window
        if (childView != null)
        {
            outputStrategy.output(forceOutput, newOldEvents, childView);
        }
    }

    /**
     * This process (update) method is for participation in a join.
     * @param newEvents - new events
     * @param oldEvents - old events
     */
    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".process Received update, " +
                    "  newData.length==" + ((newEvents == null) ? 0 : newEvents.size()) +
                    "  oldData.length==" + ((oldEvents == null) ? 0 : oldEvents.size()));
        }

        boolean isGenerateSynthetic = statementResultService.isMakeSynthetic();
        boolean isGenerateNatural = statementResultService.isMakeNatural();

        Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.processJoinResult(newEvents, oldEvents, isGenerateSynthetic);

        if ((!isGenerateSynthetic) && (!isGenerateNatural))
        {
            return;
        }

        if (newOldEvents == null)
        {
            return;
        }

        // Child view can be null in replay from named window
        if (childView != null)
        {
            outputStrategy.output(false, newOldEvents, childView);
        }
    }
}
