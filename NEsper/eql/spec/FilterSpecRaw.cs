///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.eql.expression;
using net.esper.util;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Filter definition in an un-validated and un-resolved form.
	/// <para>
	/// Event type and expression nodes in this filter specification are not yet validated, optimized for resolved
	/// against actual streams.
	/// </para>
	/// </summary>
	public class FilterSpecRaw : MetaDefItem
	{
	    private String eventTypeAlias;
	    private List<ExprNode> filterExpressions;

	    /// <summary>Ctor.</summary>
	    /// <param name="eventTypeAlias">is the name of the event type</param>
	    /// <param name="filterExpressions">
	    /// is a list of expression nodes representing individual filter expressions
	    /// </param>
	    public FilterSpecRaw(String eventTypeAlias, List<ExprNode> filterExpressions)
	    {
	        this.eventTypeAlias = eventTypeAlias;
	        this.filterExpressions = filterExpressions;
	    }

	    /// <summary>Default ctor.</summary>
	    public FilterSpecRaw()
	    {
	    }

	    /// <summary>Returns the event type alias of the events we are looking for.</summary>
	    /// <returns>event name</returns>
	    public String EventTypeAlias
	    {
            get { return eventTypeAlias; }
	    }

	    /// <summary>Returns the list of filter expressions.</summary>
	    /// <returns>filter expression list</returns>
	    public IList<ExprNode> FilterExpressions
	    {
            get { return filterExpressions; }
	    }
	}
} // End of namespace
