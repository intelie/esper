using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	public class IllegalStateException : Exception
	{
		public IllegalStateException() : base() { }
		public IllegalStateException( string message ) : base( message ) { }
	}
}
