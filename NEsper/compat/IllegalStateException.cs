using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	public class IllegalStateException : SystemException
	{
		public IllegalStateException() : base() { }
		public IllegalStateException( string message ) : base( message ) { }
	}
}
