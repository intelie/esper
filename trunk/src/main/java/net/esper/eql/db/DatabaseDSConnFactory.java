package net.esper.eql.db;

import net.esper.client.ConfigurationDBRef;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database connection factory using {@link InitialContext} and {@link DataSource} to obtain connections.
 */
public class DatabaseDSConnFactory implements DatabaseConnectionFactory
{
    private ConfigurationDBRef.DataSourceConnection dsConfig;

    /**
     * Ctor.
     * @param dsConfig is the datasource object name and initial context properties.
     */
    public DatabaseDSConnFactory(ConfigurationDBRef.DataSourceConnection dsConfig)
    {
        this.dsConfig = dsConfig;
    }

    public Connection getConnection() throws DatabaseException
    {
        Properties envProps = dsConfig.getEnvProperties();
        if (envProps == null)
        {
            envProps = new Properties();
        }

        InitialContext ctx = null;
        try
        {
            if (envProps.size() > 0)
            {
                ctx = new InitialContext(envProps);
            }
            else
            {
                ctx = new InitialContext();
            }
        }
        catch (NamingException ex)
        {
            throw new DatabaseException("Error instantiating initial context", ex);
        }

        DataSource ds = null;
        String lookupName = dsConfig.getContextLookupName();
        try
        {
            ds = (DataSource)ctx.lookup(lookupName);
        }
        catch (NamingException ex)
        {
            throw new DatabaseException("Error looking up data source in context using name '" + lookupName + "'", ex);
        }

        if (ds == null)
        {
            throw new DatabaseException("Null data source obtained through context using name '" + lookupName + "'");
        }

        Connection connection = null;
        try
        {
            connection = ds.getConnection();
        }
        catch (SQLException ex)
        {
            String detail = "SQLException: " + ex.getMessage() +
                    " SQLState: " + ex.getSQLState() +
                    " VendorError: " + ex.getErrorCode();

            throw new DatabaseException("Error obtaining database connection using datasource " +
                    "with detail " + detail
                    , ex);
        }

        return connection;
    }
}
