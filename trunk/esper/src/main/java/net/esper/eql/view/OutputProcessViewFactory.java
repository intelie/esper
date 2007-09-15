package net.esper.eql.view;

import net.esper.view.ViewSupport;
import net.esper.eql.join.JoinSetIndicator;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.spec.OutputLimitSpec;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.collection.TransformEventIterator;
import net.esper.collection.TransformEventMethod;
import net.esper.core.StatementContext;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for output processing views.
 * <p>
 *
 */
public class OutputProcessViewFactory
{
    /**
     * Creates an output processor view depending on the presence of output limiting requirements.
     * @param resultSetProcessor is the processing for select-clause and grouping
     * @param streamCount is the number of streams
     * @param outputLimitSpec is the output rate limiting requirements
     * @param statementContext is the statement-level services
     * @return output processing view
     */
    public static OutputProcessView makeView(ResultSetProcessor resultSetProcessor,
    					  int streamCount,
    					  OutputLimitSpec outputLimitSpec,
    					  StatementContext statementContext)
    {
        // Do we need to enforce an output policy?
        if (outputLimitSpec != null)
        {
            return new OutputProcessViewPolicy(resultSetProcessor, streamCount, outputLimitSpec, statementContext);
        }
        return new OutputProcessViewDirect(resultSetProcessor, streamCount > 1);
    }
}
