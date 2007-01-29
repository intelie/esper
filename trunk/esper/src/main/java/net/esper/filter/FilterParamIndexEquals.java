package net.esper.filter;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.util.*;
import java.util.concurrent.locks.*;

import net.esper.event.EventBean;
import net.esper.event.EventType;

/**
 * Index for filter parameter constants to match using the equals (=) operator.
 * The implementation is based on a regular HashMap.
 */
public final class FilterParamIndexEquals extends FilterParamIndex
{
    private final Map<Object, EventEvaluator> constantsMap;
    private final ReadWriteLock constantsMapRWLock;

    /**
     * Constructs the index for exact matches.
     * @param propertyName is the name of the event property
     * @param eventType describes the event type and is used to obtain a getter instance for the property
     */
    public FilterParamIndexEquals(String propertyName, EventType eventType)
    {
        super(propertyName, FilterOperator.EQUAL, eventType);

        constantsMap = new HashMap<Object, EventEvaluator>();
        constantsMapRWLock = new ReentrantReadWriteLock();
    }

    public final EventEvaluator get(Object filterConstant)
    {
        checkType(filterConstant);
        return constantsMap.get(filterConstant);
    }

    public final void put(Object filterConstant, EventEvaluator evaluator)
    {
        checkType(filterConstant);
        constantsMap.put(filterConstant, evaluator);
    }

    public final boolean remove(Object filterConstant)
    {
        if (constantsMap.remove(filterConstant) == null)
        {
            return false;
        }
        return true;
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
        EventEvaluator evaluator = constantsMap.get(attributeValue);
        constantsMapRWLock.readLock().unlock();

        // No listener found for the value, return
        if (evaluator == null)
        {
            return;
        }

        evaluator.matchEvent(eventBean, matches);
    }

    private void checkType(Object filterConstant)
    {
        if (this.getPropertyBoxedType() != filterConstant.getClass())
        {
            throw new IllegalArgumentException("Invalid type of filter constant of " +
                    filterConstant.getClass().getName() + " for property " + this.getPropertyName());
        }
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexEquals.class);
}
