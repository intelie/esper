using System;
using System.Data.Common;
using System.Configuration;
using System.Collections;
using System.Collections.Specialized;

namespace net.esper.client
{
    /// <summary>
	/// Marker for different connection factory settings.
	/// </summary>
    
	public interface ConnectionFactoryDesc
    {
    }

    /// <summary>
    /// Connection factory settings for using a DbProviderFactory.
    /// </summary>

    public class DbProviderFactoryConnection : ConnectionFactoryDesc
    {
        private ConnectionStringSettings settings ;

        /// <summary>
        /// Gets the connection settings.
        /// </summary>
        
        virtual public ConnectionStringSettings Settings
        {
            get { return settings; }
        }

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="providerName">The name of the provider.</param>
        /// <param name="properties">Properties that should be applied to the connection.</param>
        
        public DbProviderFactoryConnection(String providerName, NameValueCollection properties)
        {
            String name = null;

            try
            {
                DbProviderFactory dbProviderFactory = DbProviderFactories.GetFactory(providerName);
                DbConnectionStringBuilder builder = dbProviderFactory.CreateConnectionStringBuilder();

                foreach( String key in properties )
                {
                    String value = properties[key] ;
                    if (key.Equals("name", StringComparison.CurrentCultureIgnoreCase))
                    {
                        name = value;
                    }
                    else
                    {
                        builder.Add(key, value);
                    }
                }

                this.settings = new ConnectionStringSettings();
                this.settings.ProviderName = providerName;
                this.settings.ConnectionString = builder.ConnectionString;

                if (name != null)
                {
                    this.settings.Name = name;
                }
            }
            catch (Exception)
            {
                throw;
            }
        }

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="settings">The settings.</param>
        
        public DbProviderFactoryConnection(ConnectionStringSettings settings)
        {
            this.settings = settings;
        }
    }
}
