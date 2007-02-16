package net.esper.filter;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.collection.MultiKeyUntyped;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Index for filter parameter constants to match using the 'in' operator to match against a supplied set of values
 * (i.e. multiple possible exact matches).
 * The implementation is based on a regular HashMap.
 */
public final class FilterParamIndexIn extends FilterParamIndex
{
    private final Map<Object, List<EventEvaluator>> constantsMap;
    private final Map<MultiKeyUntyped, EventEvaluator> evaluatorsMap;
    private final ReadWriteLock constantsMapRWLock;

    /**
     * Constructs the index for multiple-exact matches.
     * @param propertyName is the name of the event property
     * @param eventType describes the event type and is used to obtain a getter instance for the property
     */
    public FilterParamIndexIn(String propertyName, EventType eventType)
    {
        super(propertyName, FilterOperator.IN_LIST_OF_VALUES, eventType);

        constantsMap = new HashMap<Object, List<EventEvaluator>>();
        evaluatorsMap = new HashMap<MultiKeyUntyped, EventEvaluator>();
        constantsMapRWLock = new ReentrantReadWriteLock();
    }

    public final EventEvaluator get(Object filterConstant)
    {
        MultiKeyUntyped keyValues = (MultiKeyUntyped) filterConstant;
        return evaluatorsMap.get(keyValues);
    }

    public final void put(Object filterConstant, EventEvaluator evaluator)
    {
        // Store evaluator keyed to set of values
        MultiKeyUntyped keys = (MultiKeyUntyped) filterConstant;
        evaluatorsMap.put(keys, evaluator);

        // Store each value to match against in Map with it's evaluator as a list
        Object[] keyValues = keys.getKeys();
        for (int i = 0; i < keyValues.length; i++)
        {
            List<EventEvaluator> evaluators = constantsMap.get(keyValues[i]);
            if (evaluators == null)
            {
                evaluators = new LinkedList<EventEvaluator>();
                constantsMap.put(keyValues[i], evaluators);
            }
            evaluators.add(evaluator);
        }
    }

    public final boolean remove(Object filterConstant)
    {
        MultiKeyUntyped keys = (MultiKeyUntyped) filterConstant;

        // remove the mapping of value set to evaluator
        EventEvaluator eval = evaluatorsMap.remove(keys);
        boolean isRemoved = false;
        if (eval != null)
        {
            isRemoved = true;
        }

        Object[] keyValues = keys.getKeys();
        for (int i = 0; i < keyValues.length; i++)
        {
            List<EventEvaluator> evaluators = constantsMap.get(keyValues[i]);
            evaluators.remove(eval);
        }
        return isRemoved;
    }

    public final int size()
    {
        return constantsMap.size();
    }

    public final ReadWriteLock getReadWriteLock()
    {
        return constantsMapRWLock;
    }

    public final void matchEvent(EventBean eventBean, Collection<FilterHandle> matches)
    {
        Object attributeValue = this.getGetter().get(eventBean);

        if (log.isDebugEnabled())
        {
            log.debug(".match (" + Thread.currentThread().getId() + ") attributeValue=" + attributeValue);
        }

        if (attributeValue == null)
        {
            return;
        }

        // Look up in hashtable
        constantsMapRWLock.readLock().lock();
        List<EventEvaluator> evaluators = constantsMap.get(attributeValue);

        // No listener found for the value, return
        if (evaluators == null)
        {
            constantsMapRWLock.readLock().unlock();
            return;
        }

        for (EventEvaluator evaluator : evaluators)
        {
            evaluator.matchEvent(eventBean, matches);
        }

        constantsMapRWLock.readLock().unlock();
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexIn.class);
}
