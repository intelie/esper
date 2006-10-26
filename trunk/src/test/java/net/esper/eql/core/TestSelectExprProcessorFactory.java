package net.esper.eql.core;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.eql.spec.SelectExprElementNamedSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;

public class TestSelectExprProcessorFactory extends TestCase
{
    public void testGetProcessorInvalid() throws Exception
    {
        List<SelectExprElementNamedSpec> selectionList = new LinkedList<SelectExprElementNamedSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElementNamedSpec(identNode, "result"));
        selectionList.add(new SelectExprElementNamedSpec(mathNode, "result"));

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
        List<SelectExprElementNamedSpec> selectionList = new LinkedList<SelectExprElementNamedSpec>();
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(),
                SupportEventAdapterService.getService());
        assertTrue(processor instanceof SelectExprJoinWildcardProcessor);
    }

    public void testGetProcessorValid() throws Exception
    {
        List<SelectExprElementNamedSpec> selectionList = new LinkedList<SelectExprElementNamedSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectExprElementNamedSpec(identNode, "result"));
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(),
                SupportEventAdapterService.getService());
        assertTrue(processor != null);
    }

    public void testVerifyNameUniqueness() throws Exception
    {
        // try valid case
        List<SelectExprElementNamedSpec> elements = new LinkedList<SelectExprElementNamedSpec>();
        elements.add(new SelectExprElementNamedSpec(null, "xx"));
        elements.add(new SelectExprElementNamedSpec(null, "yy"));

        SelectExprProcessorFactory.verifyNameUniqueness(elements);

        // try invalid case
        elements.add(new SelectExprElementNamedSpec(null, "yy"));
        try
        {
            SelectExprProcessorFactory.verifyNameUniqueness(elements);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
