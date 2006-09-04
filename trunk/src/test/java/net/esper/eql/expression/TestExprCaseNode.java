package net.esper.eql.expression;

import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import net.esper.collection.Pair;
import net.esper.type.RelationalOpEnum;
import net.esper.type.ArithTypeEnum;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.eql.SupportAggregationResultFuture;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.eql.parse.ASTFilterSpecHelper;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprCaseNode extends TestCase
{
    private ExprCaseNode _caseNode;
    private StreamTypeService _streamTypeService = new SupportStreamTypeSvc3Stream();
    private EventBean[] _events;

    public void setUp()
    {
        StreamTypeService _streamTypeService = new SupportStreamTypeSvc3Stream();
    }

    public void testGetType()  throws Exception
    {
        // Case Node type is based on the context of the different when node expressions
        // First when node true, the node type of the case node is the type of the value expression
        // for this when node.

        // Template expression is:
        // case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) end
        // Build case node, logical nodes don't return any values:
        // case Node type is null
        _caseNode=buildCaseNode(false,0);
        assertEquals(null, _caseNode.getType());
        _caseNode.clearExprNodeList();
        // First when node is true, case node type is the first when node type
        // case when (2.5>2) then count(5) when (Long>Integer) then (25 + 130.5) end
        _caseNode=buildCaseNode(true, 1);
        Pair<ExprNode, ExprNode> p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Second when node is true, case node type is the type of the second when node
        // case when (Float>Short) then count(5) when (3>2) then (25 + 130.5) end
        _caseNode=buildCaseNode(true, 2);
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Third test: we enable both when nodes: case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) end
        _caseNode=buildCaseNode(true, 3);
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Enabling the whole expression: case when (2.5>1) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        // the case node type is still the first value node expression type
        _caseNode=buildCaseNode(true, 7);
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Building expression:  case when (Float>Short) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 6);
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // Last test: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 4);
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();

        // Second set of tests

        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        _caseNode=buildCase2Node();
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // intPrimitive = intBoxed
        _events = new EventBean[] {makeEvent(10), makeEvent(new Integer(10))};
        _caseNode=buildCase2Node();
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().getType(), _caseNode.getType(_events));
        _caseNode.clearExprNodeList();
        // Changing the event flow
        _events = new EventBean[] {makeEvent(10), makeEvent(new Integer(20))};
        _caseNode=buildCase2Node();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // intPrimitive != intBoxed
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().getType(), _caseNode.getType(_events));
        _caseNode.clearExprNodeList();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // This time intPrimitive=20, intBoxed=40
        // Else node type becomes the case node type
        _events = new EventBean[] {makeEvent(20), makeEvent(new Integer(40))};
        _caseNode=buildCase2Node();
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().getType(), _caseNode.getType(_events));
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
            _caseNode = new ExprCaseNode(true, null);
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        try
        {
            _caseNode = new ExprCaseNode(false, new LinkedList<Pair<ExprNode,ExprNode>>());
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        // One sub-node but the when expression is incomplete:
        // missing: the action part.
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.DIVIDE);
        arithNode.addChildNode(new SupportExprNode(new Integer(4)));
        arithNode.addChildNode(new SupportExprNode(new Integer(2)));
        arithNode.validateDescendents(null);
        try
        {
            List<Pair<ExprNode,ExprNode>> listExprNodes = new LinkedList<Pair<ExprNode,ExprNode>>();
            Pair<ExprNode,ExprNode> p = new Pair(arithNode, null);
            listExprNodes.add(p);
            _caseNode = new ExprCaseNode(false, listExprNodes);
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        // Else node is not enough, it has to be paired with at least one when node.
        try
        {
            List<Pair<ExprNode,ExprNode>> listExprNodes = new LinkedList<Pair<ExprNode,ExprNode>>();
            Pair<ExprNode,ExprNode> p = new Pair(null, arithNode);
            listExprNodes.add(p);
            _caseNode = new ExprCaseNode(false, listExprNodes);
            _caseNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
        // One when node but more than one "else nods"
        // This should throw an exception.
        try
        {
            List<Pair<ExprNode,ExprNode>> listExprNodes = new LinkedList<Pair<ExprNode,ExprNode>>();
            Pair<ExprNode,ExprNode> p = new Pair(arithNode, arithNode);
            listExprNodes.add(p);
            p = new Pair(null, arithNode);
            listExprNodes.add(p);
            p = new Pair(null, arithNode);
            listExprNodes.add(p);
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

        // Second case
        _events = new EventBean[] {makeEvent(20), makeEvent(new Integer(40))};
        _caseNode=buildCase2Node();
        _caseNode.validate(_streamTypeService);
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
        Pair<ExprNode, ExprNode> p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Second when is true, case node type is the type of the second when node
        _caseNode=buildCaseNode(true, 2);
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Third test: we enable both when nodes: case when (2.5>2) then count(5) when (3>2) then (25 + 130.5) end
        _caseNode=buildCaseNode(true, 3);
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Enabling the whole expression: case when (2.5>1) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        // the case node type is still the first value node expression type
        _caseNode=buildCaseNode(true, 7);
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Building expression:  case when (Float>Short) then count(5) when (3>2) then (25 + 130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 6);
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // Last test: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 4);
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();

        // Second set of tests

        // case intPrimitive when longBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        _caseNode=buildCase2Node();
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // intPrimitive = intBoxed
        _events = new EventBean[] {makeEvent(10), makeEvent(new Integer(10))};
        _caseNode=buildCase2Node();
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
        _caseNode.clearExprNodeList();
        // Changing the event flow
        _events = new EventBean[] {makeEvent(10), makeEvent(new Integer(20))};
        _caseNode=buildCase2Node();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // intPrimitive != intBoxed and intPrimitive = (5*2)
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
        _caseNode.clearExprNodeList();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // This time intPrimitive=20, intBoxed=40
        // Else node type becomes the case node type
        _events = new EventBean[] {makeEvent(20), makeEvent(new Integer(40))};
        _caseNode=buildCase2Node();
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
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
        // We compare this last expression to:
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
        // We change the condition expression for the first when node regarding
        // the first case expression.
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(RelationalOpEnum.GE);
        Pair<ExprNode,ExprNode> p = _caseNode.getExprNodeList(0);
        p.setSecond(opNode);
        _caseNode.setExprNodeList(0, p);
        // The test is not successful, the relational operators are not equals
        assertFalse(_caseNode.equalsNode(otherCaseNode));
        // We change the value expression for the second when node regarding
        // the first case expression.
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        p = _caseNode.getExprNodeList(1);
        p.setSecond(arithNode);
        _caseNode.setExprNodeList(1, p);
        // The test is not successful, the ExprMathNode nodes are not equals
        // when their operators are not the same.
        assertFalse(_caseNode.equalsNode(otherCaseNode));
        //Testing equalNode on the else node.
        // Last test: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) else (3*3) end
        otherCaseNode.clearExprNodeList();
        _caseNode.clearExprNodeList();
        _caseNode=buildCaseNode(true, 4);
        otherCaseNode=buildCaseNode(true, 4);
        assertTrue(otherCaseNode.equalsNode(_caseNode));
        // Changing only the else node for the other case node.
        arithNode = new ExprMathNode(ArithTypeEnum.DIVIDE);
        p = otherCaseNode.getExprNodeList(2);
        p.setSecond(arithNode);
        otherCaseNode.setExprNodeList(2, p);
        assertFalse(otherCaseNode.equalsNode(_caseNode));

        // Second set of tests

        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        _caseNode=buildCase2Node();
        otherCaseNode=buildCase2Node();
        assertTrue(_caseNode.equalsNode(otherCaseNode));
        assertTrue(otherCaseNode.equalsNode(_caseNode));
        _caseNode.clearExprNodeList();
        otherCaseNode.clearExprNodeList();
        _caseNode=buildCase2Node();
        otherCaseNode=buildCase2Node();
        // We change the event property for the first when node
        // Case nodes are no longer equals
        ExprIdentNode identNode = new ExprIdentNode("longBoxed", "s1");
        identNode.validate(_streamTypeService);
        p = _caseNode.getExprNodeList(0);
        p.setFirst(arithNode);
        _caseNode.setExprNodeList(0, p);
        assertFalse(_caseNode.equalsNode(otherCaseNode));

    }

    public void testToExpressionString() throws Exception
    {
        // Build: case when 2.5>2 then count(5) when 3>2 then (25+130.5) else (3*3) end
        _caseNode=buildCaseNode(true, 7);
         assertEquals(" case  when 2.5>2 then count(5) when 3>2 then (25+130.5) else (3*3) end", _caseNode.toExpressionString());
        // Build: case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (10*20) end
        _caseNode = buildCase2Node();
        assertEquals(" case s0.intPrimitive when s1.intBoxed then count(5) when (5*2) then (s0.intPrimitive*4.0) else (10.0*20.0) end", _caseNode.toExpressionString());
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
        //Build: case when (Float>Short) then count(5) when (Long>Integer) then (25 + 130.5) else (3*3) end
        List<Pair<ExprNode, ExprNode>> listExprNode = new LinkedList<Pair<ExprNode, ExprNode>>();
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(RelationalOpEnum.GT);
        if ((withValue_) && ((whenIndex_ & 1)==1))
        {
            opNode = (ExprRelationalOpNode) makeNode(opNode,new Float(2.5F), float.class, new Short((short)2), Short.class);
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
        Pair<ExprNode, ExprNode> p = new Pair(opNode,countNode);
        listExprNode.add(p);
        opNode = new ExprRelationalOpNode(RelationalOpEnum.GT);
        if ((withValue_) && ((whenIndex_ & 2)==2))
        {
            opNode = (ExprRelationalOpNode) makeNode(opNode,new Long(3L), Long.class, new Integer(2), Integer.class);
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
        p = new Pair(opNode,arithNode);
        listExprNode.add(p);
        if ((whenIndex_ & 4)==4)
        {
            arithNode = new ExprMathNode(ArithTypeEnum.MULTIPLY);
            arithNode.addChildNode(new SupportExprNode(new Integer(3)));
            arithNode.addChildNode(new SupportExprNode(new Integer(3)));
            arithNode.validateDescendents(null);
            p = new Pair(null,arithNode);
            listExprNode.add(p);
        }
        ExprCaseNode node = new ExprCaseNode(false, listExprNode);
        return (node);
    }

    private ExprCaseNode buildCase2Node() throws Exception
    {
        // Build: case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (10*20) end
        List<Pair<ExprNode, ExprNode>> listExprNode = new LinkedList<Pair<ExprNode, ExprNode>>();
        ExprMathNode[] arithNodes = new  ExprMathNode[2];
        ExprIdentNode[] identNodes = new ExprIdentNode[2];
        identNodes[0] = new ExprIdentNode("intBoxed", "s1");
        identNodes[0].validate(_streamTypeService);
        SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[] {10, 20});
        ExprCountNode countNode = new ExprCountNode(false);
        countNode = (ExprCountNode) makeNode(countNode, 5, Integer.class);
        countNode.setAggregationResultFuture(future, 1);
        countNode.validate(null);
        Pair<ExprNode, ExprNode> p = new Pair(identNodes[0],countNode);
        listExprNode.add(p);
        arithNodes[0] = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        arithNodes[0].addChildNode(new SupportExprNode(new Integer(5)));
        arithNodes[0].addChildNode(new SupportExprNode(new Integer(2)));
        arithNodes[0].validateDescendents(_streamTypeService);
        arithNodes[1] = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        identNodes[1] = new ExprIdentNode("intPrimitive", "s0");
        identNodes[1].validate(_streamTypeService);
        arithNodes[1].addChildNode(identNodes[1]);
        arithNodes[1].addChildNode(new SupportExprNode(new Double(4.0)));
        arithNodes[1].validateDescendents(_streamTypeService);
        p = new Pair(arithNodes[0],arithNodes[1]);
        listExprNode.add(p);
        arithNodes[0] = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        arithNodes[0].addChildNode(new SupportExprNode(new Double(10.0)));
        arithNodes[0].addChildNode(new SupportExprNode(new Double(20.0)));
        arithNodes[0].validateDescendents(_streamTypeService);
        p = new Pair(null,arithNodes[0]);
        listExprNode.add(p);
        ExprCaseNode node = new ExprCaseNode(true, listExprNode);
        node.addChildNode(identNodes[1]);
        return (node);
    }

    private EventBean makeEvent(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        return SupportEventBeanFactory.createObject(event);
    }

    private EventBean makeEvent(Integer intBoxed_)
    {
        SupportBean event = new SupportBean();
        event.setIntBoxed(intBoxed_);
        return SupportEventBeanFactory.createObject(event);
    }

     private static final Log log = LogFactory.getLog(TestExprCaseNode.class);
}
