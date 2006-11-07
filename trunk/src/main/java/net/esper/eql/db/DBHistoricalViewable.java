package net.esper.eql.db;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.event.EventAdapterService;
import net.esper.event.EventPropertyGetter;
import net.esper.view.HistoricalEventViewable;
import net.esper.view.View;
import net.esper.client.EPException;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.PropertyResolutionDescriptor;
import net.esper.eql.core.StreamTypesException;
import net.esper.eql.expression.ExprValidationException;
import net.esper.util.SQLTypeMapUtil;
import net.esper.collection.Pair;

import java.util.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Viewable providing historical data from a database.
 */
public class DBHistoricalViewable implements HistoricalEventViewable
{
    private final int myStreamNumber;
    private final EventAdapterService eventAdapterService;
    private final String preparedStatementText;
    private final List<DBInputParameterDesc> inputParameters;
    private final Map<String, DBOutputTypeDesc> outputTypes;
    private final EventType eventType;

    private final ConnectionCache connectionCache;
    private EventPropertyGetter[] getters;
    private int[] getterStreamNumbers;

    /**
     * Ctor.
     * @param myStreamNumber is the stream number of the viewable
     * @param eventAdapterService for generating event beans
     * @param preparedStatementText is the SQL to use for polling
     * @param inputParameters are PreparedStatement input parameter descriptions
     * @param outputTypes describe columns selected by the SQL
     * @param databaseConnectionFactory for obtaining connections
     * @param retainConnection is the policy for connection allocation
     */
    public DBHistoricalViewable(int myStreamNumber,
                                EventAdapterService eventAdapterService,
                                String preparedStatementText,
                                List<DBInputParameterDesc> inputParameters,
                                Map<String, DBOutputTypeDesc> outputTypes,
                                DatabaseConnectionFactory databaseConnectionFactory,
                                boolean retainConnection)
    {
        this.myStreamNumber = myStreamNumber;
        this.eventAdapterService = eventAdapterService;
        this.preparedStatementText = preparedStatementText;
        this.inputParameters = inputParameters;
        this.outputTypes = outputTypes;

        // Construct an event type from SQL query results
        Map<String, Class> eventTypeFields = new HashMap<String, Class>();
        for (String name : outputTypes.keySet())
        {
            DBOutputTypeDesc dbOutputDesc = outputTypes.get(name);
            Class clazz = SQLTypeMapUtil.sqlTypeToClass(dbOutputDesc.getSqlType(), dbOutputDesc.getClassName());
            eventTypeFields.put(name, clazz);
        }
        eventType = eventAdapterService.createAnonymousMapType(eventTypeFields);

        if (retainConnection)
        {
            connectionCache = new ConnectionCacheImpl(databaseConnectionFactory, preparedStatementText);
        }
        else
        {
            connectionCache = new ConnectionNoCacheImpl(databaseConnectionFactory, preparedStatementText);
        }
    }

    public void stop()
    {
        connectionCache.destroy();
    }

    public void validate(StreamTypeService streamTypeService) throws ExprValidationException
    {
        getters = new EventPropertyGetter[inputParameters.size()];
        getterStreamNumbers = new int[inputParameters.size()];

        int count = 0;
        for (DBInputParameterDesc inputParam : inputParameters)
        {
            String eventPropertyName = inputParam.getEventPropName();
            PropertyResolutionDescriptor desc = null;

            // try to resolve the property name alone
            try
            {
                desc = streamTypeService.resolveByStreamAndPropName(eventPropertyName);
            }
            catch (StreamTypesException ex)
            {
                throw new ExprValidationException("Property '" + eventPropertyName + "' failed to resolve, reason: " + ex.getMessage());
            }

            // hold on to getter and stream number for each stream
            int streamId = desc.getStreamNum();
            if (streamId == myStreamNumber)
            {
                throw new ExprValidationException("Invalid property '" + eventPropertyName + "' resolves to the historical data itself");
            }
            String propName = desc.getPropertyName();
            getters[count] = streamTypeService.getEventTypes()[streamId].getGetter(propName);
            getterStreamNumbers[count] = streamId;

            count++;
        }
    }

    public List<EventBean>[] poll(EventBean[][] lookupEventsPerStream)
    {
        Pair<Connection, PreparedStatement> resources = connectionCache.getConnection();
        PreparedStatement preparedStatement = resources.getSecond();

        List<EventBean>[] result = new LinkedList[lookupEventsPerStream.length];
        for (int i = 0; i < lookupEventsPerStream.length; i++)
        {
            try
            {
                result[i] = execute(preparedStatement, lookupEventsPerStream[i]);
            }
            catch (EPException ex)
            {
                connectionCache.doneWith(resources);
                throw ex;
            }
        }

        connectionCache.doneWith(resources);
        
        return result;
    }

    private List<EventBean> execute(PreparedStatement preparedStatement,
                                EventBean[] lookupEventsPerStream)
    {
        // set parameters
        int count = 1;
        for (int i = 0; i < getters.length; i++)
        {
            EventBean streamEvent = lookupEventsPerStream[getterStreamNumbers[i]];
            Object value = getters[i].get(streamEvent);

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
            throw new EPException("Error executing statement '" + preparedStatementText + "'", ex);
        }

        return rows;
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
