// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprArrayNode
	{
	    private ExprArrayNode[] arrayNodes;

	    [SetUp]
	    public void SetUp()
	    {
	        arrayNodes = new ExprArrayNode[4];
	        arrayNodes[0] = new ExprArrayNode();

	        // no coercion array
	        arrayNodes[1] = new ExprArrayNode();
	        arrayNodes[1].AddChildNode(new SupportExprNode(2));
	        arrayNodes[1].AddChildNode(new SupportExprNode(3));

	        // coercion
	        arrayNodes[2] = new ExprArrayNode();
	        arrayNodes[2].AddChildNode(new SupportExprNode(1.5D));
	        arrayNodes[2].AddChildNode(new SupportExprNode(1));

	        // mixed types
	        arrayNodes[3] = new ExprArrayNode();
	        arrayNodes[3].AddChildNode(new SupportExprNode("a"));
	        arrayNodes[3].AddChildNode(new SupportExprNode(1));

	        for (int i = 0; i < arrayNodes.Length; i++)
	        {
	            arrayNodes[i].Validate(null, null, null);
	        }
	    }

	    [Test]
	    public void testGetType()
	    {
            Assert.AreEqual(typeof(object[]), arrayNodes[0].ReturnType);
            Assert.AreEqual(typeof(int?[]), arrayNodes[1].ReturnType);
	    	Assert.AreEqual(typeof(double?[]), arrayNodes[2].ReturnType);
            Assert.AreEqual(typeof(object[]), arrayNodes[3].ReturnType);
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        Object result = arrayNodes[0].Evaluate(null, true);
	        Assert.AreEqual(typeof(object[]), result.GetType());
	        Assert.AreEqual(0, ((Object[]) result).Length);

	        result = arrayNodes[1].Evaluate(null, true);
	        Assert.AreEqual(typeof(int?[]), result.GetType());
	        Assert.AreEqual(2, ((int?[]) result).Length);
	        Assert.AreEqual(2, ((int?[]) result)[0].Value);
	        Assert.AreEqual(3, ((int?[]) result)[1].Value);

	        result = arrayNodes[2].Evaluate(null, true);
	        Assert.AreEqual(typeof(double?[]), result.GetType());
	        Assert.AreEqual(2, ((double?[]) result).Length);
	        Assert.AreEqual(1.5, ((double?[]) result)[0].Value);
	        Assert.AreEqual(1.0, ((double?[]) result)[1].Value);

	        result = arrayNodes[3].Evaluate(null, true);
	        Assert.AreEqual(typeof(object[]), result.GetType());
	        Assert.AreEqual(2, ((object[]) result).Length);
	        Assert.AreEqual("a", ((object[]) result)[0]);
	        Assert.AreEqual(1, ((object[]) result)[1]);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("{}", arrayNodes[0].ExpressionString);
	        Assert.AreEqual("{2,3}", arrayNodes[1].ExpressionString);
	        Assert.AreEqual("{1.5,1}", arrayNodes[2].ExpressionString);
	        Assert.AreEqual("{\"a\",1}", arrayNodes[3].ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(arrayNodes[0].EqualsNode(arrayNodes[1]));
	        Assert.IsFalse(arrayNodes[0].EqualsNode(new SupportExprNode(null)));
	    }
	}
} // End of namespace
