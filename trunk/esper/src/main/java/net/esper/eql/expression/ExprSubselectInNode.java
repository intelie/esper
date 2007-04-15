package net.esper.eql.expression;

import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.spec.StatementSpecRaw;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Represents a subselect in an expression tree.
 */
public class ExprSubselectInNode extends ExprSubselectNode
{
    private static final Log log = LogFactory.getLog(ExprSubselectInNode.class);
    private boolean mustCoerce;
    private Class coercionType;

    public ExprSubselectInNode(StatementSpecRaw statementSpec)
    {
        super(statementSpec);
    }

    public boolean isAllowWildcardSelect()
    {
        return false;
    }

    public Class getType() throws ExprValidationException
    {
        return Boolean.class;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("The Subselect-IN requires 1 child expression");
        }

        // Must be the same boxed type returned by expressions under this
        Class typeOne = JavaClassHelper.getBoxedType(this.getChildNodes().get(0).getType());
        Class typeTwo = selectClause.getType();

        // Null constants can be compared for any type
        if ((typeOne == null) || (typeTwo == null))
        {
            return;
        }

        // Get the common type such as Bool, String or Double and Long
        try
        {
            coercionType = JavaClassHelper.getCompareToCoercionType(typeOne, typeTwo);
        }
        catch (IllegalArgumentException ex)
        {
            throw new ExprValidationException("Implicit conversion from datatype '" +
                    typeTwo.getSimpleName() +
                    "' to '" +
                    typeOne.getSimpleName() +
                    "' is not allowed");
        }

        // Check if we need to coerce
        if ((coercionType == JavaClassHelper.getBoxedType(typeOne)) &&
            (coercionType == JavaClassHelper.getBoxedType(typeTwo)))
        {
            mustCoerce = false;
        }
        else
        {
            if (!JavaClassHelper.isNumeric(coercionType))
            {
                throw new IllegalStateException("Coercion type " + coercionType + " not numeric");
            }
            mustCoerce = true;
        }

    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Set<EventBean> matchingEvents)
    {
        if (matchingEvents.size() == 0)
        {
            return false;
        }

        // Filter according to the filter expression
        // Evaluate the select expression for each remaining row
        // Check if any of the results match the child expression, using coercion
        Collection<EventBean> matchedFilteredEvents = matchingEvents;

        // Evaluate filter
        EventBean[] events = new EventBean[eventsPerStream.length + 1];
        System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

        if (filterExpr != null)
        {
            matchedFilteredEvents = new ArrayList<EventBean>();
            for (EventBean subselectEvent : matchingEvents)
            {
                // Prepare filter expression event list
                events[0] = subselectEvent;

                // Eval filter expression
                Boolean pass = (Boolean) filterExpr.evaluate(events, true);
                if ((pass != null) && (pass))
                {
                    matchedFilteredEvents.add(subselectEvent);
                }
            }
        }
        if (matchedFilteredEvents.size() == 0)
        {
            return false;
        }

        // Evaluate the child expression
        Object leftResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);

        // Evaluate each select until we have a match
        for (EventBean event : matchedFilteredEvents)
        {
            events[0] = event;
            Object rightResult = selectClause.evaluate(events, true);

            if (leftResult == null)
            {
                if (rightResult == null)
                {
                    return true;
                }
            }
            if (rightResult == null)
            {
                if (leftResult == null)
                {
                    return true;
                }
            }

            if (!mustCoerce)
            {
                if (leftResult.equals(rightResult))
                {
                    return true;
                }
            }
            else
            {
                Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                if (left.equals(right))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
