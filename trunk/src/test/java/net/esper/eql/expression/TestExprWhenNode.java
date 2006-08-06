package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNode;
import net.esper.type.RelationalOpEnum;
import net.esper.type.ArithTypeEnum;
import net.esper.type.MinMaxTypeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprWhenNode extends TestCase
{
    private ExprWhenNode _whenNode;

    public void setUp()
    {
           _whenNode = new ExprWhenNode();
    }

    public void testGetType()  throws Exception
    {
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(RelationalOpEnum.GT);
        opNode.addChildNode(new SupportExprNode(int.class));
        opNode.addChildNode(new SupportExprNode(Short.class));
        ExprCountNode countNode = new ExprCountNode(false);
        countNode.validate(null);
        _whenNode.addChildNode(opNode);
        _whenNode.addChildNode(countNode);
        _whenNode.validate(null);
        assertEquals(countNode.getType(), _whenNode.getType());
        _whenNode.getChildNodes().clear();
    }

    public void testValidate() throws Exception
    {
        ExprMinMaxRowNode minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
        _whenNode.addChildNode(minMaxNode);
        // Test too few nodes under this node
        try
        {
            _whenNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        ExprOrNode orNode = new ExprOrNode();
        _whenNode.addChildNode(orNode);
        // Mismatch order: first child node has to be a logical Expression
        try
        {
            _whenNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        _whenNode.getChildNodes().clear();

        // Permute two nodes: success, Or expression which is at same time a logical expression
        // is accepted for left node
        _whenNode.addChildNode(orNode);
        _whenNode.addChildNode(minMaxNode);
        _whenNode.validate(null);
        _whenNode.getChildNodes().clear();
    }

    public void testEvaluate() throws Exception
    {
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(RelationalOpEnum.LE);
        opNode = (ExprRelationalOpNode) makeNode(opNode,2.5, float.class, 2, Short.class);
        opNode.validate(null);
        assertEquals(false, opNode.evaluate(null));
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.ADD);
        arithNode.addChildNode(new SupportExprNode(new Integer(25)));
        arithNode.addChildNode(new SupportExprNode(new Double(130.5)));
        arithNode.validateDescendents(null);
        assertEquals(155.5d, arithNode.evaluate(null));
        _whenNode.addChildNode(opNode);
        _whenNode.addChildNode(arithNode);
        assertEquals(null, _whenNode.evaluate(null));
        // Inverse the comparison and we got the evaluation of the second expression
        opNode.getChildNodes().clear();
        _whenNode.getChildNodes().clear();
        opNode = new ExprRelationalOpNode(RelationalOpEnum.GT);
        opNode = (ExprRelationalOpNode) makeNode(opNode,2.5, float.class, 2, Short.class);
        opNode.validate(null);
        arithNode.addChildNode(new SupportExprNode(new Integer(25)));
        arithNode.addChildNode(new SupportExprNode(new Double(130.5)));
        _whenNode.addChildNode(opNode);
        _whenNode.addChildNode(arithNode);
        assertEquals(155.5d, _whenNode.evaluate(null));
    }

    public void testEquals()  throws Exception
    {
        ExprWhenNode[] whenNodes = new ExprWhenNode[2];
        whenNodes[0] = new ExprWhenNode();
        whenNodes[1] = new ExprWhenNode();
        ExprEqualsNode eqNode = new ExprEqualsNode(false);
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        whenNodes[0].addChildNode(eqNode);
        whenNodes[0].addChildNode(arithNode);
        eqNode = new ExprEqualsNode(false);
        whenNodes[1].addChildNode(eqNode);
        whenNodes[1].addChildNode(arithNode);
        assertTrue(whenNodes[0].equalsNode(whenNodes[1]));
        // Change the logical expression for the second ExprWhen Node
        // Test fails.
        eqNode = new ExprEqualsNode(true);
        whenNodes[1].getChildNodes().clear();
        whenNodes[1].addChildNode(eqNode);
        whenNodes[1].addChildNode(arithNode);
        assertFalse(whenNodes[0].equalsNode(whenNodes[1]));
        // Changing the operator of the second Node, test fails
        arithNode = new ExprMathNode(ArithTypeEnum.ADD);
        eqNode = new ExprEqualsNode(false);
        whenNodes[1].getChildNodes().clear();
        whenNodes[1].addChildNode(eqNode);
        whenNodes[1].addChildNode(arithNode);
        assertFalse(whenNodes[0].equalsNode(whenNodes[1]));
        
    }

    public void testToExpressionString() throws Exception
    {
        // Build sum(6*6)
        ExprSumNode sumNode = new ExprSumNode(false);
        ExprMathNode arithNodeChild = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        arithNodeChild = (ExprMathNode) makeNode(arithNodeChild,6,6);
        sumNode.addChildNode(arithNodeChild);
        ExprEqualsNode eqNode = new ExprEqualsNode(false);
        eqNode = (ExprEqualsNode) makeNode(eqNode, true, false);
        _whenNode.addChildNode(eqNode);
        _whenNode.addChildNode(sumNode);
        assertEquals(" when true = false then sum((6*6))", _whenNode.toExpressionString());
        _whenNode.getChildNodes().clear();
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

     private static final Log log = LogFactory.getLog(TestExprWhenNode.class);
}
