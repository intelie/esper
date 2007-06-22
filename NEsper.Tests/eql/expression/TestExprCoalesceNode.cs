///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprCoalesceNode
	{
	    private ExprCoalesceNode[] coalesceNodes;

	    [SetUp]
	    public void SetUp()
	    {
	        coalesceNodes = new ExprCoalesceNode[5];

	        coalesceNodes[0] = new ExprCoalesceNode();
	        coalesceNodes[0].AddChildNode(new SupportExprNode(null, typeof(long?)));
	        coalesceNodes[0].AddChildNode(new SupportExprNode(null, typeof(int)));
	        coalesceNodes[0].AddChildNode(new SupportExprNode(4, typeof(byte)));

	        coalesceNodes[1] = new ExprCoalesceNode();
	        coalesceNodes[1].AddChildNode(new SupportExprNode(null, typeof(String)));
	        coalesceNodes[1].AddChildNode(new SupportExprNode("a", typeof(String)));

	        coalesceNodes[2] = new ExprCoalesceNode();
	        coalesceNodes[2].AddChildNode(new SupportExprNode(null, typeof(Boolean)));
	        coalesceNodes[2].AddChildNode(new SupportExprNode(true, typeof(bool)));

	        coalesceNodes[3] = new ExprCoalesceNode();
	        coalesceNodes[3].AddChildNode(new SupportExprNode(null, typeof(char)));
	        coalesceNodes[3].AddChildNode(new SupportExprNode(null, typeof(char?)));
	        coalesceNodes[3].AddChildNode(new SupportExprNode(null, typeof(char)));
	        coalesceNodes[3].AddChildNode(new SupportExprNode('b', typeof(char?)));

	        coalesceNodes[4] = new ExprCoalesceNode();
	        coalesceNodes[4].AddChildNode(new SupportExprNode(5, typeof(float)));
	        coalesceNodes[4].AddChildNode(new SupportExprNode(null, typeof(double?)));
	    }

	    [Test]
	    public void TestGetType()
	    {
	        for (int i = 0; i < coalesceNodes.Length; i++)
	        {
	            coalesceNodes[i].Validate(null, null, null);
	        }

	        Assert.AreEqual(typeof(long?), coalesceNodes[0].GetType());
	        Assert.AreEqual(typeof(string), coalesceNodes[1].GetType());
	        Assert.AreEqual(typeof(bool?), coalesceNodes[2].GetType());
	        Assert.AreEqual(typeof(char?), coalesceNodes[3].GetType());
	        Assert.AreEqual(typeof(double?), coalesceNodes[4].GetType());
	    }

	    [Test]
	    public void TestValidate()
	    {
	        ExprCoalesceNode coalesceNode = new ExprCoalesceNode();
	        coalesceNode.AddChildNode(new SupportExprNode(1));

	        // Test too few nodes under this node
	        try
	        {
	            coalesceNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // Test node result type not fitting
	        coalesceNode.AddChildNode(new SupportExprNode("s"));
	        try
	        {
	            coalesceNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void TestEvaluate()
	    {
	        for (int i = 0; i < coalesceNodes.Length; i++)
	        {
	            coalesceNodes[i].Validate(null, null, null);
	        }

	        Assert.AreEqual(4L, coalesceNodes[0].Evaluate(null, false));
	        Assert.AreEqual("a", coalesceNodes[1].Evaluate(null, false));
	        Assert.AreEqual(true, coalesceNodes[2].Evaluate(null, false));
	        Assert.AreEqual('b', coalesceNodes[3].Evaluate(null, false));
	        Assert.AreEqual(5D, coalesceNodes[4].Evaluate(null, false));
	    }

	    [Test]
	    public void TestEquals()
	    {
	        Assert.IsFalse(coalesceNodes[0].EqualsNode(new ExprEqualsNode(true)));
	        Assert.IsTrue(coalesceNodes[0].EqualsNode(coalesceNodes[1]));
	    }

	    [Test]
	    public void TestToExpressionString()
	    {
	        //assertEquals("coalesce(null, null, ", coalesceNodes[0].ExpressionString);
	    }
	}
} // End of namespace
