using System;

namespace net.esper.client
{
    /// <summary>
	/// Enum controlling connection lifecycle.
	/// </summary>
    
	public enum ConnectionLifecycleEnum
    {
        /// <summary>Retain connection between lookups, not getting a new connection each lookup.</summary>
        RETAIN,
        /// <summary>Obtain a new connection each lookup closing the connection when done.</summary>
        POOLED
    }
}
