package net.esper.client;

import java.util.Properties;

/**
 * Container for database configuration information, such as
 * options around getting a database connection and options to control the lifecycle
 * of connections and set connection parameters.
 */
public class ConfigurationDBRef
{
    private ConnectionFactoryDesc connectionFactoryDesc;
    private ConnectionLifecycleEnum connectionLifecycleEnum;

    /**
     * Ctor.
     */
    public ConfigurationDBRef()
    {
        connectionLifecycleEnum = ConnectionLifecycleEnum.RETAIN;
    }

    /**
     * Sets the connection factory to use {@link javax.sql.DataSource} to obtain a
     * connection.
     * @param contextLookupName is the object name to look up via {@link javax.naming.InitialContext}
     * @param environmentProps are the optional properties to pass to the context
     */
    public void setDataSourceConnection(String contextLookupName, Properties environmentProps)
    {
        connectionFactoryDesc = new DataSourceConnection(contextLookupName, environmentProps);
    }

    /**
     * Sets the connection factory to use {@link java.sql.DriverManager} to obtain a
     * connection.
     * @param className is the driver class name to lookup up via Class.forName
     * @param url is the URL
     * @param connectionArgs are optional connection arguments
     */
    public void setDriverManagerConnection(String className, String url, Properties connectionArgs)
    {
        connectionFactoryDesc = new DriverManagerConnection(className, url, connectionArgs);
    }

    /**
     * Sets the connection factory to use {@link java.sql.DriverManager} to obtain a
     * connection.
     * @param className is the driver class name to lookup up via Class.forName
     * @param url is the URL
     * @param username is the username to obtain a connection
     * @param password is the password to obtain a connection
     */
    public void setDriverManagerConnection(String className, String url, String username, String password)
    {
        connectionFactoryDesc = new DriverManagerConnection(className, url, username, password);
    }

    /**
     * Sets the connection factory to use {@link java.sql.DriverManager} to obtain a
     * connection.
     * @param className is the driver class name to lookup up via Class.forName
     * @param url is the URL
     * @param username is the username to obtain a connection
     * @param password is the password to obtain a connection
     * @param connectionArgs are optional connection arguments
     */
    public void setDriverManagerConnection(String className, String url, String username, String password, Properties connectionArgs)
    {
        connectionFactoryDesc = new DriverManagerConnection(className, url, username, password, connectionArgs);
    }

    /**
     * Returns the setting to control whether a new connection is obtained for each lookup,
     * or connections are retained between lookups.
     * @return enum controlling connection allocation
     */
    public ConnectionLifecycleEnum getConnectionLifecycleEnum()
    {
        return connectionLifecycleEnum;
    }

    /**
     * Controls whether a new connection is obtained for each lookup,
     * or connections are retained between lookups.
     * @param connectionLifecycleEnum is an enum controlling connection allocation
     */
    public void setConnectionLifecycleEnum(ConnectionLifecycleEnum connectionLifecycleEnum)
    {
        this.connectionLifecycleEnum = connectionLifecycleEnum;
    }

    /**
     * Returns the descriptor controlling connection creation settings.
     * @return connection factory settings
     */
    public ConnectionFactoryDesc getConnectionFactoryDesc()
    {
        return connectionFactoryDesc;
    }

    public enum ConnectionLifecycleEnum {
        /**
         * Retain a connection between lookup, not getting a new connection each lookup.
         */
        RETAIN,

        /**
         * Obtain a new connection each lookup closing the connection when done.
         */
        POOLED;
    }

    /**
     * Marker for different connection factory settings.
     */
    public interface ConnectionFactoryDesc{}

    /**
     * Connection factory settings for using a DataSource.
     */
    public class DataSourceConnection implements ConnectionFactoryDesc
    {
        private String contextLookupName;
        private Properties envProperties;

        public DataSourceConnection(String contextLookupName, Properties envProperties)
        {
            this.contextLookupName = contextLookupName;
            this.envProperties = envProperties;
        }

        public String getContextLookupName()
        {
            return contextLookupName;
        }

        public Properties getEnvProperties()
        {
            return envProperties;
        }
    }

    /**
     * Connection factory settings for using a DriverManager.
     */
    public class DriverManagerConnection implements ConnectionFactoryDesc
    {
        private String className;
        private String url;
        private String optionalUserName;
        private String optionalPassword;
        private Properties optionalProperties;

        public DriverManagerConnection(String className, String url, Properties optionalProperties)
        {
            this.className = className;
            this.url = url;
            this.optionalProperties = optionalProperties;
        }

        public DriverManagerConnection(String className, String url, String optionalUserName, String optionalPassword)
        {
            this.className = className;
            this.url = url;
            this.optionalUserName = optionalUserName;
            this.optionalPassword = optionalPassword;
        }

        public DriverManagerConnection(String className, String url, String optionalUserName, String optionalPassword, Properties optionalProperties)
        {
            this.className = className;
            this.url = url;
            this.optionalUserName = optionalUserName;
            this.optionalPassword = optionalPassword;
            this.optionalProperties = optionalProperties;
        }

        public String getClassName()
        {
            return className;
        }

        public String getUrl()
        {
            return url;
        }

        public String getOptionalUserName()
        {
            return optionalUserName;
        }

        public String getOptionalPassword()
        {
            return optionalPassword;
        }

        public Properties getOptionalProperties()
        {
            return optionalProperties;
        }
    }
}
