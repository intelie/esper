using System;
using System.Data;
using System.Data.Common;

namespace net.esper.client
{
    /// <summary>
	/// Supplies connection-level settings for a given database name.
	/// </summary>
    
	public class ConnectionSettings
    {
        private Boolean autoCommit;
        private String catalog;
        private Boolean readOnly;
        private IsolationLevel? transactionIsolation;

        /// <summary> Returns a boolean indicating auto-commit, or null if not set and default accepted.</summary>
        /// <returns> true for auto-commit on, false for auto-commit off, or null to accept the default
        /// </returns>
        /// <summary> Indicates whether to set any new connections for this database to auto-commit.</summary>
        /// <param name="autoCommit">true to set connections to auto-commit, or false, or null to not set this value on a new connection
        /// </param>
        
        virtual public Boolean AutoCommit
        {
            get { return autoCommit; }
            set { this.autoCommit = value; }
        }

        /// <summary> Gets the name of the catalog to set on new database connections, or null for default.</summary>
        /// <returns> name of the catalog to set, or null to accept the default
        /// </returns>
        /// <summary> Sets the name of the catalog on new database connections.</summary>
        /// <param name="catalog">is the name of the catalog to set, or null to accept the default
        /// </param>
        
        virtual public String Catalog
        {
            get { return catalog; }
            set { this.catalog = value; }
        }

        /// <summary> Returns a boolean indicating read-only, or null if not set and default accepted.</summary>
        /// <returns> true for read-only on, false for read-only off, or null to accept the default
        /// </returns>
        /// <summary> Indicates whether to set any new connections for this database to read-only.</summary>
        /// <param name="readOnly">true to set connections to read-only, or false, or null to not set this value on a new connection
        /// </param>

        virtual public Boolean ReadOnly
        {
            get { return readOnly; }
            set { this.readOnly = value; }
        }

        /// <summary> Returns the connection settings for transaction isolation level.</summary>
        /// <returns> transaction isolation level
        /// </returns>
        /// <summary>
        /// Sets the transaction isolation level for new database connections,
        /// can be null to accept the default.
        /// </summary>

        virtual public IsolationLevel? TransactionIsolation
        {
	        get { return transactionIsolation; }
        	set { this.transactionIsolation = value; }
        }
    }
}
