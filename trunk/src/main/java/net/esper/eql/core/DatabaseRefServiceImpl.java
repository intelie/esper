package net.esper.eql.core;

import net.esper.client.ConfigurationDBRef;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class DatabaseRefServiceImpl implements DatabaseRefService
{
    private final Map<String, ConfigurationDBRef> mapDatabaseRef;

    public DatabaseRefServiceImpl(Map<String, ConfigurationDBRef> mapDatabaseRef)
    {
        this.mapDatabaseRef = mapDatabaseRef;
    }

    // TODO: factor into separate classes the connection get
    // TODO: rename class
    // (1) to enable constructors to do some initial-one-time config such as load driver class
    // (2) to reuse the class to get more connections
    public Connection getConnection(String databaseName) throws DatabaseRefException
    {
        if (!mapDatabaseRef.containsKey(databaseName))
        {
            throw new DatabaseRefException("Cannot locate configuration information for database '" + databaseName + "'");
        }

        ConfigurationDBRef config = mapDatabaseRef.get(databaseName);
        Connection connection = null;
        if (config.getConnectionFactoryDesc() instanceof ConfigurationDBRef.DriverManagerConnection)
        {
            connection = getConnection((ConfigurationDBRef.DriverManagerConnection) config.getConnectionFactoryDesc());
        }
        else
        {
            connection = getConnection((ConfigurationDBRef.DataSourceConnection) config.getConnectionFactoryDesc());
        }

        return connection;
    }

    private Connection getConnection(ConfigurationDBRef.DriverManagerConnection driverConfig) throws DatabaseRefException
    {
        // load driver class
        String driverClassName = driverConfig.getClassName();
        try
        {
            Class.forName(driverClassName);
        }
        catch (ClassNotFoundException ex)
        {
            throw new DatabaseRefException("Error loading driver class '" + driverClassName + "'", ex);
        }
        catch (RuntimeException ex)
        {
            throw new DatabaseRefException("Error loading driver class '" + driverClassName + "'", ex);
        }

        // get driver manager
        Connection connection = null;
        String url = driverConfig.getUrl();
        Properties properties = driverConfig.getOptionalProperties();
        if (properties == null)
        {
            properties = new Properties();
        }
        try
        {
            String user = driverConfig.getOptionalUserName();
            String pwd = driverConfig.getOptionalPassword();
            if ((user == null) && (pwd == null) && (properties.size() == 0))
            {
                connection = DriverManager.getConnection(url);
            }
            else if (properties.size() > 0)
            {
                connection = DriverManager.getConnection(url, properties);
            }
            else
            {
                connection = DriverManager.getConnection(url, user, pwd);
            }
        }
        catch (SQLException ex)
        {
            String detail = "SQLException: " + ex.getMessage() +
                    " SQLState: " + ex.getSQLState() +
                    " VendorError: " + ex.getErrorCode();

            throw new DatabaseRefException("Error obtaining database connection using url '" + url +
                    "' with detail " + detail
                    , ex);
        }

        return connection;
    }

    private Connection getConnection(ConfigurationDBRef.DataSourceConnection dsConfig) throws DatabaseRefException
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
            throw new DatabaseRefException("Error instantiating initial context", ex);
        }

        DataSource ds = null;
        String lookupName = dsConfig.getContextLookupName();
        try
        {
            ds = (DataSource)ctx.lookup(lookupName);
        }
        catch (NamingException ex)
        {
            throw new DatabaseRefException("Error looking up data source in context using name '" + lookupName + "'", ex);
        }

        if (ds == null)
        {
            throw new DatabaseRefException("Null data source obtained through context using name '" + lookupName + "'");
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

            throw new DatabaseRefException("Error obtaining database connection using datasource " +
                    "with detail " + detail
                    , ex);
        }

        return connection;
    }

    public void returnConnection(String databaseName, Connection connection) throws DatabaseRefException
    {
        return;
    }
}
