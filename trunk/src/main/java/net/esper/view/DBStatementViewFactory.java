package net.esper.view;

import net.esper.eql.spec.DBStatementStreamSpec;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.core.DatabaseRefException;
import net.esper.eql.core.DatabaseRefService;

import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBStatementViewFactory
{
    public static EventStream createDBEventStream(DBStatementStreamSpec databaseStreamSpec, DatabaseRefService databaseRefService)
            throws ExprValidationException
    {
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

        String sql = databaseStreamSpec.getSqlWithSubsParams();
        PreparedStatement prepared = null;

        try
        {
            prepared = connection.prepareStatement(sql);

            ParameterMetaData parameterMetaData = prepared.getParameterMetaData();
            for (int i = 0; i < parameterMetaData.getParameterCount(); i++)
            {
                int num = i + 1;
                System.out.println("Input column " + num + " type=" + parameterMetaData.getParameterTypeName(num));
            }

            ResultSetMetaData resultMetaData = prepared.getMetaData();
            for (int i = 0; i < resultMetaData.getColumnCount(); i++)
            {
                int num = i + 1;
                System.out.println("Output column " + num + " field=" + resultMetaData.getColumnName(num) +
                        " " + resultMetaData.getColumnTypeName(num));
            }
        }
        catch (SQLException ex)
        {
            String text = "Error executing statement '" + sql + "'";
            log.error(text, ex);
            throw new ExprValidationException(text + ", reason: " + ex.getMessage());
        }


        return null;
    }

    private static final Log log = LogFactory.getLog(DBStatementViewFactory.class);
}
