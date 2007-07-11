// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.support.util;

namespace net.esper.pattern
{
	[TestFixture]
	public class TestEvalNodeNumber
	{
	    [Test]
	    public void testFlow()
	    {
	        EvalNodeNumber parent = new EvalNodeNumber();

	        EvalNodeNumber child1 = parent.NewChildNumber();
	        ArrayAssertionUtil.AreEqualExactOrder(child1.Number, new short[] {0});

	        EvalNodeNumber child1_1 = child1.NewChildNumber();
	        ArrayAssertionUtil.AreEqualExactOrder(child1_1.Number, new short[] {0, 0});

	        EvalNodeNumber child1_2 = child1_1.NewSiblingNumber();
	        ArrayAssertionUtil.AreEqualExactOrder(child1_2.Number, new short[] {0, 1});

	        EvalNodeNumber child1_2_1 = child1_2.NewChildNumber();
	        ArrayAssertionUtil.AreEqualExactOrder(child1_2_1.Number, new short[] {0, 1, 0});

	        EvalNodeNumber child1_2_2 = child1_2_1.NewSiblingNumber();
	        ArrayAssertionUtil.AreEqualExactOrder(child1_2_2.Number, new short[] {0, 1, 1});
	        ArrayAssertionUtil.AreEqualExactOrder(child1_2_2.ParentNumber.Number, new short[] {0, 1});
	    }

	    [Test]
	    public void testHashCode()
	    {

	    }
	}
} // End of namespace
