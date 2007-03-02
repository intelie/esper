using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	[Serializable]
	public class IllegalStateException : SystemException
	{
		public IllegalStateException() : base() { }
		public IllegalStateException( string message ) : base( message ) { }
	}
}
