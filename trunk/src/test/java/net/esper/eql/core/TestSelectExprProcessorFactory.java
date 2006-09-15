package net.esper.eql.core;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.eql.spec.SelectExprElementSpec;
import net.esper.eql.core.SelectExprJoinWildcardProcessor;
import net.esper.eql.core.SelectExprProcessor;
import net.esper.eql.core.SelectExprProcessorFactory;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;

public class TestSelectExprProcessorFactory extends TestCase
{
    public void testGetProcessorInvalid() throws Exception
    {
        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElementSpec(identNode, "result"));
        selectionList.add(new SelectExprElementSpec(mathNode, "result"));

        try
        {
            SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(),
                    null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testGetProcessorWildcard() throws Exception
    {
        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(),
                SupportEventAdapterService.getService());
        assertTrue(processor instanceof SelectExprJoinWildcardProcessor);
    }

    public void testGetProcessorValid() throws Exception
    {
        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectExprElementSpec(identNode, "result"));
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(),
                SupportEventAdapterService.getService());
        assertTrue(processor != null);
    }
}
