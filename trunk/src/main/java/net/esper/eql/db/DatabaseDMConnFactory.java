package net.esper.eql.db;

import net.esper.client.ConfigurationDBRef;
import net.esper.eql.db.DatabaseConnectionFactory;
import net.esper.eql.db.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database connection factory using {@link DriverManager} to obtain connections.
 */
public class DatabaseDMConnFactory implements DatabaseConnectionFactory
{
    private ConfigurationDBRef.DriverManagerConnection driverConfig;

    /**
     * Ctor.
     * @param driverConfig is the driver manager configuration
     * @throws DatabaseException thrown if the driver class cannot be loaded
     */
    public DatabaseDMConnFactory(ConfigurationDBRef.DriverManagerConnection driverConfig)
            throws DatabaseException
    {
        this.driverConfig = driverConfig;

        // load driver class
        String driverClassName = driverConfig.getClassName();
        try
        {
            Class.forName(driverClassName);
        }
        catch (ClassNotFoundException ex)
        {
            throw new DatabaseException("Error loading driver class '" + driverClassName + "'", ex);
        }
        catch (RuntimeException ex)
        {
            throw new DatabaseException("Error loading driver class '" + driverClassName + "'", ex);
        }
    }

    public Connection getConnection() throws DatabaseException
    {
        // use driver manager to get a connection
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

            throw new DatabaseException("Error obtaining database connection using url '" + url +
                    "' with detail " + detail
                    , ex);
        }

        return connection;
    }
}
