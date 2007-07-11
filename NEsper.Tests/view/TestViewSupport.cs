///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.support.view;

namespace net.esper.view
{
	[TestFixture]
	public class TestViewSupport
	{
	    private SupportSchemaNeutralView top;

	    private SupportSchemaNeutralView child_1;
	    private SupportSchemaNeutralView child_2;

	    private SupportSchemaNeutralView child_2_1;
	    private SupportSchemaNeutralView child_2_2;

	    private SupportSchemaNeutralView child_2_1_1;
	    private SupportSchemaNeutralView child_2_2_1;
	    private SupportSchemaNeutralView child_2_2_2;

	    [SetUp]
	    public void SetUp()
	    {
	        top = new SupportSchemaNeutralView("top");

	        child_1 = new SupportSchemaNeutralView("1");
	        child_2 = new SupportSchemaNeutralView("2");
	        top.AddView(child_1);
	        top.AddView(child_2);

	        child_2_1 = new SupportSchemaNeutralView("2_1");
	        child_2_2 = new SupportSchemaNeutralView("2_2");
	        child_2.AddView(child_2_1);
	        child_2.AddView(child_2_2);

	        child_2_1_1 = new SupportSchemaNeutralView("2_1_1");
	        child_2_2_1 = new SupportSchemaNeutralView("2_2_1");
	        child_2_2_2 = new SupportSchemaNeutralView("2_2_2");
	        child_2_1.AddView(child_2_1_1);
	        child_2_2.AddView(child_2_2_1);
	        child_2_2.AddView(child_2_2_2);
	    }

	    [Test]
	    public void testFindDescendent()
	    {
	        // Test a deep find
            IList<View> descendents = ViewSupport.FindDescendent(top, child_2_2_1);
	        Assert.AreEqual(2, descendents.Count);
	        Assert.AreEqual(child_2, descendents[0]);
	        Assert.AreEqual(child_2_2, descendents[1]);

	        descendents = ViewSupport.FindDescendent(top, child_2_1_1);
	        Assert.AreEqual(2, descendents.Count);
	        Assert.AreEqual(child_2, descendents[0]);
	        Assert.AreEqual(child_2_1, descendents[1]);

	        descendents = ViewSupport.FindDescendent(top, child_2_1);
	        Assert.AreEqual(1, descendents.Count);
	        Assert.AreEqual(child_2, descendents[0]);

	        // Test a shallow find
	        descendents = ViewSupport.FindDescendent(top, child_2);
	        Assert.AreEqual(0, descendents.Count);

	        // Test a no find
	        descendents = ViewSupport.FindDescendent(top, new SupportSchemaNeutralView());
	        Assert.AreEqual(null, descendents);
	    }


	}
} // End of namespace
