///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.pattern
{
	/// <summary>
	/// This exception is thrown to indicate a problem with a view expression.
	/// </summary>
	public sealed class PatternObjectException : Exception
	{
	    /// <summary>Constructor.</summary>
	    /// <param name="message">is the error message</param>
	    public PatternObjectException(String message)
	        : base(message)
	    {
	    }

	    /// <summary>Constructor for an inner exception and message.</summary>
	    /// <param name="message">is the error message</param>
	    /// <param name="cause">is the inner exception</param>
	    public PatternObjectException(String message, Exception cause)
	        : base(message, cause)
	    {
	    }

	    /// <summary>Constructor.</summary>
	    /// <param name="cause">is the inner exception</param>
	    public PatternObjectException(Exception cause)
	        : base(cause)
	    {
	    }
	}
} // End of namespace
