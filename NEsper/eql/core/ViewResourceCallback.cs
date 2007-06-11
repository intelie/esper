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
	/// <summary>Callback for use by expression nodes to receive view resources.</summary>
	public interface ViewResourceCallback
	{
	    /// <summary>Supplies view resource.</summary>
	    Object ViewResource { set; }
	}
} // End of namespace
