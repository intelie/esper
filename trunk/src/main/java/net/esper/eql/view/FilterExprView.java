package net.esper.eql.view;

import net.esper.eql.expression.ExprEvaluator;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
import java.util.Iterator;

/**
 * Simple filter view filtering events using a filter expression tree.
 */
public class FilterExprView extends ViewSupport
{
    private ExprEvaluator exprEvaluator;

    /**
     * Ctor.
     * @param exprEvaluator - Filter expression evaluation impl
     */
    public FilterExprView(ExprEvaluator exprEvaluator)
    {
        this.exprEvaluator = exprEvaluator;
    }

    public EventType getEventType()
    {
        return parent.getEventType();
    }

    public Iterator<EventBean> iterator()
    {
        return parent.iterator();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        EventBean[] filteredNewData = filterEvents(exprEvaluator, newData, true);
        EventBean[] filteredOldData = filterEvents(exprEvaluator, oldData, false);

        if ((filteredNewData != null) || (filteredOldData != null))
        {
            this.updateChildren(filteredNewData, filteredOldData);
        }
    }

    /**
     * Filters events using the supplied evaluator.
     * @param exprEvaluator - evaluator to use
     * @param events - events to filter
     * @return filtered events, or null if no events got through the filter 
     */
    protected static EventBean[] filterEvents(ExprEvaluator exprEvaluator, EventBean[] events, boolean isNewData)
    {
        if (events == null)
        {
            return null;
        }

        EventBean[] evalEventArr = new EventBean[1];
        boolean passResult[] = new boolean[events.length];
        int passCount = 0;

        for (int i = 0; i < events.length; i++)
        {
            evalEventArr[0] = events[i];
            boolean pass = (Boolean) exprEvaluator.evaluate(evalEventArr, isNewData);
            if (pass)
            {
                passResult[i] = true;
                passCount++;
            }
        }

        if (passCount == 0)
        {
            return null;
        }
        if (passCount == events.length)
        {
            return events;
        }

        EventBean[] resultArray = new EventBean[passCount];
        int count = 0;
        for (int i = 0; i < passResult.length; i++)
        {
            if (passResult[i])
            {
                resultArray[count] = events[i];
                count++;
            }
        }
        return resultArray;
    }
}
