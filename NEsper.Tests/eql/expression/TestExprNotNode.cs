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
using net.esper.type;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprNotNode
	{
	    private ExprNotNode notNode;

	    [SetUp]
	    public void SetUp()
	    {
	        notNode = new ExprNotNode();
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(bool?), notNode.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // fails with zero expressions
	        try
	        {
	            notNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // fails with too many sub-expressions
	        notNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        notNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        try
	        {
	            notNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // test failure, type mismatch
	        notNode = new ExprNotNode();
	        notNode.AddChildNode(new SupportExprNode(typeof(String)));
	        try
	        {
	            notNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // validates
	        notNode = new ExprNotNode();
	        notNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        notNode.Validate(null, null, null);
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        notNode.AddChildNode(new SupportBoolExprNode(true));
	        Assert.IsFalse( (Boolean) notNode.Evaluate(null, false));

	        notNode = new ExprNotNode();
	        notNode.AddChildNode(new SupportBoolExprNode(false));
	        Assert.IsTrue( (Boolean) notNode.Evaluate(null, false));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        notNode.AddChildNode(new SupportExprNode(true));
	        Assert.AreEqual("NOT(True)", notNode.ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(notNode.EqualsNode(notNode));
	        Assert.IsFalse(notNode.EqualsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
	        Assert.IsFalse(notNode.EqualsNode(new ExprOrNode()));
	        Assert.IsTrue(notNode.EqualsNode(new ExprNotNode()));
	    }
	}
} // End of namespace
