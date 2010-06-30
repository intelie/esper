package com.espertech.esper.support.epl;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SupportDriverManagerDataSource implements DataSource
{
    private Properties properties;

    public SupportDriverManagerDataSource(Properties properties)
    {
        this.properties = properties;
    }

    public Connection getConnection() throws SQLException
    {
        return getConnectionInternal();
    }

    public Connection getConnection(String username, String password) throws SQLException
    {
        throw new UnsupportedOperationException();
    }

    private Connection getConnectionInternal()
    {
        // load driver class
        String driverClassName = properties.getProperty("driverClassName");
        try
        {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Class.forName(driverClassName, true, cl);
        }
        catch (ClassNotFoundException ex)
        {
            throw new RuntimeException("Error loading driver class '" + driverClassName + '\'', ex);
        }
        catch (RuntimeException ex)
        {
            throw new RuntimeException("Error loading driver class '" + driverClassName + '\'', ex);
        }

        // use driver manager to get a connection
        Connection connection;
        String url = properties.getProperty("url");
        String user = properties.getProperty("username");
        String pwd = properties.getProperty("password");

        try
        {
            connection = DriverManager.getConnection(url, user, pwd);
        }
        catch (SQLException ex)
        {
            String detail = "SQLException: " + ex.getMessage() +
                    " SQLState: " + ex.getSQLState() +
                    " VendorError: " + ex.getErrorCode();

            throw new RuntimeException("Error obtaining database connection using url '" + url +
                    "' with detail " + detail
                    , ex);
        }

        return connection;
    }

    public PrintWriter getLogWriter() throws SQLException
    {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException
    {
    }

    public void setLoginTimeout(int seconds) throws SQLException
    {
    }

    public int getLoginTimeout() throws SQLException
    {
        return 0;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException
    {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException
    {
        return false;
    }
}
