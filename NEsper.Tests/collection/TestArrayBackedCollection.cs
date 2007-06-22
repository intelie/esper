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

namespace net.esper.collection
{
	[TestFixture]
	public class TestArrayBackedCollection
	{
	    private ArrayBackedCollection<int> coll;

	    [SetUp]
	    public void SetUp()
	    {
	        coll = new ArrayBackedCollection<int>(5);
	    }

	    [Test]
	    public void TestGet()
	    {
	        Assert.AreEqual(0, coll.Count);
	        Assert.AreEqual(5, coll.Array.Length);

	        coll.Add(5);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {5, null, null, null, null}, coll.Array);
	        coll.Add(4);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {5, 4, null, null, null}, coll.Array);
	        Assert.AreEqual(2, coll.Count);

	        coll.Add(1);
	        coll.Add(2);
	        coll.Add(3);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {5, 4, 1, 2, 3}, coll.Array);
	        Assert.AreEqual(5, coll.Count);

	        coll.Add(10);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {5, 4, 1, 2, 3, 10, null, null, null, null}, coll.Array);
	        Assert.AreEqual(6, coll.Count);

	        coll.Add(11);
	        coll.Add(12);
	        coll.Add(13);
	        coll.Add(14);
	        coll.Add(15);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {5, 4, 1, 2, 3, 10, 11, 12, 13, 14, 15,
	                null,null,null,null,null,null,null,null,null}, coll.Array);
	        Assert.AreEqual(11, coll.Count);

	        coll.Clear();
	        Assert.AreEqual(0, coll.Count);
	    }
	}
} // End of namespace
