using System;
using System.Data.Common;
using System.Configuration;
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
        /// <value>The settings.</value>
        
        virtual public ConnectionStringSettings Settings
        {
            get { return settings; }
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
