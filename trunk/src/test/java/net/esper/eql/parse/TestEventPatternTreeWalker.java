package net.esper.eql.parse;

import junit.framework.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import antlr.collections.AST;
import net.esper.support.eql.parse.SupportParserHelper;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_N;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.pattern.*;

public class TestEventPatternTreeWalker extends TestCase
{
    public void testDisplayAST() throws Exception
    {
        String expression = "timer:at(2, */9, 1:5, *, [1,4], [1, 2:5, */3])";

        log.debug(".testDisplayAST parsing: " + expression);

        AST ast = SupportParserHelper.parsePattern(expression);
        log.debug(".parseAndWalk success, tree walking...");
        SupportParserHelper.displayAST(ast);

        log.debug(".testDisplayAST tree walking: " + expression);
        EQLPatternTreeWalker walker = new EQLPatternTreeWalker(null);
        walker.startPatternExpressionRule(ast);
    }

    public void testWalkPattern() throws Exception
    {
        String text = "every g=" + SupportBean.class.getName() + "(string=\"IBM\") where timer:within(20)";

        EQLPatternTreeWalker walker = parseAndWalk(text);

        EvalRootNode rootNode = walker.getRootNode();
        rootNode.dumpDebug(".testWalk ");

        assertEquals(1, rootNode.getChildNodes().size());
        assertTrue(rootNode.getChildNodes().get(0) instanceof EvalEveryNode);
        EvalNode everyNode = rootNode.getChildNodes().get(0);

        assertEquals(1, everyNode.getChildNodes().size());
        assertTrue(everyNode.getChildNodes().get(0) instanceof EvalGuardNode);
        EvalGuardNode guardNode = (EvalGuardNode) everyNode.getChildNodes().get(0);

        assertEquals(1, guardNode.getChildNodes().size());
        assertTrue(guardNode.getChildNodes().get(0) instanceof EvalFilterNode);
        EvalFilterNode filterNode = (EvalFilterNode) guardNode.getChildNodes().get(0);

        assertEquals("g", filterNode.getEventAsName());
        assertEquals(0, filterNode.getChildNodes().size());
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());

        assertEquals(1, walker.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, walker.getTaggedEventTypes().get("g").getUnderlyingType());
    }

    public void testWalkPropertyCombination() throws Exception
    {
        final String EVENT = SupportBeanComplexProps.class.getName();
        String property = tryWalkGetProperty(EVENT + "(mapped ( 'key' )  = 'value')");
        assertEquals("mapped('key')", property);

        property = tryWalkGetProperty(EVENT + "(indexed [ 1 ]  = 1)");
        assertEquals("indexed[1]", property);
        property = tryWalkGetProperty(EVENT + "(nested . nestedValue  = 'value')");
        assertEquals("nested.nestedValue", property);
    }

    public void testWalkPatternUseResult() throws Exception
    {
        final String EVENT = SupportBean_N.class.getName();
        String text = "na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive in [0:na.doublePrimitive])";
        parseAndWalk(text);
    }

    public void testWalkPatternNoPackage() throws Exception
    {
        SupportEventAdapterService.getService().addBeanType("SupportBean_N", SupportBean_N.class);
        String text = "na=SupportBean_N()";
        parseAndWalk(text);
    }

    public void testWalkPatternTypesValid() throws Exception
    {
        String text = SupportBean.class.getName();

        EQLPatternTreeWalker walker = parseAndWalk(text);
        assertEquals(0, walker.getTaggedEventTypes().size());
    }

    public void testPatternWalkTypesInvalid() throws Exception
    {
        String text = "a=" + SupportBean.class.getName() + " or a=" + SupportBean_A.class.getName();

        try
        {
            parseAndWalk(text);
            TestCase.fail();
        }
        catch (Exception ex)
        {
            log.debug(".testWalkTypesInvalid Expected exception, msg=" + ex.getMessage());
        }
    }

    private String tryWalkGetProperty(String stmt) throws Exception
    {
        EQLPatternTreeWalker walker = parseAndWalk(stmt);
        EvalRootNode rootNode = walker.getRootNode();
        rootNode.dumpDebug(".testWalk ");

        EvalFilterNode filterNode = (EvalFilterNode) rootNode.getChildNodes().get(0);
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());
        return filterNode.getFilterSpec().getParameters().get(0).getPropertyName();
    }

    private static EQLPatternTreeWalker parseAndWalk(String expression) throws Exception
    {
        log.debug(".parseAndWalk Trying text=" + expression);
        AST ast = SupportParserHelper.parsePattern(expression);
        log.debug(".parseAndWalk success, tree walking...");
        SupportParserHelper.displayAST(ast);

        EQLPatternTreeWalker walker = new EQLPatternTreeWalker(SupportEventAdapterService.getService());
        walker.startPatternExpressionRule(ast);
        return walker;
    }

    private static final Log log = LogFactory.getLog(TestEventPatternTreeWalker.class);
}