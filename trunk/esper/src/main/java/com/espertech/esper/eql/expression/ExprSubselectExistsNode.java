package com.espertech.esper.eql.expression;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.eql.spec.StatementSpecRaw;
import com.espertech.esper.eql.core.StreamTypeService;
import com.espertech.esper.eql.core.MethodResolutionService;
import com.espertech.esper.eql.core.ViewResourceDelegate;
import com.espertech.esper.eql.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.schedule.TimeProvider;

import java.util.Set;

/**
 * Represents an exists-subselect in an expression tree.
 */
public class ExprSubselectExistsNode extends ExprSubselectNode
{
    private static final Log log = LogFactory.getLog(ExprSubselectExistsNode.class);

    /**
     * Ctor.
     * @param statementSpec is the lookup statement spec from the parser, unvalidated
     */
    public ExprSubselectExistsNode(StatementSpecRaw statementSpec)
    {
        super(statementSpec);
    }

    public Class getType() throws ExprValidationException
    {
        return Boolean.class;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Set<EventBean> matchingEvents)
    {
        if (matchingEvents == null)
        {
            return false;
        }
        if (matchingEvents.size() == 0)
        {
            return false;
        }

        if (filterExpr == null)
        {
            return true;
        }

        // Evaluate filter
        EventBean[] events = new EventBean[eventsPerStream.length + 1];
        System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

        for (EventBean subselectEvent : matchingEvents)
        {
            // Prepare filter expression event list
            events[0] = subselectEvent;

            Boolean pass = (Boolean) filterExpr.evaluate(events, true);
            if ((pass != null) && (pass))
            {
                return true;
            }
        }

        return false;
    }
}
