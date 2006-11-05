package net.esper.view;

import net.esper.eql.spec.DBStatementStreamSpec;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.core.DatabaseRefException;
import net.esper.eql.core.DatabaseRefService;
import net.esper.util.PlaceholderParser;
import net.esper.util.PlaceholderParseException;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBStatementViewFactory
{
    public static Viewable createDBStatementView(DBStatementStreamSpec databaseStreamSpec, DatabaseRefService databaseRefService, EventAdapterService eventAdapterService)
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
        Connection connection = null;
        try
        {
            connection = databaseRefService.getConnection(databaseName);
        }
        catch (DatabaseRefException ex)
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
        DBStatementMetaData metaData = new DBStatementMetaData();

        try
        {
            ParameterMetaData parameterMetaData = prepared.getParameterMetaData();
            for (int i = 0; i < parameterMetaData.getParameterCount(); i++)
            {
                metaData.addInputParam(parameters[i], parameterMetaData.getParameterType(i + 1));
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

        try
        {
            ResultSetMetaData resultMetaData = prepared.getMetaData();
            for (int i = 0; i < resultMetaData.getColumnCount(); i++)
            {
                int columnType = resultMetaData.getColumnType(i + 1);
                String javaClass = resultMetaData.getColumnTypeName(i + 1);
                metaData.addOutputParam(resultMetaData.getColumnName(i + 1), columnType, javaClass);
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
            String text = "Error obtaining result metadata from prepared statement '" + preparedStatementText + "'";
            log.error(text, ex);
            throw new ExprValidationException(text + ", please check the statement, reason: " + ex.getMessage());
        }

        log.debug(".createDBEventStream metadata=" + metaData.toString());

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

        // Now we know what the SQL query generates as results, construct an event type
        Map<String, Class> dbEventType = metaData.getOutputEventType();
        EventType eventType = eventAdapterService.createAnonymousMapType(dbEventType);

        return new DBHistoricalEventViewable(eventType);
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

    private static final Log log = LogFactory.getLog(DBStatementViewFactory.class);
}
