/// <summary>***********************************************************************************
/// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
/// http://esper.codehaus.org                                                          *
/// ---------------------------------------------------------------------------------- *
/// The software in this package is published under the terms of the GPL license       *
/// a copy of which has been included with this distribution in the license.txt file.  *
/// ************************************************************************************
/// </summary>
using System;

namespace net.esper.client
{
	/// <summary>
    /// Called to indicate an event emitted from an EPRuntime.
    /// </summary>
	
    public delegate void EmittedListener( Object _event ) ;
}