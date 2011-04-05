package com.espertech.esper.epl.spec;

import com.espertech.esper.pattern.EvalNodeUtil;
import junit.framework.TestCase;
import com.espertech.esper.epl.parse.EPLTreeWalker;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.filter.*;
import com.espertech.esper.pattern.EvalFilterNode;
import com.espertech.esper.pattern.EvalNode;
import com.espertech.esper.pattern.EvalNodeAnalysisResult;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.parse.SupportParserHelper;
import com.espertech.esper.support.epl.parse.SupportEPLTreeWalkerFactory;
import com.espertech.esper.support.view.SupportStatementContextFactory;

import java.util.List;
import java.util.HashSet;

import org.antlr.runtime.tree.Tree;

public class TestPatternStreamSpecRaw extends TestCase
{
    public void testPatternEquals() throws Exception
    {
        String text = "select * from pattern [" +
                "s=" + SupportBean.class.getName() + "(intPrimitive=5) -> " +
                "t=" + SupportBean.class.getName() + "(intPrimitive=s.intBoxed)" +
                "]";
        tryPatternEquals(text);

        text = "select * from pattern [" +
                "s=" + SupportBean.class.getName() + "(5=intPrimitive) -> " +
                "t=" + SupportBean.class.getName() + "(s.intBoxed=intPrimitive)" +
                "]";
        tryPatternEquals(text);
    }

    public void testInvalid() throws Exception
    {
        String text = "select * from pattern [" +
                "s=" + SupportBean.class.getName() + " -> " +
                "t=" + SupportBean.class.getName() + "(intPrimitive=s.doubleBoxed)" +
                "]";
        tryInvalid(text);

        text = "select * from pattern [" +
                "s=" + SupportBean.class.getName() + " -> " +
                "t=" + SupportBean.class.getName() + "(intPrimitive in (s.doubleBoxed))" +
                "]";
        tryInvalid(text);
    }

    private void tryInvalid(String text) throws Exception
    {
        try
        {
            PatternStreamSpecRaw raw = makeSpec(text);
            compile(raw);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }

    public void testPatternExpressions() throws Exception
    {
        String text = "select * from pattern [" +
                "s=" + SupportBean.class.getName() + "(intPrimitive in (s.intBoxed + 1, 0), intBoxed+1=intPrimitive-1)" +
                "]";

        PatternStreamSpecRaw raw = makeSpec(text);
        PatternStreamSpecCompiled spec = compile(raw);
        assertEquals(1, spec.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, spec.getTaggedEventTypes().get("s").getFirst().getUnderlyingType());

        EvalNodeAnalysisResult evalNodeAnalysisResult = EvalNodeUtil.recursiveAnalyzeChildNodes(spec.getEvalNode());
        List<EvalFilterNode> filters = evalNodeAnalysisResult.getFilterNodes();
        assertEquals(1, filters.size());

        // node 0
        EvalFilterNode filterNode = filters.get(0);
        assertEquals(SupportBean.class, filterNode.getFilterSpec().getFilterForEventType().getUnderlyingType());
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());
        FilterSpecParamExprNode exprParam = (FilterSpecParamExprNode) filterNode.getFilterSpec().getParameters().get(0);   
    }

    public void testPatternInSetOfVal() throws Exception
    {
        String text = "select * from pattern [" +
                "s=" + SupportBean.class.getName() + " -> " +
                       SupportBean.class.getName() + "(intPrimitive in (s.intBoxed, 0))" +
                "]";

        PatternStreamSpecRaw raw = makeSpec(text);
        PatternStreamSpecCompiled spec = compile(raw);
        assertEquals(1, spec.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, spec.getTaggedEventTypes().get("s").getFirst().getUnderlyingType());

        EvalNodeAnalysisResult evalNodeAnalysisResult = EvalNodeUtil.recursiveAnalyzeChildNodes(spec.getEvalNode());
        List<EvalFilterNode> filters = evalNodeAnalysisResult.getFilterNodes();
        assertEquals(2, filters.size());

        // node 0
        EvalFilterNode filterNode = filters.get(0);
        assertEquals(SupportBean.class, filterNode.getFilterSpec().getFilterForEventType().getUnderlyingType());
        assertEquals(0, filterNode.getFilterSpec().getParameters().size());

        // node 1
        filterNode = filters.get(1);
        assertEquals(SupportBean.class, filterNode.getFilterSpec().getFilterForEventType().getUnderlyingType());
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());

        FilterSpecParamIn inlist = (FilterSpecParamIn) filterNode.getFilterSpec().getParameters().get(0);
        assertEquals(FilterOperator.IN_LIST_OF_VALUES, inlist.getFilterOperator());
        assertEquals(2, inlist.getListOfValues().size());

