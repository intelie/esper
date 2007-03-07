package net.esper.eql.spec;

import antlr.collections.AST;
import junit.framework.TestCase;
import net.esper.eql.core.AutoImportServiceImpl;
import net.esper.eql.parse.EQLTreeWalker;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.expression.ExprAndNode;
import net.esper.filter.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.parse.SupportParserHelper;
import net.esper.support.event.SupportEventAdapterService;

public class TestFilterStreamSpecRaw extends TestCase
{
    public void testNoExpr() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName());
        FilterSpec spec = compile(raw);
        assertEquals(SupportBean.class, spec.getEventType().getUnderlyingType());
        assertEquals(0, spec.getParameters().size());
    }

    public void testMultipleExpr() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "(intPrimitive-1>2 and intBoxed-5>3)");
        FilterSpec spec = compile(raw);
        assertEquals(SupportBean.class, spec.getEventType().getUnderlyingType());
        assertEquals(1, spec.getParameters().size());
        // expecting unoptimized expressions to condense to a single boolean expression, more efficient this way

        FilterSpecParamExprNode exprNode = (FilterSpecParamExprNode) spec.getParameters().get(0);
        assertEquals(FilterStreamSpecRaw.PROPERTY_NAME_BOOLEAN_EXPRESSION, exprNode.getPropertyName());
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
        FilterSpec spec = compile(raw);
        assertEquals(1, spec.getParameters().size());
        assertEquals("intPrimitive", spec.getParameters().get(0).getPropertyName());
        assertEquals(FilterOperator.EQUAL, spec.getParameters().get(0).getFilterOperator());
        assertEquals(5, getConstant(spec.getParameters().get(0)));
    }

    public void testEqualsAndLess() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() + "(string='a' and intPrimitive<9)");
        FilterSpec spec = compile(raw);
        assertEquals(2, spec.getParameters().size());

        assertEquals("string", spec.getParameters().get(0).getPropertyName());
        assertEquals(FilterOperator.EQUAL, spec.getParameters().get(0).getFilterOperator());
        assertEquals("a", getConstant(spec.getParameters().get(0)));

        assertEquals("intPrimitive", spec.getParameters().get(1).getPropertyName());
        assertEquals(FilterOperator.LESS, spec.getParameters().get(1).getFilterOperator());
        assertEquals(9, getConstant(spec.getParameters().get(1)));
    }

    public void testCommaAndCompar() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "(doubleBoxed>1.11, doublePrimitive>=9.11 and intPrimitive<=9, string || 'a' = 'sa')");
        FilterSpec spec = compile(raw);
        assertEquals(4, spec.getParameters().size());

        assertEquals("doubleBoxed", spec.getParameters().get(0).getPropertyName());
        assertEquals(FilterOperator.GREATER, spec.getParameters().get(0).getFilterOperator());
        assertEquals(1.11, getConstant(spec.getParameters().get(0)));

        assertEquals("doublePrimitive", spec.getParameters().get(1).getPropertyName());
        assertEquals(FilterOperator.GREATER_OR_EQUAL, spec.getParameters().get(1).getFilterOperator());
        assertEquals(9.11, getConstant(spec.getParameters().get(1)));

        assertEquals("intPrimitive", spec.getParameters().get(2).getPropertyName());
        assertEquals(FilterOperator.LESS_OR_EQUAL, spec.getParameters().get(2).getFilterOperator());
        assertEquals(9, getConstant(spec.getParameters().get(2)));

        assertEquals(FilterStreamSpecRaw.PROPERTY_NAME_BOOLEAN_EXPRESSION, spec.getParameters().get(3).getPropertyName());
        assertEquals(FilterOperator.BOOLEAN_EXPRESSION, spec.getParameters().get(3).getFilterOperator());
        assertTrue(spec.getParameters().get(3) instanceof FilterSpecParamExprNode);
    }

    public void testNestedAnd() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "((doubleBoxed=1 and doublePrimitive=2) and (intPrimitive=3 and (string like '%_a' and string = 'a')))");
        FilterSpec spec = compile(raw);
        assertEquals(5, spec.getParameters().size());

        assertEquals("doubleBoxed", spec.getParameters().get(0).getPropertyName());
        assertEquals(FilterOperator.EQUAL, spec.getParameters().get(0).getFilterOperator());
        assertEquals(1.0, getConstant(spec.getParameters().get(0)));

        assertEquals("doublePrimitive", spec.getParameters().get(1).getPropertyName());
        assertEquals(FilterOperator.EQUAL, spec.getParameters().get(1).getFilterOperator());
        assertEquals(2.0, getConstant(spec.getParameters().get(1)));

        assertEquals("intPrimitive", spec.getParameters().get(2).getPropertyName());
        assertEquals(FilterOperator.EQUAL, spec.getParameters().get(2).getFilterOperator());
        assertEquals(3, getConstant(spec.getParameters().get(2)));

        assertEquals("string", spec.getParameters().get(3).getPropertyName());
        assertEquals(FilterOperator.EQUAL, spec.getParameters().get(3).getFilterOperator());
        assertEquals("a", getConstant(spec.getParameters().get(3)));

        assertEquals(FilterStreamSpecRaw.PROPERTY_NAME_BOOLEAN_EXPRESSION, spec.getParameters().get(4).getPropertyName());
        assertEquals(FilterOperator.BOOLEAN_EXPRESSION, spec.getParameters().get(4).getFilterOperator());
        assertTrue(spec.getParameters().get(4) instanceof FilterSpecParamExprNode);
    }

    public void testIn() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() + "(doubleBoxed in (1, 2, 3))");
        FilterSpec spec = compile(raw);
        assertEquals(1, spec.getParameters().size());

        assertEquals("doubleBoxed", spec.getParameters().get(0).getPropertyName());
        assertEquals(FilterOperator.IN_LIST_OF_VALUES, spec.getParameters().get(0).getFilterOperator());
        FilterSpecParamIn inParam = (FilterSpecParamIn) spec.getParameters().get(0);
        assertEquals(3, inParam.getListOfValues().size());
        assertEquals(1.0, getConstant(inParam.getListOfValues().get(0)));
        assertEquals(2.0, getConstant(inParam.getListOfValues().get(1)));
        assertEquals(3.0, getConstant(inParam.getListOfValues().get(2)));
    }

    public void testNotIn() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() + "(string not in (\"a\"))");
        FilterSpec spec = compile(raw);
        assertEquals(1, spec.getParameters().size());

        assertEquals("string", spec.getParameters().get(0).getPropertyName());
        assertEquals(FilterOperator.NOT_IN_LIST_OF_VALUES, spec.getParameters().get(0).getFilterOperator());
        FilterSpecParamIn inParam = (FilterSpecParamIn) spec.getParameters().get(0);
        assertEquals(1, inParam.getListOfValues().size());
        assertEquals("a", getConstant(inParam.getListOfValues().get(0)));
    }

    public void testRanges() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "(intBoxed in [1:5] and doubleBoxed in (2:6) and floatBoxed in (3:7] and byteBoxed in [0:1))");
        FilterSpec spec = compile(raw);
        assertEquals(4, spec.getParameters().size());

        assertEquals("intBoxed", spec.getParameters().get(0).getPropertyName());
        assertEquals(FilterOperator.RANGE_CLOSED, spec.getParameters().get(0).getFilterOperator());
        FilterSpecParamRange rangeParam = (FilterSpecParamRange) spec.getParameters().get(0);
        assertEquals(1.0, getConstant(rangeParam.getMin()));
        assertEquals(5.0, getConstant(rangeParam.getMax()));

        assertEquals("doubleBoxed", spec.getParameters().get(1).getPropertyName());
        assertEquals(FilterOperator.RANGE_OPEN, spec.getParameters().get(1).getFilterOperator());
        rangeParam = (FilterSpecParamRange) spec.getParameters().get(1);
        assertEquals(2.0, getConstant(rangeParam.getMin()));
        assertEquals(6.0, getConstant(rangeParam.getMax()));

        assertEquals("floatBoxed", spec.getParameters().get(2).getPropertyName());
        assertEquals(FilterOperator.RANGE_HALF_CLOSED, spec.getParameters().get(2).getFilterOperator());
        rangeParam = (FilterSpecParamRange) spec.getParameters().get(2);
        assertEquals(3.0, getConstant(rangeParam.getMin()));
        assertEquals(7.0, getConstant(rangeParam.getMax()));

        assertEquals("byteBoxed", spec.getParameters().get(3).getPropertyName());
        assertEquals(FilterOperator.RANGE_HALF_OPEN, spec.getParameters().get(3).getFilterOperator());
        rangeParam = (FilterSpecParamRange) spec.getParameters().get(3);
        assertEquals(0.0, getConstant(rangeParam.getMin()));
        assertEquals(1.0, getConstant(rangeParam.getMax()));
    }

    public void testRangesNot() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() +
                "(intBoxed not in [1:5] and doubleBoxed not in (2:6) and floatBoxed not in (3:7] and byteBoxed not in [0:1))");
        FilterSpec spec = compile(raw);
        assertEquals(4, spec.getParameters().size());

        assertEquals("intBoxed", spec.getParameters().get(0).getPropertyName());
        assertEquals(FilterOperator.NOT_RANGE_CLOSED, spec.getParameters().get(0).getFilterOperator());
        FilterSpecParamRange rangeParam = (FilterSpecParamRange) spec.getParameters().get(0);
        assertEquals(1.0, getConstant(rangeParam.getMin()));
        assertEquals(5.0, getConstant(rangeParam.getMax()));

        assertEquals("doubleBoxed", spec.getParameters().get(1).getPropertyName());
        assertEquals(FilterOperator.NOT_RANGE_OPEN, spec.getParameters().get(1).getFilterOperator());
        rangeParam = (FilterSpecParamRange) spec.getParameters().get(1);
        assertEquals(2.0, getConstant(rangeParam.getMin()));
        assertEquals(6.0, getConstant(rangeParam.getMax()));

        assertEquals("floatBoxed", spec.getParameters().get(2).getPropertyName());
        assertEquals(FilterOperator.NOT_RANGE_HALF_CLOSED, spec.getParameters().get(2).getFilterOperator());
        rangeParam = (FilterSpecParamRange) spec.getParameters().get(2);
        assertEquals(3.0, getConstant(rangeParam.getMin()));
        assertEquals(7.0, getConstant(rangeParam.getMax()));

        assertEquals("byteBoxed", spec.getParameters().get(3).getPropertyName());
        assertEquals(FilterOperator.NOT_RANGE_HALF_OPEN, spec.getParameters().get(3).getFilterOperator());
        rangeParam = (FilterSpecParamRange) spec.getParameters().get(3);
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

    private FilterSpec compile(FilterStreamSpecRaw raw) throws Exception
    {
        FilterStreamSpecCompiled compiled = (FilterStreamSpecCompiled) raw.compile(SupportEventAdapterService.getService(), new AutoImportServiceImpl());
        return compiled.getFilterSpec();
    }
    
    private static FilterStreamSpecRaw makeSpec(String expression) throws Exception
    {
        AST ast = SupportParserHelper.parseEQL(expression);
        SupportParserHelper.displayAST(ast);

        EQLTreeWalker walker = new EQLTreeWalker();
        walker.startEQLExpressionRule(ast);

        FilterStreamSpecRaw spec = (FilterStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);
        return spec;
    }    
}
