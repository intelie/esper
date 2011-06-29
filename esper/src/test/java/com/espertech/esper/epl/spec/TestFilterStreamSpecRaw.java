package com.espertech.esper.epl.spec;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.expression.ExprAndNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.parse.EPLTreeWalker;
import com.espertech.esper.filter.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.parse.SupportEPLTreeWalkerFactory;
import com.espertech.esper.support.epl.parse.SupportParserHelper;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import junit.framework.TestCase;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.Tree;

import java.util.*;

public class TestFilterStreamSpecRaw extends TestCase
{
    public void testNoExpr() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName());
        FilterSpecCompiled spec = compile(raw);
        assertEquals(SupportBean.class, spec.getFilterForEventType().getUnderlyingType());
        assertEquals(0, spec.getParameters().size());
    }

    public void testMultipleExpr() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "(intPrimitive-1>2 and intBoxed-5>3)");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(SupportBean.class, spec.getFilterForEventType().getUnderlyingType());
        assertEquals(1, spec.getParameters().size());
        // expecting unoptimized expressions to condense to a single boolean expression, more efficient this way

        FilterSpecParamExprNode exprNode = (FilterSpecParamExprNode) spec.getParameters().getFirst();
        assertEquals(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION, exprNode.getPropertyName());
        assertEquals(FilterOperator.BOOLEAN_EXPRESSION, exprNode.getFilterOperator());
        assertTrue(exprNode.getExprNode() instanceof ExprAndNode);
    }

    public void testInvalid() throws Exception
    {
        tryInvalid("select * from " + SupportBean.class.getName() + "(intPrimitive=5L)");
        tryInvalid("select * from " + SupportBean.class.getName() + "(5d = byteBoxed)");
        tryInvalid("select * from " + SupportBean.class.getName() + "(5d > longBoxed)");
        tryInvalid("select * from " + SupportBean.class.getName() + "(longBoxed in (5d, 1.1d))");
    }

    private void tryInvalid(String text) throws Exception
    {
        try
        {
            FilterStreamSpecRaw raw = makeSpec(text);
            compile(raw);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }

    public void testEquals() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() + "(intPrimitive=5)");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(1, spec.getParameters().size());
        assertEquals("intPrimitive", spec.getParameters().getFirst().getPropertyName());
        assertEquals(FilterOperator.EQUAL, spec.getParameters().getFirst().getFilterOperator());
        assertEquals(5, getConstant(spec.getParameters().getFirst()));
    }

    public void testEqualsAndLess() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() + "(string='a' and intPrimitive<9)");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(2, spec.getParameters().size());
        Map<String, FilterSpecParam> params = mapParameters(spec.getParameters());

        assertEquals(FilterOperator.EQUAL, params.get("string").getFilterOperator());
        assertEquals("a", getConstant(params.get("string")));

        assertEquals(FilterOperator.LESS, params.get("intPrimitive").getFilterOperator());
        assertEquals(9, getConstant(params.get("intPrimitive")));
    }

    private Map<String, FilterSpecParam> mapParameters(ArrayDeque<FilterSpecParam> params)
    {
        Map<String, FilterSpecParam> map = new HashMap<String, FilterSpecParam>();
        for (FilterSpecParam param : params)
        {
            map.put(param.getPropertyName(), param);
        }
        return map;
    }

    public void testCommaAndCompar() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "(doubleBoxed>1.11, doublePrimitive>=9.11 and intPrimitive<=9, string || 'a' = 'sa')");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(4, spec.getParameters().size());
        Map<String, FilterSpecParam> params = mapParameters(spec.getParameters());

        assertEquals(FilterOperator.GREATER, params.get("doubleBoxed").getFilterOperator());
        assertEquals(1.11, getConstant(params.get("doubleBoxed")));

        assertEquals(FilterOperator.GREATER_OR_EQUAL, params.get("doublePrimitive").getFilterOperator());
        assertEquals(9.11, getConstant(params.get("doublePrimitive")));

        assertEquals(FilterOperator.LESS_OR_EQUAL, params.get("intPrimitive").getFilterOperator());
        assertEquals(9, getConstant(params.get("intPrimitive")));

        assertEquals(FilterOperator.BOOLEAN_EXPRESSION, params.get(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION).getFilterOperator());
        assertTrue(params.get(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION) instanceof FilterSpecParamExprNode);
    }

    public void testNestedAnd() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "((doubleBoxed=1 and doublePrimitive=2) and (intPrimitive=3 and (string like '%_a' and string = 'a')))");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(5, spec.getParameters().size());
        Map<String, FilterSpecParam> params = mapParameters(spec.getParameters());

        assertEquals(FilterOperator.EQUAL, params.get("doubleBoxed").getFilterOperator());
        assertEquals(1.0, getConstant(params.get("doubleBoxed")));

        assertEquals(FilterOperator.EQUAL, params.get("doublePrimitive").getFilterOperator());
        assertEquals(2.0, getConstant(params.get("doublePrimitive")));

        assertEquals(FilterOperator.EQUAL, params.get("intPrimitive").getFilterOperator());
        assertEquals(3, getConstant(params.get("intPrimitive")));

        assertEquals(FilterOperator.EQUAL, params.get("string").getFilterOperator());
        assertEquals("a", getConstant(params.get("string")));

        assertEquals(FilterOperator.BOOLEAN_EXPRESSION, params.get(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION).getFilterOperator());
        assertTrue(params.get(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION) instanceof FilterSpecParamExprNode);
    }

    public void testIn() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() + "(doubleBoxed in (1, 2, 3))");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(1, spec.getParameters().size());

        assertEquals("doubleBoxed", spec.getParameters().getFirst().getPropertyName());
        assertEquals(FilterOperator.IN_LIST_OF_VALUES, spec.getParameters().getFirst().getFilterOperator());
        FilterSpecParamIn inParam = (FilterSpecParamIn) spec.getParameters().getFirst();
        assertEquals(3, inParam.getListOfValues().size());
        assertEquals(1.0, getConstant(inParam.getListOfValues().get(0)));
        assertEquals(2.0, getConstant(inParam.getListOfValues().get(1)));
        assertEquals(3.0, getConstant(inParam.getListOfValues().get(2)));
    }

    public void testNotIn() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() + "(string not in (\"a\"))");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(1, spec.getParameters().size());

        assertEquals("string", spec.getParameters().getFirst().getPropertyName());
        assertEquals(FilterOperator.NOT_IN_LIST_OF_VALUES, spec.getParameters().getFirst().getFilterOperator());
        FilterSpecParamIn inParam = (FilterSpecParamIn) spec.getParameters().getFirst();
        assertEquals(1, inParam.getListOfValues().size());
        assertEquals("a", getConstant(inParam.getListOfValues().get(0)));
    }

    public void testRanges() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "(intBoxed in [1:5] and doubleBoxed in (2:6) and floatBoxed in (3:7] and byteBoxed in [0:1))");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(4, spec.getParameters().size());
        Map<String, FilterSpecParam> params = mapParameters(spec.getParameters());

        assertEquals(FilterOperator.RANGE_CLOSED, params.get("intBoxed").getFilterOperator());
        FilterSpecParamRange rangeParam = (FilterSpecParamRange) params.get("intBoxed");
        assertEquals(1.0, getConstant(rangeParam.getMin()));
        assertEquals(5.0, getConstant(rangeParam.getMax()));

        assertEquals(FilterOperator.RANGE_OPEN, params.get("doubleBoxed").getFilterOperator());
        rangeParam = (FilterSpecParamRange) params.get("doubleBoxed");
        assertEquals(2.0, getConstant(rangeParam.getMin()));
        assertEquals(6.0, getConstant(rangeParam.getMax()));

        assertEquals(FilterOperator.RANGE_HALF_CLOSED, params.get("floatBoxed").getFilterOperator());
        rangeParam = (FilterSpecParamRange) params.get("floatBoxed");
        assertEquals(3.0, getConstant(rangeParam.getMin()));
        assertEquals(7.0, getConstant(rangeParam.getMax()));

        assertEquals(FilterOperator.RANGE_HALF_OPEN, params.get("byteBoxed").getFilterOperator());
        rangeParam = (FilterSpecParamRange) params.get("byteBoxed");
        assertEquals(0.0, getConstant(rangeParam.getMin()));
        assertEquals(1.0, getConstant(rangeParam.getMax()));
    }

    public void testRangesNot() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "(intBoxed not in [1:5] and doubleBoxed not in (2:6) and floatBoxed not in (3:7] and byteBoxed not in [0:1))");
        FilterSpecCompiled spec = compile(raw);
        assertEquals(4, spec.getParameters().size());
        Map<String, FilterSpecParam> params = mapParameters(spec.getParameters());

        assertEquals(FilterOperator.NOT_RANGE_CLOSED, params.get("intBoxed").getFilterOperator());
        FilterSpecParamRange rangeParam = (FilterSpecParamRange) params.get("intBoxed");
        assertEquals(1.0, getConstant(rangeParam.getMin()));
        assertEquals(5.0, getConstant(rangeParam.getMax()));

        assertEquals(FilterOperator.NOT_RANGE_OPEN, params.get("doubleBoxed").getFilterOperator());
        rangeParam = (FilterSpecParamRange) params.get("doubleBoxed");
        assertEquals(2.0, getConstant(rangeParam.getMin()));
        assertEquals(6.0, getConstant(rangeParam.getMax()));

        assertEquals(FilterOperator.NOT_RANGE_HALF_CLOSED, params.get("floatBoxed").getFilterOperator());
        rangeParam = (FilterSpecParamRange) params.get("floatBoxed");
        assertEquals(3.0, getConstant(rangeParam.getMin()));
        assertEquals(7.0, getConstant(rangeParam.getMax()));

        assertEquals(FilterOperator.NOT_RANGE_HALF_OPEN, params.get("byteBoxed").getFilterOperator());
        rangeParam = (FilterSpecParamRange) params.get("byteBoxed");
        assertEquals(0.0, getConstant(rangeParam.getMin()));
        assertEquals(1.0, getConstant(rangeParam.getMax()));
    }

    private double getConstant(FilterSpecParamRangeValue param)
    {
        return ((RangeValueDouble) param).getDoubleValue();
    }

    private Object getConstant(FilterSpecParamInValue param)
    {
        InSetOfValuesConstant constant = (InSetOfValuesConstant) param;
        return constant.getConstant();
    }

    private Object getConstant(FilterSpecParam param)
    {
        FilterSpecParamConstant constant = (FilterSpecParamConstant) param;
        return constant.getFilterConstant();
    }

    private FilterSpecCompiled compile(FilterStreamSpecRaw raw) throws Exception
    {
        FilterStreamSpecCompiled compiled = (FilterStreamSpecCompiled) raw.compile(SupportStatementContextFactory.makeContext(), new HashSet<String>(), false, Collections.<Integer>emptyList());
        return compiled.getFilterSpec();
    }

    private static FilterStreamSpecRaw makeSpec(String expression) throws Exception
    {
        Pair<Tree, CommonTokenStream> ast = SupportParserHelper.parseEPL(expression);
        SupportParserHelper.displayAST(ast.getFirst());

        EPLTreeWalker walker = SupportEPLTreeWalkerFactory.makeWalker(ast.getFirst(), ast.getSecond());
        walker.startEPLExpressionRule();

        return (FilterStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);
    }
}
