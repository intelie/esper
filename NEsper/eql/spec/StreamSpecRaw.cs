///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.spec
{
	/// <summary>
	/// An uncompiled, unoptimize for of stream specification created by a parser.
	/// </summary>
	public interface StreamSpecRaw : StreamSpec
	{
	    /// <summary>
	    /// Compiles a raw stream specification consisting event type information and filter expressions
	    /// to an validated, optimized form for use with filter service
	    /// </summary>
	    /// <param name="eventAdapterService">supplies type information</param>
	    /// <param name="methodResolutionService">for resolving imports</param>
	    /// <returns>compiled stream</returns>
	    /// <throws>ExprValidationException to indicate validation errors</throws>
	    StreamSpecCompiled Compile(EventAdapterService eventAdapterService,
	                                      MethodResolutionService methodResolutionService);

	}
}
