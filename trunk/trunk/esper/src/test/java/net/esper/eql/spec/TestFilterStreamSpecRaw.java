package net.esper.eql.spec;

import antlr.collections.AST;
import junit.framework.TestCase;
import net.esper.eql.core.AutoImportServiceImpl;
import net.esper.eql.parse.EQLTreeWalker;
import net.esper.filter.FilterSpec;
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
    }

    public void testEquals() throws Exception
    {
        FilterStreamSpecRaw raw = makeSpec("select * from " + SupportBean.class.getName() + "(intPrimitive=5)");
        FilterSpec spec = compile(raw);
        assertEquals(1, spec.getParameters().size());
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
