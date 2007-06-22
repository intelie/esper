// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.client;

namespace net.esper.support.emit
{
	public class SupportMTEmittedListener
	{
	    private List<Object> emittedObjects = new List<Object>();

	    public void Emitted(Object _object)
	    {
	        emittedObjects.Add(_object);
	    }

	    public Object[] GetEmittedObjects()
	    {
	        return emittedObjects.ToArray();
	    }

	    public void Reset()
	    {
	        emittedObjects.Clear();
	    }
	}
} // End of namespace
