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
    private List<ExprNode> _listExprNode;

    public void setUp()
    {
           //_caseNode = new ExprCaseNode(false);
        _listExprNode = new ArrayList<ExprNode>();
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
        buildCaseNode(false,0);
        assertEquals(null, _caseNode.getType());
        // first when is true, case node type is the first when node type
        _listExprNode.clear();
        buildCaseNode(true, 1);
        ExprWhenNode whenNode = (ExprWhenNode) _listExprNode.get(0);
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _listExprNode.clear();
        // Second when is true, case node type is the type of the second when node
        buildCaseNode(true, 2);
        whenNode = (ExprWhenNode) _listExprNode.get(1);
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _listExprNode.clear();
        // Third test: we enable both when nodes: case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) end
        buildCaseNode(true, 3);
        whenNode = (ExprWhenNode) _listExprNode.get(0);
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _listExprNode.clear();
        // Enabling the whole expression: case when (2.5>1) then count(5) when (3>2) then (25 + 130.5) end
        // the case node type is still the first value node expression type
        buildCaseNode(true, 7);
        whenNode = (ExprWhenNode) _listExprNode.get(0);
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _listExprNode.clear();
        // Building expression:  case when (Float>Short) then count(5) when (3>2) then (25 + 130.5) end
        buildCaseNode(true, 6);
        whenNode = (ExprWhenNode) _listExprNode.get(1);
        assertEquals(whenNode.getValExprNode().getType(), _caseNode.getType());
        _listExprNode.clear();
        // Last test: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) end
        buildCaseNode(true, 4);
        ExprElseNode elseNode = (ExprElseNode) _listExprNode.get(2);
        assertEquals(elseNode.getType(), _caseNode.getType());
        _listExprNode.clear();
    }

    public void testValidate() throws Exception
    {
        // No subnodes: Exception is thrown.
        _listExprNode.clear();
        try
        {
            _caseNode = new ExprCaseNode(false, _listExprNode);
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
            _listExprNode.add(arithNode);
            _caseNode = new ExprCaseNode(false, _listExprNode);
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        _listExprNode.clear();
        // Else node is not enough, it has to be paired with at least one when node.
        ExprElseNode elseNode = new ExprElseNode();
        elseNode.addChildNode(arithNode);
        try
        {
            _listExprNode.add(elseNode);
            _caseNode = new ExprCaseNode(false, _listExprNode);
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        _listExprNode.clear();
        // expression: case when (2.5>1) then count(5) when (3>2) then (25 + 130.5) end
        // The validation should be successful for every node traversed.
        buildCaseNode(false, 7);
        _caseNode.validate(null);
        _listExprNode.clear();
        buildCaseNode(true, 7);
        _caseNode.validate(null);
        _listExprNode.clear();
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
        buildCaseNode(false,0);
        assertEquals(null, _caseNode.evaluate(null));
        // first when is true, case node type is the first when node type
        _listExprNode.clear();
        buildCaseNode(true, 1);
        ExprWhenNode whenNode = (ExprWhenNode) _listExprNode.get(0);
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _listExprNode.clear();
        // Second when is true, case node type is the type of the second when node
        buildCaseNode(true, 2);
        whenNode = (ExprWhenNode) _listExprNode.get(1);
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _listExprNode.clear();
        // Third test: we enable both when nodes: case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) end
        buildCaseNode(true, 3);
        whenNode = (ExprWhenNode) _listExprNode.get(0);
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _listExprNode.clear();
        // Enabling the whole expression: case when (2.5>1) then count(5) when (3>2) then (25 + 130.5) end
        // the case node type is still the first value node expression type
        buildCaseNode(true, 7);
        whenNode = (ExprWhenNode) _listExprNode.get(0);
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _listExprNode.clear();
        // Building expression:  case when (Float>Short) then count(5) when (3>2) then (25 + 130.5) end
        buildCaseNode(true, 6);
        whenNode = (ExprWhenNode) _listExprNode.get(1);
        assertEquals(whenNode.evaluate(null), _caseNode.evaluate(null));
        _listExprNode.clear();
        // Last test: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) end
        buildCaseNode(true, 4);
        ExprElseNode elseNode = (ExprElseNode) _listExprNode.get(2);
        assertEquals(elseNode.evaluate(null), _caseNode.evaluate(null));
        _listExprNode.clear();
    }

    public void testEquals()  throws Exception
    {

    }

    public void testToExpressionString() throws Exception
    {
        // Build: case when 2.5>2 then count(5) when 3>2 then (25+130.5) else (3*3) end
        buildCaseNode(true, 7);
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

    private void buildCaseNode(boolean withValue_, int whenIndex_) throws Exception
    {
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
        _listExprNode.add(whenNodes[0]);
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
        _listExprNode.add(whenNodes[1]);
        if ((whenIndex_ & 4)==4)
        {
            ExprElseNode elseNode = new ExprElseNode();
            arithNode = new ExprMathNode(ArithTypeEnum.MULTIPLY);
            arithNode.addChildNode(new SupportExprNode(new Integer(3)));
            arithNode.addChildNode(new SupportExprNode(new Integer(3)));
            arithNode.validateDescendents(null);
            elseNode.addChildNode(arithNode);
            _listExprNode.add(elseNode);
        }
        _caseNode = new ExprCaseNode(false, _listExprNode);
    }

     private static final Log log = LogFactory.getLog(TestExprCaseNode.class);
}
