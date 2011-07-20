/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.expression;

import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.type.BitWiseOpEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestExprBitWiseNode extends TestCase {

	private ExprBitWiseNode _bitWiseNode;

	public void setUp()
	{
		_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
	}

    public void testValidate()
    {
        // Must have exactly 2 subnodes
        try
        {
        	_bitWiseNode.validate(ExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        	log.debug("No nodes in the expression");
        }

        // Must have only number or boolean-type subnodes
        _bitWiseNode.addChildNode(new SupportExprNode(String.class));
        _bitWiseNode.addChildNode(new SupportExprNode(Integer.class));
        try
        {
        	_bitWiseNode.validate(ExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testGetType() throws Exception
    {
    	log.debug(".testGetType");
    	_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
    	_bitWiseNode.addChildNode(new SupportExprNode(Double.class));
    	_bitWiseNode.addChildNode(new SupportExprNode(Integer.class));
        try
        {
        	_bitWiseNode.validate(ExprValidationContextFactory.makeEmpty());
        	fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        _bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
        _bitWiseNode.addChildNode(new SupportExprNode(Long.class));
        _bitWiseNode.addChildNode(new SupportExprNode(Long.class));
    	ExprNodeUtility.getValidatedSubtree(_bitWiseNode, ExprValidationContextFactory.makeEmpty());
        assertEquals(Long.class, _bitWiseNode.getType());
    }

    public void testEvaluate() throws Exception
    {
    	log.debug(".testEvaluate");
    	_bitWiseNode.addChildNode(new SupportExprNode(new Integer(10)));
    	_bitWiseNode.addChildNode(new SupportExprNode(new Integer(12)));
    	ExprNodeUtility.getValidatedSubtree(_bitWiseNode, ExprValidationContextFactory.makeEmpty());
        assertEquals(8, _bitWiseNode.evaluate(null, false, null));
    }

    public void testEqualsNode() throws Exception
    {
    	log.debug(".testEqualsNode");
    	_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
        assertTrue(_bitWiseNode.equalsNode(_bitWiseNode));
        assertFalse(_bitWiseNode.equalsNode(new ExprBitWiseNode(BitWiseOpEnum.BXOR)));
    }

    public void testToExpressionString() throws Exception
    {
    	log.debug(".testToExpressionString");
    	_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
    	_bitWiseNode.addChildNode(new SupportExprNode(4));
    	_bitWiseNode.addChildNode(new SupportExprNode(2));
        assertEquals("(4&2)", _bitWiseNode.toExpressionString());
    }

    static Log log = LogFactory.getLog(TestExprBitWiseNode.class);

}
