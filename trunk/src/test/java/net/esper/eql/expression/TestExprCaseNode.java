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
import static net.esper.support.eql.SupportExprNodeFactory.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.eql.parse.ASTFilterSpecHelper;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprCaseNode extends TestCase
{
    private ExprCaseNode _caseNode;
    private StreamTypeService _streamTypeService;
    private EventBean[] _events;

    public void setUp()
    {
        _streamTypeService = new SupportStreamTypeSvc3Stream();
    }

    public void testGetType()  throws Exception
    {
        // Case Node type is based on the context of the different when node expressions
        // First when node true, the node type of the case node is the type of the value expression
        // for this when node.

        // Template expression is:
        // case when (so.floatPrimitive>s1.shortBoxed) then count(5) when (so.LongPrimitive>s1.intPrimitive) then (25 + 130.5) else (3*3) end
        // Build case node, logical nodes don't return any values:
        // case Node type is the first boolean expression found.
        _caseNode=makeCaseNode();
        Pair<ExprNode, ExprNode> p = _caseNode.getExprNodeList(0);
        assertEquals(p.getFirst().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // case when (2.5>2) then count(5) when (1>3) then (25 + 130.5) else (3*3) end
        // First when node is true, case node type is the first when node type.
        _caseNode=makeCaseNode();
        _events = new EventBean[] {makeEvent(0,null,2.5F,null,1L), makeEvent(3,null,0,new Short((short)2),0)};
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().getType(), _caseNode.getType(_events));
        _caseNode.clearExprNodeList();
        // case when (2>10) then count(5) when (3>1) then (25 + 130.5) else (3*3) end
        // Second when node is true, case node type is the type of the second when node
        _caseNode=makeCaseNode();
        _events = new EventBean[] {makeEvent(0,null,2F,null,3L), makeEvent(1,null,0,new Short((short)10),0)};
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().getType(), _caseNode.getType(_events));
        _caseNode.clearExprNodeList();
        // Last test: case when (2>10) then count(5) when (1>3) then (25 + 130.5) else (3*3) end
        _caseNode=makeCaseNode();
        _events = new EventBean[] {makeEvent(0,null,2F,null,1L), makeEvent(3,null,0,new Short((short)10),0)};
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().getType(), _caseNode.getType(_events));
        _caseNode.clearExprNodeList();

        // Second set of tests

        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        _caseNode=makeCase2Node();
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().getType(), _caseNode.getType());
        _caseNode.clearExprNodeList();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // intPrimitive = intBoxed
        _events = new EventBean[] {makeEvent(10), makeEvent(new Integer(10))};
        _caseNode=makeCase2Node();
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().getType(), _caseNode.getType(_events));
        _caseNode.clearExprNodeList();
        // Changing the event flow
        _events = new EventBean[] {makeEvent(10), makeEvent(new Integer(20))};
        _caseNode=makeCase2Node();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // intPrimitive != intBoxed
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().getType(), _caseNode.getType(_events));
        _caseNode.clearExprNodeList();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // This time intPrimitive=20, intBoxed=40
        // Else node type becomes the case node type
        _events = new EventBean[] {makeEvent(20), makeEvent(new Integer(40))};
        _caseNode=makeCase2Node();
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
        // One when node but more than one "else nodes"
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
        _caseNode=makeCaseNode();
        _events = new EventBean[] {makeEvent(1,new Integer(2),2F,new Short((short)3),1L), makeEvent(4,new Integer(5),0,new Short((short)10),0)};
        _caseNode.validate(_streamTypeService);
        _caseNode.clearExprNodeList();

        // Second case
        _events = new EventBean[] {makeEvent(20), makeEvent(new Integer(40))};
        _caseNode=makeCase2Node();
        _caseNode.validate(_streamTypeService);
        _caseNode.clearExprNodeList();
    }

    public void testEvaluate() throws Exception
    {
        // The value of the case node is contextual.
        // The result of the case node is the result of the expression
        // for the first when node condition true
        // Template expression is:
        // case when (so.floatPrimitive>s1.shortBoxed) then count(5) when (so.LongPrimitive>s1.intPrimitive) then (25 + 130.5) else (3*3) end

        // first when is true, case node type is the first when node type
        // case when (2.5>2) then count(5) when (1>3) then (25 + 130.5) else (3*3) end
        // First when node is true, case node type is the first when node type.
        _caseNode=makeCaseNode();
        _events = new EventBean[] {makeEvent(0,null,2.5F,null,1L), makeEvent(3,null,0,new Short((short)2),0)};
        Pair<ExprNode, ExprNode> p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
        _caseNode.clearExprNodeList();
        // Second when is true, case node type is the type of the second when node
        // case when (2>10) then count(5) when (3>1) then (25 + 130.5) else (3*3) end
        _caseNode=makeCaseNode();
        _events = new EventBean[] {makeEvent(0,null,2F,null,3L), makeEvent(1,null,0,new Short((short)10),0)};
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
        _caseNode.clearExprNodeList();
        // Third test: case when (2>10) then count(5) when (1>3) then (25 + 130.5) else (3*3) end
        _caseNode=makeCaseNode();
        _events = new EventBean[] {makeEvent(0,null,2F,null,1L), makeEvent(3,null,0,new Short((short)10),0)};
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
        _caseNode.clearExprNodeList();

        // Second set of tests

        // case intPrimitive when longBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        _caseNode=makeCase2Node();
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().evaluate(null), _caseNode.evaluate(null));
        _caseNode.clearExprNodeList();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // intPrimitive = intBoxed
        _events = new EventBean[] {makeEvent(10), makeEvent(new Integer(10))};
        _caseNode=makeCase2Node();
        p = _caseNode.getExprNodeList(0);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
        _caseNode.clearExprNodeList();
        // Changing the event flow
        _events = new EventBean[] {makeEvent(10), makeEvent(new Integer(20))};
        _caseNode=makeCase2Node();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // intPrimitive != intBoxed and intPrimitive = (5*2)
        p = _caseNode.getExprNodeList(1);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
        _caseNode.clearExprNodeList();
        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        // This time intPrimitive=20, intBoxed=40
        // Else node type becomes the case node type
        _events = new EventBean[] {makeEvent(20), makeEvent(new Integer(40))};
        _caseNode=makeCase2Node();
        p = _caseNode.getExprNodeList(2);
        assertEquals(p.getSecond().evaluate(_events), _caseNode.evaluate(_events));
    }

    public void testEquals()  throws Exception
    {
        ExprCaseNode otherCaseNode;

        // Building expression:
        // case when (so.floatPrimitive>s1.shortBoxed) then count(5) when (so.LongPrimitive>s1.intPrimitive) then (25 + 130.5) else (3*3) end
        _caseNode=makeCaseNode();
        otherCaseNode=makeCaseNode();
        assertTrue(_caseNode.equalsNode(otherCaseNode));
        assertTrue(otherCaseNode.equalsNode(_caseNode));
        _caseNode.clearExprNodeList();
        otherCaseNode.clearExprNodeList();

        // We change the condition expression for the first when node regarding
        // the first case expression.
        _caseNode=makeCaseNode();
        otherCaseNode=makeCaseNode();
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(RelationalOpEnum.GE);
        Pair<ExprNode,ExprNode> p = _caseNode.getExprNodeList(0);
        p.setFirst(opNode);
        _caseNode.setExprNodeList(0, p);
        // The test is not successful, the relational operators are not equals
        assertFalse(_caseNode.equalsNode(otherCaseNode));
        _caseNode.clearExprNodeList();
        otherCaseNode.clearExprNodeList();

        // We change the value expression for the second when node regarding
        // the first case expression.
        _caseNode=makeCaseNode();
        otherCaseNode=makeCaseNode();
        ExprMathNode arithNode = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        p = _caseNode.getExprNodeList(1);
        p.setSecond(arithNode);
        _caseNode.setExprNodeList(1, p);
        // The test is not successful, the ExprMathNode nodes are not equals
        // when their operators are not the same.
        assertFalse(_caseNode.equalsNode(otherCaseNode));
        _caseNode.clearExprNodeList();
        otherCaseNode.clearExprNodeList();

        // Changing only the else node for the other case node.
        _caseNode=makeCaseNode();
        otherCaseNode=makeCaseNode();
        arithNode = new ExprMathNode(ArithTypeEnum.DIVIDE);
        p = otherCaseNode.getExprNodeList(2);
        p.setSecond(arithNode);
        otherCaseNode.setExprNodeList(2, p);
        assertFalse(otherCaseNode.equalsNode(_caseNode));
        _caseNode.clearExprNodeList();
        otherCaseNode.clearExprNodeList();

        // Second set of tests

        // case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (3*3) end
        _caseNode=makeCase2Node();
        otherCaseNode=makeCase2Node();
        assertTrue(_caseNode.equalsNode(otherCaseNode));
        assertTrue(otherCaseNode.equalsNode(_caseNode));
        _caseNode.clearExprNodeList();
        otherCaseNode.clearExprNodeList();
        _caseNode=makeCase2Node();
        otherCaseNode=makeCase2Node();
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
        _caseNode=makeCaseNode();
        assertEquals(" case  when s0.floatPrimitive>s1.shortBoxed then count(5) when s0.longPrimitive>s1.intPrimitive then (25+130.5) else (3*3) end", _caseNode.toExpressionString());
        // Build: case intPrimitive when intBoxed then count(5) when (5*2) then (intPrimitive*4) else (10*20) end
        _caseNode = makeCase2Node();
        assertEquals(" case s0.intPrimitive when s1.intBoxed then count(5) when (5*2) then (s0.intPrimitive*4.0) else (10.0*20.0) end", _caseNode.toExpressionString());
    }

    private EventBean makeEvent(int i, Integer iB, float f, Short s, long l)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(i);
        event.setIntBoxed(iB);
        event.setFloatPrimitive(f);
        event.setShortBoxed(s);
        event.setLongPrimitive(l);
        return SupportEventBeanFactory.createObject(event);
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
