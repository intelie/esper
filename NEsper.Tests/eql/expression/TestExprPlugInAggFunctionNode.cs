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
using net.esper.type;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprPlugInAggFunctionNode
	{
	    private ExprPlugInAggFunctionNode plugInNode;

	    [SetUp]
	    public void SetUp()
	    {
	        plugInNode = new ExprPlugInAggFunctionNode(false, new SupportPluginAggregationMethodOne(), "matrix");
	    }

	    [Test]
	    public void testGetType()
	    {
	        plugInNode.Validate(null, null, null);
	        Assert.AreEqual(typeof(int?), plugInNode.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // fails with too many sub-expressions
	        plugInNode.AddChildNode(new SupportExprNode(typeof(bool?)));
	        plugInNode.AddChildNode(new SupportExprNode(typeof(bool?)));
	        try
	        {
	            plugInNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        ExprPlugInAggFunctionNode otherOne = new ExprPlugInAggFunctionNode(false, new SupportPluginAggregationMethodOne(), "matrix");
	        ExprPlugInAggFunctionNode otherTwo = new ExprPlugInAggFunctionNode(false, new SupportPluginAggregationMethodOne(), "matrix2");

	        Assert.IsTrue(plugInNode.EqualsNode(plugInNode));
	        Assert.IsFalse(plugInNode.EqualsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
	        Assert.IsTrue(otherOne.EqualsNode(plugInNode));
	        Assert.IsFalse(otherTwo.EqualsNode(plugInNode));
	    }
	}
} // End of namespace
