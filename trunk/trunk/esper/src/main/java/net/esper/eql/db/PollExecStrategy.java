package net.esper.eql.db;

import net.esper.event.EventBean;

import java.util.List;

/**
 * Interface for polling data from a data source such as a relational database.
 * <p>
 * Lifecycle methods are for managing connection resources.
 */
public interface PollExecStrategy
{
    /**
     * Start the poll, called before any poll operation.
     */
    public void start();

    /**
     * Poll events using the keys provided.
     * @param lookupValues is keys for exeuting a query or such
     * @return a list of events for the keys
     */
    public List<EventBean> poll(Object[] lookupValues);

    /**
     * Indicate we are done polling and can release resources.
     */
    public void done();

    /**
     * Indicate we are no going to use this object again.
     */
    public void destroy();
}
