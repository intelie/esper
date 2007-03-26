using System;

namespace net.esper.compat
{
    /// <summary>
    /// Constants we keep for our locking algorithms.
    /// </summary>

	public class LockConstants
	{
        /// <summary>
        /// Number of milliseconds until read locks timeout
        /// </summary>
		public const int ReaderTimeout = 5000 ;
        /// <summary>
        /// Number of milliseconds until write locks timeout
        /// </summary>
		public const int WriterTimeout = 10000 ;
	}
}
