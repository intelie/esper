///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.eql.join.plan;

namespace net.esper.eql.join.plan
{
	[TestFixture]
	public class TestQueryPlanIndex
	{
	    private QueryPlanIndex indexSpec;

	    [SetUp]
	    public void SetUp()
	    {
	        String[][] indexes = new String[][] {
	            new string[]{ "p01", "p02"},
	            new string[]{ "p21" },
	            new string[0],
	        };

            indexSpec = new QueryPlanIndex(indexes, new Type[indexes.Length][]);
	    }

	    [Test]
	    public void TestInvalidUse()
	    {
	        try
	        {
	            new QueryPlanIndex(null, null);
	            Assert.Fail();
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void TestGetIndexNum()
	    {
	        Assert.AreEqual(0, indexSpec.GetIndexNum(new String[] { "p01", "p02"}));
	        Assert.AreEqual(1, indexSpec.GetIndexNum(new String[] {"p21"}));
	        Assert.AreEqual(2, indexSpec.GetIndexNum(new String[0]));

	        Assert.AreEqual(-1, indexSpec.GetIndexNum(new String[] { "YY", "XX"}));
	    }

	    [Test]
	    public void TestAddIndex()
	    {
	        int indexNum = indexSpec.AddIndex(new String[] {"a", "b"}, null);
	        Assert.AreEqual(3, indexNum);
	        Assert.AreEqual(3, indexSpec.GetIndexNum(new String[] { "a", "b"}));
	    }
	}
} // End of namespace
