///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

namespace net.esper.filter
{
	[TestFixture]
	public class TestDoubleRange
	{
	    [Test]
	    public void testNew()
	    {
	        DoubleRange range = new DoubleRange(10d, 20d);
	        Assert.AreEqual(20d, range.Max);
	        Assert.AreEqual(10d, range.Min);

	        range = new DoubleRange(20d, 10d);
	        Assert.AreEqual(20d, range.Max);
	        Assert.AreEqual(10d, range.Min);
	    }

	    [Test]
	    public void testEquals()
	    {
	        DoubleRange rangeOne = new DoubleRange(10d, 20d);
	        DoubleRange rangeTwo = new DoubleRange(20d, 10d);
	        DoubleRange rangeThree = new DoubleRange(20d, 11d);
	        DoubleRange rangeFour = new DoubleRange(21d, 10d);

	        Assert.AreEqual(rangeOne, rangeTwo);
	        Assert.AreEqual(rangeTwo, rangeOne);
	        Assert.IsFalse(rangeOne.Equals(rangeThree));
	        Assert.IsFalse(rangeOne.Equals(rangeFour));
	        Assert.IsFalse(rangeThree.Equals(rangeFour));
	    }

	    [Test]
	    public void testHash()
	    {
	        DoubleRange range = new DoubleRange(10d, 20d);
	        double valueA = 10.0;
	        double valueB = 20.0;

	        int hashCode = 7 + valueA.GetHashCode() ^ valueB.GetHashCode();

	        Assert.AreEqual(hashCode, range.GetHashCode());
	    }
	}
} // End of namespace
