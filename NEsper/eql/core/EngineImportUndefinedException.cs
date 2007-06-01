///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.eql.core
{

	/// <summary>
	/// Indicates a problem importing classes, aggregation functions and the like.
	/// </summary>
	public class EngineImportUndefinedException : Exception
	{
	    /// <summary>Ctor.</summary>
	    /// <param name="msg">exception message</param>
	    public EngineImportUndefinedException(String msg)
			: base(msg)
	    {
	    }

	    /// <summary>Ctor.</summary>
	    /// <param name="msg">exception message</param>
	    /// <param name="ex">inner exception</param>
	    public EngineImportUndefinedException(String msg, Exception ex)
			: base(msg, ex)
	    {
	    }
	}
} // End of namespace
