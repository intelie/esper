///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.core;
using net.esper.eql.core;
using net.esper.eql.join;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.view;
using org.apache.commons.logging;

namespace net.esper.eql.view
{
	/// <summary>Factory for output processing views.<p></summary>
	public class OutputProcessViewFactory
	{
	    /// <summary>
	    /// Creates an output processor view depending on the presence of output limiting requirements.
	    /// </summary>
	    /// <param name="resultSetProcessor">
	    /// is the processing for select-clause and grouping
	    /// </param>
	    /// <param name="streamCount">is the number of streams</param>
	    /// <param name="outputLimitSpec">is the output rate limiting requirements</param>
	    /// <param name="statementContext">is the statement-level services</param>
	    /// <returns>output processing view</returns>
	    public static OutputProcessView MakeView(
							  ResultSetProcessor resultSetProcessor,
	    					  int streamCount,
	    					  OutputLimitSpec outputLimitSpec,
	    					  StatementContext statementContext)
	    {
	        // Do we need to enforce an output policy?
	        if (outputLimitSpec != null)
	        {
	            return new OutputProcessViewPolicy(resultSetProcessor, streamCount, outputLimitSpec, statementContext);
	        }
	        return new OutputProcessViewDirect(resultSetProcessor);
	    }
	}
}
