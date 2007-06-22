///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.type;

namespace net.esper.support.eql
{
	public class SupportOuterJoinDescFactory
	{
	    public static OuterJoinDesc MakeDesc(String propOne, String streamOne, String propTwo, String streamTwo, OuterJoinType type)
	    {
	        ExprIdentNode identNodeOne = new ExprIdentNode(propOne, streamOne);
	        ExprIdentNode identNodeTwo = new ExprIdentNode(propTwo, streamTwo);

	        identNodeOne.Validate(new SupportStreamTypeSvc3Stream(), null, null);
	        identNodeTwo.Validate(new SupportStreamTypeSvc3Stream(), null, null);
	        OuterJoinDesc desc = new OuterJoinDesc(type, identNodeOne, identNodeTwo);

	        return desc;
	    }
	}
} // End of namespace
