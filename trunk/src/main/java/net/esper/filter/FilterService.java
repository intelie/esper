package net.esper.filter;

import net.esper.event.EventBean;

/**
 * Interface for filtering events by event type and event property values. Allows adding and removing filters.
 * <p>
 * Filters are defined by a {@link FilterSpec} and are associated with a {@link FilterCallback}
 * callback.
 * Implementations may decide if the same filter callback can be registered twice for different or some
 * filter specifications.
 * <p>
 * The performance of an implementation of this service is crucial in achieving a high overall event throughput.
 */
public interface FilterService
{
    /**
     * Finds matching filters to the event passed in and invokes their associated callback method.
     * @param event is the event to be matched against filters
     */
    public void evaluate(EventBean event);

    /**
     * Add a filter for events as defined by the filter specification, and register a
     * callback to be invoked upon evaluation of an event that matches the filter spec.
     * @param filterValueSet is a specification of filter parameters, contains
     * event type information, event property values and operators
     * @param callback is the callback to be invoked when the filter matches an event
     */
    public void add(FilterValueSet filterValueSet, FilterCallback callback);

    /**
     * Remove a filter callback.
     * @param callback is the callback to be removed
     */
    public void remove(FilterCallback callback);

    /**
     * Return a count of the number of events evaluated by this service.
     * @return count of invocations of evaluate method
     */
    public int getNumEventsEvaluated();

}