// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.eql.core;

namespace net.esper.support.eql
{
	public class SupportViewResourceCallback : ViewResourceCallback
	{
	    private IList<Object> resources = new List<Object>();

	    public IList<Object> Resources
	    {
	    	get { return resources; }
	    }
		
		public object ViewResource {
			set {
	    		resources.Add(value);
			}
		}
	}
} // End of namespace
