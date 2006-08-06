package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.type.RelationalOpEnum;
import net.esper.type.MinMaxTypeEnum;
import net.esper.type.ArithTypeEnum;
import net.esper.support.eql.SupportExprNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprElseNode extends TestCase
{
    private ExprElseNode _elseNode;

    public void setUp()
    {
           _elseNode = new ExprElseNode();
    }

    public void testGetType()  throws Exception
    {
        ExprCountNode countNode = new ExprCountNode(false);
        countNode.validate(null);
        _elseNode.addChildNode(countNode);
        assertEquals(countNode.getType(), _elseNode.getType());
        _elseNode.getChildNodes().clear();
    }

    public void testValidate() throws Exception
    {
        // Test too few nodes under this node
        try
        {
            _elseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        SupportExprNode testNode = new SupportExprNode(Integer.class);
        _elseNode.addChildNode(testNode);
        // test successful, only one child value Expression is allowed
        _elseNode.validate(null);
        SupportExprNode testNode2 = new SupportExprNode(Integer.class);
        _elseNode.addChildNode(testNode2);
        // More than one node causes an exception
        try
        {
            _elseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        _elseNode.getChildNodes().clear();
    }

    public void testEvaluate() throws Exception
    {
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.ADD);
        arithNode = (ExprMathNode) makeNode(arithNode, new Integer(2), new Double(3.0));
        arithNode.validateDescendents(null);
        assertEquals(5.0d, arithNode.evaluate(null));
        _elseNode.addChildNode(arithNode);
        // Evaluation of else Node is the result from its expression node
        assertEquals(arithNode.evaluate(null), _elseNode.evaluate(null));
        _elseNode.getChildNodes().clear();
    }

    public void testEquals()  throws Exception
    {
        ExprElseNode[]  elseNodes = new ExprElseNode[2];
        elseNodes[0] = new ExprElseNode();
        elseNodes[1] = new ExprElseNode();
        ExprMinMaxRowNode minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MIN);
        ExprEqualsNode eqNode = new ExprEqualsNode(false);
        elseNodes[0].addChildNode(minMaxNode);
        elseNodes[1].addChildNode(eqNode);
        assertFalse(elseNodes[0].equalsNode(elseNodes[1]));
        // Make the two subNodes identical: test succeeds.
        elseNodes[0].getChildNodes().clear();
        elseNodes[0].addChildNode(eqNode);
        assertTrue(elseNodes[0].equalsNode(elseNodes[1]));
    }

    public void testToExpressionString() throws Exception
    {
        // Build sum(2*6)
        ExprSumNode sumNode = new ExprSumNode(false);
        ExprMathNode arithNodeChild = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        arithNodeChild = (ExprMathNode) makeNode(arithNodeChild,2,6);
        sumNode.addChildNode(arithNodeChild);
        _elseNode.addChildNode(sumNode);
        assertEquals(" else sum((2*6))", _elseNode.toExpressionString());
        _elseNode.getChildNodes().clear();
    }

    private ExprNode makeNode(ExprNode node_, Object typeLeft_, Object typeRight_)
    {
        node_.addChildNode(new SupportExprNode(typeLeft_));
        node_.addChildNode(new SupportExprNode(typeRight_));
        return node_;
    }

    private ExprNode makeNode(ExprNode node_, Object valueLeft_, Class typeLeft_, Object valueRight_, Class typeRight_)
    {
        node_.addChildNode(new SupportExprNode(valueLeft_, typeLeft_));
        node_.addChildNode(new SupportExprNode(valueRight_, typeRight_));
        return node_;
    }

     private static final Log log = LogFactory.getLog(TestExprElseNode.class);
}
