using System;

namespace net.esper.compat
{
	[Serializable]
	public class UnsupportedOperationException : NotSupportedException
	{
        /// <summary>
        /// Initializes a new instance of the <see cref="UnsupportedOperationException"/> class.
        /// </summary>
		public UnsupportedOperationException() : base() { }
        /// <summary>
        /// Initializes a new instance of the <see cref="UnsupportedOperationException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
		public UnsupportedOperationException( string message ) : base( message ) { }	}
}
