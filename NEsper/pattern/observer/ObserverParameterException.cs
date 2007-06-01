///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.pattern.observer
{
	/// <summary>Thrown to indicate a validation error in guard parameterization.</summary>
	public class ObserverParameterException : Exception
	{
	    /// <summary>Ctor.</summary>
	    /// <param name="message">validation error message</param>
	    public ObserverParameterException(String message)
	        : base(message)
	    {
	    }
	}
} // End of namespace
