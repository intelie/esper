package net.esper.eql.expression;

import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;

import net.esper.type.RelationalOpEnum;
import net.esper.type.ArithTypeEnum;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.eql.SupportAggregationResultFuture;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprCaseNode extends TestCase
{
    private ExprCaseNode _caseNode;
    public void setUp()
    {
           //_caseNode = new ExprCaseNode(false);
    }

    public void testGetType()  throws Exception
    {
        // Case Node type is based on the context of the different when node expressions
        // First when node true, the node type of the case node is the type of the value expression
        // for this when node.

        // Template expression is:
        // case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        // Build case node, logical nodes don't return any values:
        // case Node type is null
        _caseNode=buildCaseNode(false,0);
        assertEquals(null, _caseNode.getType());
        // first when is true, case node type is the first when node type
        _caseNode.clearExprNodeList();
        _caseNode=buildCaseNode(true, 1);
        ExprWhenNode whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(0));
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Second when is true, case node type is the type of the second when node
        _caseNode=buildCaseNode(true, 2);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(1));
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Third test: we enable both when nodes: case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) end
        _caseNode=buildCaseNode(true, 3);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(0));
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Enabling the whole expression: case when (2.5>1) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        // the case node type is still the first value node expression type
        _caseNode=buildCaseNode(true, 7);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(0));
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Building expression:  case when (Float>Short) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 6);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(1));
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Last test: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 4);
        ExprElseNode elseNode = (ExprElseNode) (_caseNode.getExprNodeList(2));
        assertEquals(elseNode.getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
    }

    public void testValidate() throws Exception
    {
        // No subnodes: Exception is thrown.
        try
        {
            _caseNode = new ExprCaseNode(false, null);
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        try
        {
            _caseNode = new ExprCaseNode(false, new ArrayList<ExprNode>());
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        // One sub-node but it's not a When node or Else mode
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.DIVIDE);
        arithNode.addChildNode(new SupportExprNode(new Integer(4)));
        arithNode.addChildNode(new SupportExprNode(new Integer(2)));
        arithNode.validateDescendents(null);
        try
        {
            List<ExprNode> listExprNodes = new ArrayList<ExprNode>();
            listExprNodes.add(arithNode);
            _caseNode = new ExprCaseNode(false, listExprNodes);
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        // Else node is not enough, it has to be paired with at least one when node.
        ExprElseNode elseNode = new ExprElseNode();
        elseNode.addChildNode(arithNode);
        try
        {
            List<ExprNode> listExprNodes = new ArrayList<ExprNode>();
            listExprNodes.add(elseNode);
            _caseNode = new ExprCaseNode(false, listExprNodes);
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        // expression: case when (2.5>1) then count(5) when (3>2) then (25 + 130.5) end
        // The validation should be successful for every node traversed.
        _caseNode=buildCaseNode(false, 7);
        _caseNode.validate(null);
        _caseNode.clearExprNodeList();
        _caseNode=buildCaseNode(true, 7);
        _caseNode.validate(null);
        _caseNode.clearExprNodeList();
    }

    public void testEvaluate() throws Exception
    {
        // The value of the case node is contextual.
        // The result of the case node is the result of the expression
        // for the first when node condition true
        // Template expression is:
        // case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        // Build case node, logical nodes don't return any values:
        // case Node type is null
        _caseNode=buildCaseNode(false,0);
        assertEquals(null, _caseNode.evaluate(null));
        // first when is true, case node type is the first when node type
        _caseNode.clearExprNodeList();
        _caseNode=buildCaseNode(true, 1);
        ExprWhenNode whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(0));
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Second when is true, case node type is the type of the second when node
        _caseNode=buildCaseNode(true, 2);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(1));
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Third test: we enable both when nodes: case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) end
        _caseNode=buildCaseNode(true, 3);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(0));
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Enabling the whole expression: case when (2.5>1) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        // the case node type is still the first value node expression type
        _caseNode=buildCaseNode(true, 7);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(0));
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Building expression:  case when (Float>Short) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 6);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(1));
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Last test: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 4);
        ExprElseNode elseNode = (ExprElseNode) (_caseNode.getExprNodeList(2));
        assertEquals(elseNode.evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
    }

    public void testEquals()  throws Exception
    {
        // Template expression is:
        // case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) else (3*3) end

        // Building expression:
        // case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) end
        ExprCaseNode otherCaseNode;
        _caseNode=buildCaseNode(false,0);
        otherCaseNode=buildCaseNode(false,0);
        assertTrue(_caseNode.equalsNode(otherCaseNode));
        _caseNode.clearExprNodeList();
        otherCaseNode.clearExprNodeList();
        // case when (2.5>2) then count(5) when (Long>Integer) then (25 + 130.5) end
        _caseNode=buildCaseNode(true, 1);
        // compare to
        // case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) end
        // Test successful as only the operand of the Relational Operator is used
        // for the comparison.
        otherCaseNode=buildCaseNode(false,0);
        assertTrue(_caseNode.equalsNode(otherCaseNode));
        _caseNode.clearExprNodeList();
        otherCaseNode.clearExprNodeList();
        // case when (Float>Short) then count(5) when (3>2) then (25 + 130.5) end
        _caseNode=buildCaseNode(true, 2);
        otherCaseNode=buildCaseNode(true,2);
        assertTrue(_caseNode.equalsNode(otherCaseNode));
        // We change the value expression for the second when node regarding
        // the second case node.
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.ADD);
        ExprWhenNode whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(1));
        whenNode.setValExprNode(arithNode);
        _caseNode.setExprNodeList(1, whenNode);
        // The test is yet successful, the ExprMathNode nodes are equals
        // when only their operators are the same.
        assertTrue(_caseNode.equalsNode(otherCaseNode));
        // This test shows it.
        arithNode = new ExprMathNode(ArithTypeEnum.DIVIDE);
        whenNode = (ExprWhenNode) (_caseNode.getExprNodeList(1));
        whenNode.setValExprNode(arithNode);
        _caseNode.setExprNodeList(1, whenNode);        
        assertFalse(_caseNode.equalsNode(otherCaseNode));
        //Testign equalNode on the else node.
        // Last test: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) else (3&3) end
        otherCaseNode.clearExprNodeList();
        _caseNode.clearExprNodeList();
        _caseNode=buildCaseNode(true, 4);
        otherCaseNode=buildCaseNode(true, 4);
        assertTrue(otherCaseNode.equalsNode(_caseNode));
        // Changing only the else node for the other case node.
        arithNode = new ExprMathNode(ArithTypeEnum.DIVIDE);
        ExprElseNode elseNode = (ExprElseNode) (otherCaseNode.getExprNodeList(2));
        elseNode.getChildNodes().set(0,arithNode);
        otherCaseNode.setExprNodeList(2, elseNode);
        assertFalse(otherCaseNode.equalsNode(_caseNode));
    }

    public void testToExpressionString() throws Exception
    {
        // Build: case when 2.5>2 then count(5) when 3>2 then (25+130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 7);
        log.debug(_caseNode.toExpressionString());
        assertEquals(" case when 2.5>2 then count(5) when 3>2 then (25+130.5) else (3*3) end", _caseNode.toExpressionString());
    }

    private ExprNode makeNode(ExprNode node_, Class typeLeft_, Class typeRight_)
    {
        node_.addChildNode(new SupportExprNode(typeLeft_));
        node_.addChildNode(new SupportExprNode(typeRight_));
        return node_;
    }

    private ExprNode makeNode(ExprNode node_, Object value, Class type) throws Exception
    {
        node_.addChildNode(new SupportExprNode(value, type));
        node_.validate(null);
        return node_;
    }

    private ExprNode makeNode(ExprNode node_, Object valueLeft_, Class typeLeft_, Object valueRight_, Class typeRight_)
    {
        node_.addChildNode(new SupportExprNode(valueLeft_, typeLeft_));
        node_.addChildNode(new SupportExprNode(valueRight_, typeRight_));
        return node_;
    }

    private ExprCaseNode buildCaseNode(boolean withValue_, int whenIndex_) throws Exception
    {
        List<ExprNode> listExprNode = new ArrayList<ExprNode>();;
        ExprWhenNode[] whenNodes = new ExprWhenNode[2];
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(RelationalOpEnum.GT);
        if ((withValue_) && ((whenIndex_ & 1)==1))
        {
            opNode = (ExprRelationalOpNode) makeNode(opNode,new Float(2.5F), float.class, new Short((short)2), Short.class);
            log.debug("opNode " + opNode.toExpressionString());
        }
        else
        {
            opNode = (ExprRelationalOpNode) makeNode(opNode, float.class, Short.class);
        }
        opNode.validate(null);
        SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[] {10, 20});
        ExprCountNode countNode = new ExprCountNode(false);
        countNode = (ExprCountNode) makeNode(countNode, 5, Integer.class);
        countNode.setAggregationResultFuture(future, 1);
        countNode.validate(null);
        log.debug("countNode " + countNode.toExpressionString());
        whenNodes[0] = new ExprWhenNode();
        whenNodes[0].addChildNode(opNode);
        whenNodes[0].addChildNode(countNode);
        log.debug(whenNodes[0].getType().getName());
        listExprNode.add(whenNodes[0]);
        opNode = new ExprRelationalOpNode(RelationalOpEnum.GT);
        if ((withValue_) && ((whenIndex_ & 2)==2))
        {
            opNode = (ExprRelationalOpNode) makeNode(opNode,new Long(3L), Long.class, new Integer(2), Integer.class);
            log.debug("opNode " + opNode.toExpressionString());
        }
        else
        {
            opNode = (ExprRelationalOpNode) makeNode(opNode, Long.class, Integer.class);
        }
        opNode.validate(null);
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.ADD);
        arithNode.addChildNode(new SupportExprNode(new Integer(25)));
        arithNode.addChildNode(new SupportExprNode(new Double(130.5)));
        arithNode.validateDescendents(null);
        whenNodes[1] = new ExprWhenNode();
        whenNodes[1].addChildNode(opNode);
        whenNodes[1].addChildNode(arithNode);
        log.debug(whenNodes[1].getType().getName());
        listExprNode.add(whenNodes[1]);
        if ((whenIndex_ & 4)==4)
        {
            ExprElseNode elseNode = new ExprElseNode();
            arithNode = new ExprMathNode(ArithTypeEnum.MULTIPLY);
            arithNode.addChildNode(new SupportExprNode(new Integer(3)));
            arithNode.addChildNode(new SupportExprNode(new Integer(3)));
            arithNode.validateDescendents(null);
            elseNode.addChildNode(arithNode);
            listExprNode.add(elseNode);
        }
        ExprCaseNode node = new ExprCaseNode(false, listExprNode);
        return (node);
    }

     private static final Log log = LogFactory.getLog(TestExprCaseNode.class);
}
