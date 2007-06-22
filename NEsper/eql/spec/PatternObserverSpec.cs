///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Specification for a pattern observer object consists of a namespace, name and object parameters.
	/// </summary>
	public sealed class PatternObserverSpec : ObjectSpec
	{
	    /// <summary>Constructor.</summary>
	    /// <param name="_namespace">if the namespace the object is in</param>
	    /// <param name="objectName">is the name of the object</param>
	    /// <param name="objectParameters">
	    /// is a list of values representing the object parameters
	    /// </param>
	    public PatternObserverSpec(String _namespace, String objectName, IList<Object> objectParameters)
	        : base(_namespace, objectName, objectParameters)
	    {
	    }
	}
} // End of namespace
