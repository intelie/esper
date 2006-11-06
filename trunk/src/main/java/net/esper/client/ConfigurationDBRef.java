package net.esper.client;

import java.util.Properties;

public class ConfigurationDBRef
{
    private ConnectionFactoryDesc connectionFactoryDesc;
    private ConnectionLifecycleEnum connectionLifecycleEnum;

    public ConfigurationDBRef()
    {
        connectionLifecycleEnum = ConnectionLifecycleEnum.RETAIN;
    }

    public void setDataSourceConnection(String contextLookupName, Properties environmentProps)
    {
        connectionFactoryDesc = new DataSourceConnection(contextLookupName, environmentProps);
    }

    public void setDriverManagerConnection(String className, String url, Properties connectionArgs)
    {
        connectionFactoryDesc = new DriverManagerConnection(className, url, connectionArgs);
    }

    public void setDriverManagerConnection(String className, String url, String username, String password)
    {
        connectionFactoryDesc = new DriverManagerConnection(className, url, username, password);
    }

    public void setDriverManagerConnection(String className, String url, String username, String password, Properties properties)
    {
        connectionFactoryDesc = new DriverManagerConnection(className, url, username, password, properties);
    }

    public ConnectionLifecycleEnum getConnectionLifecycleEnum()
    {
        return connectionLifecycleEnum;
    }

    public void setConnectionLifecycleEnum(ConnectionLifecycleEnum connectionLifecycleEnum)
    {
        this.connectionLifecycleEnum = connectionLifecycleEnum;
    }

    public ConnectionFactoryDesc getConnectionFactoryDesc()
    {
        return connectionFactoryDesc;
    }

    public enum ConnectionLifecycleEnum {
        RETAIN,
        POOLED;
    }

    public interface ConnectionFactoryDesc{}

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
