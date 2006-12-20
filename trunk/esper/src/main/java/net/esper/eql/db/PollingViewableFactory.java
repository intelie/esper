package net.esper.eql.db;

import net.esper.eql.spec.DBStatementStreamSpec;
import net.esper.eql.expression.ExprValidationException;
import net.esper.util.PlaceholderParser;
import net.esper.util.PlaceholderParseException;
import net.esper.util.SQLTypeMapUtil;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.view.HistoricalEventViewable;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for a view onto historical data via SQL statement.
 */
public class PollingViewableFactory
{
    /**
     * Creates the viewable for polling via database SQL query.
     * @param streamNumber is the stream number of the view
     * @param databaseStreamSpec provides the SQL statement, database name and additional info
     * @param databaseConfigService for getting database connection and settings
     * @param eventAdapterService for generating event beans from database information
     * @return viewable providing poll functionality
     * @throws ExprValidationException if the validation failed
     */
    public static HistoricalEventViewable createDBStatementView(int streamNumber,
                                                 DBStatementStreamSpec databaseStreamSpec,
                                                 DatabaseConfigService databaseConfigService,
                                                 EventAdapterService eventAdapterService
                                                 )
            throws ExprValidationException
    {
        // Parse the SQL for placeholders and text fragments
        List<PlaceholderParser.Fragment> sqlFragments = null;
        try
        {
            sqlFragments = PlaceholderParser.parsePlaceholder(databaseStreamSpec.getSqlWithSubsParams());
        }
        catch (PlaceholderParseException ex)
        {
            String text = "Error parsing SQL";
            throw new ExprValidationException(text + ", reason: " + ex.getMessage());
        }

        // Assemble a PreparedStatement and parameter list
        String preparedStatementText = createPreparedStatement(sqlFragments);
        String[] parameters = getParameters(sqlFragments);
        log.debug(".createDBEventStream preparedStatementText=" + preparedStatementText +
                " parameters=" + Arrays.toString(parameters));

        // Get a database connection
        String databaseName = databaseStreamSpec.getDatabaseName();
        DatabaseConnectionFactory databaseConnectionFactory = null;
        try
        {
            databaseConnectionFactory = databaseConfigService.getConnectionFactory(databaseName);
        }
        catch (DatabaseConfigException ex)
        {
            String text = "Error connecting to database '" + databaseName + "'";
            log.error(text, ex);
            throw new ExprValidationException(text + ", reason: " + ex.getMessage());
        }

        Connection connection = null;
        try
        {
            connection = databaseConnectionFactory.getConnection();
        }
        catch (DatabaseConfigException ex)
        {
            String text = "Error connecting to database '" + databaseName + "'";
            log.error(text, ex);
            throw new ExprValidationException(text + ", reason: " + ex.getMessage());
        }

        // Execute prepared statement
        PreparedStatement prepared = null;
        try
        {
            prepared = connection.prepareStatement(preparedStatementText);
        }
        catch (SQLException ex)
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                // don't handle
            }
            String text = "Error executing statement '" + preparedStatementText + "'";
            log.error(text, ex);
            throw new ExprValidationException(text + ", reason: " + ex.getMessage());
        }

        // Interrogate prepared statement - parameters and result
        List<String> inputParameters = new LinkedList<String>();
        try
        {
            ParameterMetaData parameterMetaData = prepared.getParameterMetaData();
            for (int i = 0; i < parameterMetaData.getParameterCount(); i++)
            {
                inputParameters.add(parameters[i]);
            }
        }
        catch (Exception ex)
        {
            try
            {
                prepared.close();
            }
            catch (SQLException e)
            {
                // don't handle
            }
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                // don't handle
            }
            String text = "Error obtaining parameter metadata from prepared statement '" + preparedStatementText + "'";
            log.error(text, ex);
            throw new ExprValidationException(text + ", please check the statement, reason: " + ex.getMessage());
        }

        Map<String, DBOutputTypeDesc> outputProperties = new HashMap<String, DBOutputTypeDesc>();
        try
        {
            ResultSetMetaData resultMetaData = prepared.getMetaData();
            for (int i = 0; i < resultMetaData.getColumnCount(); i++)
            {
                String columnName = resultMetaData.getColumnName(i + 1);
                int columnType = resultMetaData.getColumnType(i + 1);
                String javaClass = resultMetaData.getColumnTypeName(i + 1);
                DBOutputTypeDesc outputType = new DBOutputTypeDesc(columnType, javaClass);
                outputProperties.put(columnName, outputType);
            }
        }
        catch (Exception ex)
        {
            try
            {
                prepared.close();
            }
            catch (SQLException e)
            {
                // don't handle
            }
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                // don't handle
            }
            String text = "Error in statement '" + preparedStatementText + "', failed to obtain result metadata";
            log.error(text, ex);
            throw new ExprValidationException(text + ", please check the statement, reason: " + ex.getMessage());
        }

        log.debug(".createDBEventStream in=" + inputParameters.toString() +
                " out=" + outputProperties.toString());

        // Close statement
        try
        {
            prepared.close();
        }
        catch (SQLException e)
        {
            try
            {
                connection.close();
            }
            catch (SQLException ex)
            {
                // don't handle
            }
            String text = "Error clsoing prepared statement";
            log.error(text, e);
            throw new ExprValidationException(text + ", reason: " + e.getMessage());
        }

        // Close connection
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            String text = "Error clsoing connection";
            log.error(text, e);
            throw new ExprValidationException(text + ", reason: " + e.getMessage());
        }

        // Create event type
        // Construct an event type from SQL query result metadata
        Map<String, Class> eventTypeFields = new HashMap<String, Class>();
        for (String name : outputProperties.keySet())
        {
            DBOutputTypeDesc dbOutputDesc = outputProperties.get(name);
            Class clazz = SQLTypeMapUtil.sqlTypeToClass(dbOutputDesc.getSqlType(), dbOutputDesc.getClassName());
            eventTypeFields.put(name, clazz);
        }
        EventType eventType = eventAdapterService.createAnonymousMapType(eventTypeFields);

        // Get a proper connection and data cache
        ConnectionCache connectionCache = null;
        DataCache dataCache = null;
        try
        {
            connectionCache = databaseConfigService.getConnectionCache(databaseName, preparedStatementText);
            dataCache = databaseConfigService.getDataCache(databaseName);
        }
        catch (DatabaseConfigException e)
        {
            String text = "Error obtaining cache configuration";
            log.error(text, e);
            throw new ExprValidationException(text + ", reason: " + e.getMessage());
        }

        PollExecStrategyDBQuery dbPollStrategy = new PollExecStrategyDBQuery(eventAdapterService,
                eventType, connectionCache, preparedStatementText, outputProperties);

        return new PollingViewable(streamNumber, inputParameters, dbPollStrategy, dataCache, eventType);
    }

    private static String createPreparedStatement(List<PlaceholderParser.Fragment> parseFragements)
    {
        StringBuffer buffer = new StringBuffer();
        for (PlaceholderParser.Fragment fragment : parseFragements)
        {
            if (!fragment.isParameter())
            {
                buffer.append(fragment.getValue());
            }
            else
            {
                buffer.append("?");
            }
        }
        return buffer.toString();
    }

    private static String[] getParameters(List<PlaceholderParser.Fragment> parseFragements)
    {
        List<String> eventPropertyParams = new LinkedList<String>();
        for (PlaceholderParser.Fragment fragment : parseFragements)
        {
            if (fragment.isParameter())
            {
                eventPropertyParams.add(fragment.getValue());
            }
        }
        return eventPropertyParams.toArray(new String[0]);
    }

    private static final Log log = LogFactory.getLog(PollingViewableFactory.class);
}
