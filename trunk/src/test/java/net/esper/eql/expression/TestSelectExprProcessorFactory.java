package net.esper.eql.expression;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;

public class TestSelectExprProcessorFactory extends TestCase
{
    public void testGetProcessorInvalid() throws Exception
    {
        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElement(identNode, "result"));
        selectionList.add(new SelectExprElement(mathNode, "result"));

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
        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(),
                SupportEventAdapterService.getService());
        assertTrue(processor instanceof SelectExprJoinWildcardProcessor);
    }

    public void testGetProcessorValid() throws Exception
    {
        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectExprElement(identNode, "result"));
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(),
                SupportEventAdapterService.getService());
        assertTrue(processor != null);
    }
}