        // in-value 1
        InSetOfValuesEventProp prop = (InSetOfValuesEventProp) inlist.getListOfValues().get(0);
        assertEquals("s", prop.getResultEventAsName());
        assertEquals("intBoxed", prop.getResultEventProperty());

        // in-value 1
        InSetOfValuesConstant constant = (InSetOfValuesConstant) inlist.getListOfValues().get(1);
        assertEquals(0, constant.getConstant());
    }

    public void testRange() throws Exception
    {
        String text = "select * from pattern [" +
                "s=" + SupportBean.class.getName() + " -> " +
                       SupportBean.class.getName() + "(intPrimitive between s.intBoxed and 100)" +
                "]";

        PatternStreamSpecRaw raw = makeSpec(text);
        PatternStreamSpecCompiled spec = compile(raw);
        assertEquals(1, spec.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, spec.getTaggedEventTypes().get("s").getFirst().getUnderlyingType());

        EvalNodeAnalysisResult evalNodeAnalysisResult = EvalNodeUtil.recursiveAnalyzeChildNodes(spec.getEvalNode());
        List<EvalFilterNode> filters = evalNodeAnalysisResult.getFilterNodes();
        assertEquals(2, filters.size());

        // node 0
        EvalFilterNode filterNode = filters.get(0);
        assertEquals(SupportBean.class, filterNode.getFilterSpec().getFilterForEventType().getUnderlyingType());
        assertEquals(0, filterNode.getFilterSpec().getParameters().size());

        // node 1
        filterNode = filters.get(1);
        assertEquals(SupportBean.class, filterNode.getFilterSpec().getFilterForEventType().getUnderlyingType());
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());

        FilterSpecParamRange range = (FilterSpecParamRange) filterNode.getFilterSpec().getParameters().get(0);
        assertEquals(FilterOperator.RANGE_CLOSED, range.getFilterOperator());

        // min-value
        RangeValueEventProp prop = (RangeValueEventProp) range.getMin();
        assertEquals("s", prop.getResultEventAsName());
        assertEquals("intBoxed", prop.getResultEventProperty());

        // max-value
        RangeValueDouble constant = (RangeValueDouble) range.getMax();
        assertEquals(100d, constant.getDoubleValue());
    }

    private void tryPatternEquals(String text) throws Exception
    {
        PatternStreamSpecRaw raw = makeSpec(text);
        PatternStreamSpecCompiled spec = compile(raw);
        assertEquals(2, spec.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, spec.getTaggedEventTypes().get("s").getFirst().getUnderlyingType());
        assertEquals(SupportBean.class, spec.getTaggedEventTypes().get("t").getFirst().getUnderlyingType());

        EvalNodeAnalysisResult evalNodeAnalysisResult = EvalNodeUtil.recursiveAnalyzeChildNodes(spec.getEvalNode());
        List<EvalFilterNode> filters = evalNodeAnalysisResult.getFilterNodes();
        assertEquals(2, filters.size());

        // node 0
        EvalFilterNode filterNode = filters.get(0);
        assertEquals(SupportBean.class, filterNode.getFilterSpec().getFilterForEventType().getUnderlyingType());
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());

        FilterSpecParamConstant constant = (FilterSpecParamConstant) filterNode.getFilterSpec().getParameters().get(0);
        assertEquals(FilterOperator.EQUAL, constant.getFilterOperator());
        assertEquals("intPrimitive", constant.getPropertyName());
        assertEquals(5, constant.getFilterConstant());

        // node 1
        filterNode = filters.get(1);
        assertEquals(SupportBean.class, filterNode.getFilterSpec().getFilterForEventType().getUnderlyingType());
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());

        FilterSpecParamEventProp eventprop = (FilterSpecParamEventProp) filterNode.getFilterSpec().getParameters().get(0);
        assertEquals(FilterOperator.EQUAL, constant.getFilterOperator());
        assertEquals("intPrimitive", constant.getPropertyName());
        assertEquals("s", eventprop.getResultEventAsName());
        assertEquals("intBoxed", eventprop.getResultEventProperty());
    }

    private PatternStreamSpecCompiled compile(PatternStreamSpecRaw raw) throws Exception
    {
        PatternStreamSpecCompiled compiled = (PatternStreamSpecCompiled) raw.compile(SupportStatementContextFactory.makeContext(), new HashSet<String>(), false);
        return compiled;
    }

    private static PatternStreamSpecRaw makeSpec(String expression) throws Exception
    {
        Tree ast = SupportParserHelper.parseEPL(expression);
        SupportParserHelper.displayAST(ast);

        EPLTreeWalker walker = SupportEPLTreeWalkerFactory.makeWalker(ast);
        walker.startEPLExpressionRule();

        PatternStreamSpecRaw spec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);
        return spec;
    }
}
