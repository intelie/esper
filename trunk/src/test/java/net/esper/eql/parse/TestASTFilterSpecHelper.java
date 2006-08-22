package net.esper.eql.parse;

import junit.framework.TestCase;
import net.esper.support.eql.parse.SupportParserHelper;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.filter.*;
import net.esper.event.EventType;
import net.esper.event.BeanEventAdapter;
import net.esper.util.DebugFacility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import antlr.collections.AST;

import java.util.Map;
import java.util.HashMap;

public class TestASTFilterSpecHelper extends TestCase
{
    public void testInvalid() throws Exception
    {
        String classname = SupportBean.class.getName();

        assertIsInvalid("goofy.mickey");
        assertIsInvalid(classname + "(dummy=4)");
        assertIsInvalid(classname + "(boolPrimitive=4)");
        assertIsInvalid(classname + "(intPrimitive=false)");
        assertIsInvalid(classname + "(string in [2:2])");
        assertIsInvalid(classname + "(string=\"a\", string=\"b\")");        // Same attribute twice should be a problem
    }

    public void testValidNoParams() throws Exception
    {
        String expression = "gum=" + SupportBean.class.getName();

        FilterSpec spec = getFilterSpec(expression, null);
        assertEquals(SupportBean.class, spec.getEventType().getUnderlyingType());
        assertEquals(0, spec.getParameters().size());

        assertEquals("gum", getEventNameTag(expression));
    }

    public void testValidWithParams() throws Exception
    {
        String expression = "name=" + SupportBean.class.getName() + "(intPrimitive>4, string=\"test\", doublePrimitive in [1:4])";

        FilterSpec spec = getFilterSpec(expression, null);
        assertEquals(SupportBean.class, spec.getEventType().getUnderlyingType());
        assertEquals(3, spec.getParameters().size());

        FilterSpecParam param = spec.getParameters().get(0);
        assertEquals("intPrimitive", param.getPropertyName());
        assertEquals(FilterOperator.GREATER, param.getFilterOperator());
        assertEquals(4, param.getFilterValue(null));

        param = spec.getParameters().get(1);
        assertEquals("string", param.getPropertyName());
        assertEquals(FilterOperator.EQUAL, param.getFilterOperator());
        assertEquals("test", param.getFilterValue(null));

        param = spec.getParameters().get(2);
        assertEquals("doublePrimitive", param.getPropertyName());
        assertEquals(FilterOperator.RANGE_CLOSED, param.getFilterOperator());
        assertEquals(new DoubleRange(1, 4), param.getFilterValue(null));

        assertEquals("name", getEventNameTag(expression));
    }

    public void testValidUseResultParams() throws Exception
    {
        String expression = "n1=" + SupportBean.class.getName() + "(intPrimitive=n2.intBoxed)";

        Map<String, EventType> taggedEventTypes = new HashMap<String, EventType>();
        taggedEventTypes.put("n2", SupportEventTypeFactory.createBeanType(SupportBean.class));

        FilterSpec spec = getFilterSpec(expression, taggedEventTypes);

        assertEquals(SupportBean.class, spec.getEventType().getUnderlyingType());
        assertEquals(1, spec.getParameters().size());
        FilterSpecParamEventProp eventPropParam = (FilterSpecParamEventProp) spec.getParameters().get(0);
        assertEquals("n2", eventPropParam.getResultEventAsName());
        assertEquals("intBoxed", eventPropParam.getResultEventProperty());
    }

    public void testValidComplexProperty() throws Exception
    {
        String expression = "n1=" + SupportBeanComplexProps.class.getName() + "(mapped('a') = '1')";
        FilterSpec spec = getFilterSpec(expression, null);

        assertEquals(1, spec.getParameters().size());
        FilterSpecParamConstant param = (FilterSpecParamConstant) spec.getParameters().get(0);
        assertEquals("mapped('a')", param.getPropertyName());
    }

    public void testValidRange() throws Exception
    {
        String expression = "myname=" + SupportBean.class.getName() + "(intPrimitive in (1:2), intBoxed in [2:6))";

        FilterSpec spec = getFilterSpec(expression, null);
        assertEquals(SupportBean.class, spec.getEventType().getUnderlyingType());
        assertEquals(2, spec.getParameters().size());

        FilterSpecParam param = spec.getParameters().get(0);
        assertEquals("intPrimitive", param.getPropertyName());
        assertEquals(FilterOperator.RANGE_OPEN, param.getFilterOperator());

        param = spec.getParameters().get(1);
        assertEquals("intBoxed", param.getPropertyName());
        assertEquals(FilterOperator.RANGE_HALF_OPEN, param.getFilterOperator());

        assertEquals("myname", getEventNameTag(expression));
    }

    public void testValidRangeUseResult() throws Exception
    {
        String expression = "myname=" + SupportBean.class.getName() + "(intPrimitive in (asName.intPrimitive:asName.intBoxed))";

        Map<String, EventType> taggedEventTypes = new HashMap<String, EventType>();
        taggedEventTypes.put("asName", SupportEventTypeFactory.createBeanType(SupportBean.class));

        FilterSpec spec = getFilterSpec(expression, taggedEventTypes);
        assertEquals(SupportBean.class, spec.getEventType().getUnderlyingType());
        assertEquals(1, spec.getParameters().size());

        FilterSpecParam param = spec.getParameters().get(0);
        assertEquals("intPrimitive", param.getPropertyName());
        assertEquals(FilterOperator.RANGE_OPEN, param.getFilterOperator());
        assertEquals(DoubleRange.class, param.getFilterValueClass(taggedEventTypes));
    }

    public void testGetPropertyName() throws Exception
    {
        final String PROPERTY = "a('aa').b[1].c";

        // Should parse and result in the exact same property name
        AST propertyNameExprNode = SupportParserHelper.parseEventProperty(PROPERTY);
        String propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode.getFirstChild());
        assertEquals(PROPERTY, propertyName);

        // Try AST with tokens separated, same property name
        propertyNameExprNode = SupportParserHelper.parseEventProperty("a(    'aa'   ). b [ 1 ] . c");
        propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode.getFirstChild());
        assertEquals(PROPERTY, propertyName);
    }

    private void assertIsInvalid(String expression)
    {
        try
        {
            getFilterSpec(expression, null);
            assertTrue(false);
        }
        catch (Exception ex)
        {
            log.debug("Caught expected exception, type= " + ex.getClass().getName() + "  msg=" + ex.getMessage());
        }
    }

    private FilterSpec getFilterSpec(String expressionText, Map<String, EventType> taggedEventTypes) throws Exception
    {
        AST filterNode = parse(expressionText);
        DebugFacility.dumpAST(filterNode);
        return ASTFilterSpecHelper.buildSpec(filterNode, taggedEventTypes, SupportEventAdapterService.getService());
    }

    private String getEventNameTag(String expressionText) throws Exception
    {
        AST filterNode = parse(expressionText);
        return ASTFilterSpecHelper.getEventNameTag(filterNode);
    }

    private AST parse(String expression) throws Exception
    {
        log.debug(".getFilterSpec Parsing expression " + expression);

        AST filterNode = SupportParserHelper.parsePattern(expression);
        return filterNode;
    }

    private static final Log log = LogFactory.getLog(TestASTFilterSpecHelper.class);
}
