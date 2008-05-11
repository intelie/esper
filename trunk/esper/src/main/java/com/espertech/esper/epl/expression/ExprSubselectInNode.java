package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.spec.StatementSpecRaw;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.schedule.TimeProvider;
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
    private boolean isNotIn;
    private boolean mustCoerce;
    private Class coercionType;

    /**
     * Ctor.
     * @param statementSpec is the lookup statement spec from the parser, unvalidated
     */
    public ExprSubselectInNode(StatementSpecRaw statementSpec)
    {
        super(statementSpec);
    }

    public Class getType() throws ExprValidationException
    {
        return Boolean.class;
    }

    /**
     * Indicate that this is a not-in lookup.
     * @param notIn is true for not-in, or false for regular 'in'
     */
    public void setNotIn(boolean notIn)
    {
        isNotIn = notIn;
    }

    /**
     * Returns true for not-in, or false for in.
     * @return true for not-in
     */
    public boolean isNotIn()
    {
        return isNotIn;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("The Subselect-IN requires 1 child expression");
        }

        // Must be the same boxed type returned by expressions under this
        Class typeOne = JavaClassHelper.getBoxedType(this.getChildNodes().get(0).getType());
        Class typeTwo;
        if (selectClause != null)
        {
            typeTwo = selectClause.getType();
        }
        else
        {
            typeTwo = this.rawEventType.getUnderlyingType();
        }         

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
        if (matchingEvents == null)
        {
            return isNotIn;
        }
        if (matchingEvents.size() == 0)
        {
            return isNotIn;
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
            return isNotIn;
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
                    return !isNotIn;
                }
                continue;
            }
            if (rightResult == null)
            {
                continue;
            }

            if (!mustCoerce)
            {
                if (leftResult.equals(rightResult))
                {
                    return !isNotIn;
                }
            }
            else
            {
                Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                if (left.equals(right))
                {
                    return !isNotIn;
                }
            }
        }

        return isNotIn;
    }
}
