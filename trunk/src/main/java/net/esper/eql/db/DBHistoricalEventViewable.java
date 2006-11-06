package net.esper.eql.db;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.event.EventAdapterService;
import net.esper.collection.MultiKey;
import net.esper.view.HistoricalEventViewable;
import net.esper.view.View;
import net.esper.client.EPException;

import java.util.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class DBHistoricalEventViewable implements HistoricalEventViewable
{
    private final EventAdapterService eventAdapterService;
    private final String preparedStatementText;
    private final DBStatementMetaData metaData;
    private final DatabaseConnectionFactory databaseConnectionFactory;
    private final EventType eventType;

    private final StatementExecutor statementExecutor;

    public DBHistoricalEventViewable(EventAdapterService eventAdapterService, String preparedStatementText, DBStatementMetaData metaData, DatabaseConnectionFactory databaseConnectionFactory, boolean retainConnection)
    {
        this.eventAdapterService = eventAdapterService;
        this.preparedStatementText = preparedStatementText;
        this.metaData = metaData;
        this.databaseConnectionFactory = databaseConnectionFactory;

        // Construct an event type from SQL query results
        Map<String, Class> dbEventType = metaData.getOutputEventType();
        eventType = eventAdapterService.createAnonymousMapType(dbEventType);

        if (retainConnection)
        {
            statementExecutor = new CachedStatementExecutor();
        }
        else
        {
            statementExecutor = new StatementExecutorImpl();
        }
    }

    public void stop()
    {
        statementExecutor.stop();
    }

    public void poll(EventBean[] lookupEvents, Set<MultiKey<EventBean>> joinSet)
    {
        Connection connection = null;
        try
        {
            connection = databaseConnectionFactory.getConnection();
        }
        catch (DatabaseException ex)
        {
            throw new EPException("Error obtaining connection", ex);
        }

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement(preparedStatementText);
        }
        catch (SQLException ex)
        {
            throw new EPException("Error preparing statement '" + preparedStatementText + "'", ex);
        }

        for (int i = 0; i < lookupEvents.length; i++)
        {
            try
            {
                execute(preparedStatement, lookupEvents[i], joinSet);
            }
            catch (EPException ex)
            {
                try
                {
                    preparedStatement.close();
                }
                catch (SQLException e) {}
                try
                {
                    connection.close();
                }
                catch (SQLException e) {}
                throw ex;
            }
        }

        try
        {
            preparedStatement.close();
        }
        catch (SQLException ex)
        {
            try
            {
                connection.close();
            }
            catch (SQLException e) {}
            throw new EPException("Error closing statement", ex);
        }

        try
        {
            connection.close();
        }
        catch (SQLException ex)
        {
            throw new EPException("Error closing statement", ex);
        }
    }

    private void execute(PreparedStatement preparedStatement,
                         EventBean lookupEvent,
                         Set<MultiKey<EventBean>> joinSet)
    {
        // set parameters
        int count = 1;
        for (DBStatementMetaData.DBInputDesc inputType : metaData.getInputPropertyTypes())
        {
            String propertyName = inputType.getEventPropName();
            Object value = lookupEvent.get(propertyName);

            try
            {
                preparedStatement.setObject(count, value);
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
        try
        {
            while (resultSet.next())
            {
                Map<String, Object> row = new HashMap<String, Object>();
                for (Map.Entry<String, DBStatementMetaData.DBOutputDesc> entry : metaData.getPropertiesOut().entrySet())
                {
                    String columnName = entry.getKey();
                    Object value = resultSet.getObject(columnName);
                    row.put(columnName, value);
                }
                EventBean eventBeanRow = eventAdapterService.createMapFromValues(row, eventType);
                joinSet.add(new MultiKey<EventBean>(new EventBean[] {lookupEvent, eventBeanRow}));
                // TODO: does the order matter here
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
            throw new EPException("Error executing statement '" + preparedStatementText + "'", ex);
        }
    }



    // also verify dbinput params against the event stream(s) via StreamReuseService

    // get connection or use existing connection
    // prepare statement or use prepared statement
    // for each lookup event:
    //  set parameters on prepared statement
    //  execute
    //  map result columns to EventBean instances of event type
    //  add to joinSet
    // may close statement and connection

    //Connection connection = databaseRefService.getConnection(databaseName);


    protected static void setParams(PreparedStatement statement, EventBean event, List<DBStatementMetaData.DBInputDesc> inputDescs)
    {
        int count = 1;
        for (DBStatementMetaData.DBInputDesc inputDesc : inputDescs)
        {
            Object value = event.get(inputDesc.getEventPropName());
            try
            {
                statement.setObject(count, value);
            }
            catch (SQLException ex)
            {
                // TODO
            }
            count++;
        }
    }

    public View addView(View view)
    {
        return view;
    }

    public List<View> getViews()
    {
        throw new UnsupportedOperationException("Subviews not supported");
    }

    public boolean removeView(View view)
    {
        throw new UnsupportedOperationException("Subviews not supported");
    }

    public boolean hasViews()
    {
        return false;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        throw new UnsupportedOperationException("Iterator not supported");
    }
}
