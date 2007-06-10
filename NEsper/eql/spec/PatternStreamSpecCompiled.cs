///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Specification for building an event stream out of a pattern statement and views staggered onto the
	/// pattern statement.
	/// <p>
	/// The pattern statement is represented by the top EvalNode evaluation node.
	/// A pattern statement contains tagged events (i.e. a=A -&gt; b=B).
	/// Thus the resulting event type is has properties "a" and "b" of the type of A and B.
	/// </summary>
	public class PatternStreamSpecCompiled : StreamSpecBase, StreamSpecCompiled
	{
	    private readonly EvalNode evalNode;
	    private readonly EDictionary<String, EventType> taggedEventTypes;       // Stores types for filters with tags

	    /// <summary>Ctor.</summary>
	    /// <param name="evalNode">pattern evaluation node representing pattern statement</param>
	    /// <param name="viewSpecs">specifies what view to use to derive data</param>
	    /// <param name="taggedEventTypes">
	    /// event tags and their types as specified in the pattern, copied to allow original collection to change
	    /// </param>
	    /// <param name="optionalStreamName">stream name, or null if none supplied</param>
	    public PatternStreamSpecCompiled(EvalNode evalNode, IDictionary<String, EventType> taggedEventTypes, List<ViewSpec> viewSpecs, String optionalStreamName)
	        : base(optionalStreamName, viewSpecs)
	    {
	        this.evalNode = evalNode;

	        EDictionary<String, EventType> copy = new EHashDictionary<String, EventType>();
	        copy.PutAll(taggedEventTypes);
	        this.taggedEventTypes = copy;
	    }

	    /// <summary>
	    /// Returns the pattern expression evaluation node for the top pattern operator.
	    /// </summary>
	    /// <returns>parent pattern expression node</returns>
	    public EvalNode EvalNode
	    {
	    	get { return evalNode; }
	    }

	    /// <summary>Returns event types tagged in the pattern expression.</summary>
	    /// <returns>map of tag and event type tagged in pattern expression</returns>
	    public EDictionary<String, EventType> TaggedEventTypes
	    {
	    	get { return taggedEventTypes; }
	    }
	}
} // End of namespace
