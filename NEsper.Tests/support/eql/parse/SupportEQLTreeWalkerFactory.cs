// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using net.esper.eql.core;
using net.esper.eql.parse;
using net.esper.pattern;

namespace net.esper.support.eql.parse
{
	public class SupportEQLTreeWalkerFactory
	{
	    public static EQLTreeWalker MakeWalker(EngineImportService engineImportService)
	    {
	        return new EQLTreeWalker(engineImportService, new PatternObjectResolutionServiceImpl(null));
	    }

	    public static EQLTreeWalker MakeWalker()
	    {
	        return MakeWalker(new EngineImportServiceImpl());
	    }
	}
} // End of namespace
