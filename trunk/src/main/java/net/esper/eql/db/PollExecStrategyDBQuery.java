package net.esper.eql.db;

import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.collection.Pair;
import net.esper.client.EPException;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Viewable providing historical data from a database.
 */
public class PollExecStrategyDBQuery implements PollExecStrategy
{
    private final EventAdapterService eventAdapterService;
    private final String preparedStatementText;
    private final Map<String, DBOutputTypeDesc> outputTypes;
    private final ConnectionCache connectionCache;
    private final EventType eventType;

    private Pair<Connection, PreparedStatement> resources;

    /**
     * Ctor.
     * @param eventAdapterService for generating event beans
     * @param preparedStatementText is the SQL to use for polling
     * @param outputTypes describe columns selected by the SQL
     * @param eventType is the event type that this poll generates
     * @param connectionCache caches Connection and PreparedStatement
     */
    public PollExecStrategyDBQuery(EventAdapterService eventAdapterService,
                                   EventType eventType,
                                   ConnectionCache connectionCache,
                                   String preparedStatementText,
                                   Map<String, DBOutputTypeDesc> outputTypes)
    {
        this.eventAdapterService = eventAdapterService;
        this.eventType = eventType;
        this.connectionCache = connectionCache;
        this.preparedStatementText = preparedStatementText;
        this.outputTypes = outputTypes;
    }

    public void start()
    {
        resources = connectionCache.getConnection();
    }

    public void done()
    {
        connectionCache.doneWith(resources);
    }

    public void destroy()
    {
        connectionCache.destroy();
    }

    public List<EventBean> poll(Object[] lookupValues)
    {
        List<EventBean> result = null;
        try
        {
            result = execute(resources.getSecond(), lookupValues);
        }
        catch (EPException ex)
        {
            connectionCache.doneWith(resources);
            throw ex;
        }

        return result;
    }

    private List<EventBean> execute(PreparedStatement preparedStatement,
                                    Object[] lookupValuePerStream)
    {
        // set parameters
        int count = 1;
        for (int i = 0; i < lookupValuePerStream.length; i++)
        {
            try
            {
                preparedStatement.setObject(count, lookupValuePerStream[i]);
            }
            catch (SQLException ex)
            {
                throw new EPException("Error setting parameter " + count, ex);
            }

            count++;
        }

        // execute
        ResultSet resultSet = null;
        try
        {
             resultSet = preparedStatement.executeQuery();
        }
        catch (SQLException ex)
        {
            throw new EPException("Error executing statement '" + preparedStatementText + "'", ex);
        }

        // generate events for result set
        List<EventBean> rows = new LinkedList<EventBean>();
        try
        {
            while (resultSet.next())
            {
                Map<String, Object> row = new HashMap<String, Object>();
                for (Map.Entry<String, DBOutputTypeDesc> entry : outputTypes.entrySet())
                {
                    String columnName = entry.getKey();
                    Object value = resultSet.getObject(columnName);
                    row.put(columnName, value);
                }
                EventBean eventBeanRow = eventAdapterService.createMapFromValues(row, eventType);
                rows.add(eventBeanRow);
            }
        }
        catch (SQLException ex)
        {
            throw new EPException("Error reading results for statement '" + preparedStatementText + "'", ex);
        }

        try
        {
            resultSet.close();
        }
        catch (SQLException ex)
        {
            throw new EPException("Error closing statement '" + preparedStatementText + "'", ex);
        }

        return rows;
    }
}
