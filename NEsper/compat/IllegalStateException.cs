using System;
using System.Collections.Generic;

namespace net.esper.compat
{
    /// <summary>
    /// An exception that occurs when some illegal state occurs.
    /// </summary>

	[Serializable]
	public class IllegalStateException : Exception
	{
        /// <summary>
        /// Initializes a new instance of the <see cref="IllegalStateException"/> class.
        /// </summary>
		public IllegalStateException() : base() { }
        /// <summary>
        /// Initializes a new instance of the <see cref="IllegalStateException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
		public IllegalStateException( string message ) : base( message ) { }
        /// <summary>
        /// Initializes a new instance of the <see cref="IllegalStateException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
        /// <param name="e">The underlying exception.</param>
        public IllegalStateException(string message, Exception e) : base(message, e) { }
    }
}
