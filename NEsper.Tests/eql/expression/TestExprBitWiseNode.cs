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

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprBitWiseNode {

		private ExprBitWiseNode _bitWiseNode;

		[SetUp]
		public void SetUp()
		{
			_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
		}

	    [Test]
	    public void TestValidate()
	    {
	        // Must have exactly 2 subnodes
	        try
	        {
	        	_bitWiseNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        	log.Debug("No nodes in the expression");
	        }

	        // Must have only number or bool-type subnodes
	        _bitWiseNode.AddChildNode(new SupportExprNode(typeof(String)));
	        _bitWiseNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        try
	        {
	        	_bitWiseNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void TestGetType()
	    {
	    	log.Debug(".testGetType");
	    	_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
	    	_bitWiseNode.AddChildNode(new SupportExprNode(typeof(double?)));
	    	_bitWiseNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        try
	        {
	        	_bitWiseNode.Validate(null, null, null);
	        	Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }
	        _bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
	        _bitWiseNode.AddChildNode(new SupportExprNode(typeof(long?)));
	        _bitWiseNode.AddChildNode(new SupportExprNode(typeof(long?)));
	    	_bitWiseNode.GetValidatedSubtree(null, null, null);
	        Assert.AreEqual(typeof(long?), _bitWiseNode.GetType());
	    }

	    [Test]
	    public void TestEvaluate()
	    {
	    	log.Debug(".testEvaluate");
	    	_bitWiseNode.AddChildNode(new SupportExprNode(10));
	    	_bitWiseNode.AddChildNode(new SupportExprNode(12));
	    	_bitWiseNode.GetValidatedSubtree(null, null, null);
	        Assert.AreEqual(8, _bitWiseNode.Evaluate(null, false));
	    }

	    [Test]
	    public void TestEqualsNode()
	    {
	    	log.Debug(".testEqualsNode");
	    	_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
	        Assert.IsTrue(_bitWiseNode.EqualsNode(_bitWiseNode));
	        Assert.IsFalse(_bitWiseNode.EqualsNode(new ExprBitWiseNode(BitWiseOpEnum.BXOR)));
	    }

	    [Test]
	    public void TestToExpressionString()
	    {
	    	log.Debug(".testToExpressionString");
	    	_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
	    	_bitWiseNode.AddChildNode(new SupportExprNode(4));
	    	_bitWiseNode.AddChildNode(new SupportExprNode(2));
	        Assert.AreEqual("(4&2)", _bitWiseNode.ExpressionString);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	}
} // End of namespace
