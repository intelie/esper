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

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.collection
{
	[TestFixture]
	public class TestSortedRefCountedSet
	{
	    private SortedRefCountedSet<String> refSet;
	    private Random random = new Random();

	    [SetUp]
	    public void SetUp()
	    {
	        refSet = new SortedRefCountedSet<String>();
	    }

	    [Test]
	    public void TestMaxMinValue()
	    {
	        refSet.Add("a");
	        refSet.Add("b");
	        Assert.AreEqual("ba", refSet.MaxValue + refSet.MinValue);
	        refSet.Remove("a");
	        Assert.AreEqual("bb", refSet.MaxValue + refSet.MinValue);
	        refSet.Remove("b");
	        Assert.IsNull(refSet.MaxValue);
	        Assert.IsNull(refSet.MinValue);

	        refSet.Add("b");
	        refSet.Add("a");
	        refSet.Add("d");
	        refSet.Add("a");
	        refSet.Add("c");
	        refSet.Add("a");
	        refSet.Add("c");
	        Assert.AreEqual("da", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("d");
	        Assert.AreEqual("ca", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("a");
	        Assert.AreEqual("ca", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("a");
	        Assert.AreEqual("ca", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("c");
	        Assert.AreEqual("ca", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("c");
	        Assert.AreEqual("ba", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("a");
	        Assert.AreEqual("bb", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("b");
	        Assert.IsNull(refSet.MaxValue);
	        Assert.IsNull(refSet.MinValue);
	    }

	    [Test]
	    public void TestAdd()
	    {
	        refSet.Add("a");
	        refSet.Add("b");
	        refSet.Add("a");
	        refSet.Add("c");
	        refSet.Add("a");

	        Assert.AreEqual("c", refSet.MaxValue);
	        Assert.AreEqual("a", refSet.MinValue);
	    }

	    [Test]
	    public void TestRemove()
	    {
	        refSet.Add("a");
	        refSet.Remove("a");
	        Assert.IsNull(refSet.MaxValue);
	        Assert.IsNull(refSet.MinValue);

	        refSet.Add("a");
	        refSet.Add("a");
	        Assert.AreEqual("aa", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("a");
	        Assert.AreEqual("aa", refSet.MaxValue + refSet.MinValue);

	        refSet.Remove("a");
	        Assert.IsNull(refSet.MaxValue);
	        Assert.IsNull(refSet.MinValue);

	        try
	        {
	            refSet.Remove("c");
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }

	        try
	        {
	            refSet.Add("a");
	            refSet.Remove("a");
	            refSet.Remove("a");
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
