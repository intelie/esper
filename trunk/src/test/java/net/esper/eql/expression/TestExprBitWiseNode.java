package net.esper.eql.expression;

import net.esper.support.eql.SupportExprNode;
import net.esper.type.BitWiseOpEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestExprBitWiseNode extends TestCase {

	private ExprBitWiseNode _bitWiseNode;

	public TestExprBitWiseNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setUp()
	{
		_bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
	}

    public void testValidate()
    {
        // Must have exactly 2 subnodes
        try
        {
        	_bitWiseNode.validate(null, null);
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
        	_bitWiseNode.validate(null, null);
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
        	_bitWiseNode.validate(null, null);
        	fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        _bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
        _bitWiseNode.addChildNode(new SupportExprNode(Long.class));
        _bitWiseNode.addChildNode(new SupportExprNode(Long.class));
    	_bitWiseNode.getValidatedSubtree(null, null);
        assertEquals(Long.class, _bitWiseNode.getType());
    }

    public void testEvaluate() throws Exception
    {
    	log.debug(".testEvaluate");
    	_bitWiseNode.addChildNode(new SupportExprNode(new Integer(10)));
    	_bitWiseNode.addChildNode(new SupportExprNode(new Integer(12)));
    	_bitWiseNode.getValidatedSubtree(null, null);
        assertEquals(8, _bitWiseNode.evaluate(null));
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
