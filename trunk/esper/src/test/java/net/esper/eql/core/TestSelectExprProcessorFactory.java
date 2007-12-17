package net.esper.eql.core;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.eql.spec.SelectExprElementStreamRawSpec;
import net.esper.eql.spec.SelectExprElementStreamCompiledSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;

public class TestSelectExprProcessorFactory extends TestCase
{
    private List<SelectExprElementStreamCompiledSpec> listOfStreamsSelected = new ArrayList<SelectExprElementStreamCompiledSpec>();

    public void testGetProcessorInvalid() throws Exception
    {
        List<SelectExprElementCompiledSpec> selectionList = new LinkedList<SelectExprElementCompiledSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElementCompiledSpec(identNode, "result"));
        selectionList.add(new SelectExprElementCompiledSpec(mathNode, "result"));

        try
        {
            SelectExprProcessorFactory.getProcessor(selectionList, listOfStreamsSelected, false, null,
                    new SupportStreamTypeSvc3Stream(), null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testGetProcessorWildcard() throws Exception
    {
        List<SelectExprElementCompiledSpec> selectionList = new LinkedList<SelectExprElementCompiledSpec>();
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, listOfStreamsSelected, false, null,
                new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.getService());
        assertTrue(processor instanceof SelectExprJoinWildcardProcessor);
    }

    public void testGetProcessorValid() throws Exception
    {
        List<SelectExprElementCompiledSpec> selectionList = new LinkedList<SelectExprElementCompiledSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectExprElementCompiledSpec(identNode, "result"));
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, listOfStreamsSelected, false, null,
                new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.getService());
        assertTrue(processor != null);
    }

    public void testVerifyNameUniqueness() throws Exception
    {
        // try valid case
        List<SelectExprElementCompiledSpec> elements = new LinkedList<SelectExprElementCompiledSpec>();
        elements.add(new SelectExprElementCompiledSpec(null, "xx"));
        elements.add(new SelectExprElementCompiledSpec(null, "yy"));

        List<SelectExprElementStreamCompiledSpec> streams = new LinkedList<SelectExprElementStreamCompiledSpec>();
        streams.add(new SelectExprElementStreamCompiledSpec("win", null, 1, false));
        streams.add(new SelectExprElementStreamCompiledSpec("s2", "abc", 2, false));

        SelectExprProcessorFactory.verifyNameUniqueness(elements, streams);

        // try invalid case
        elements.add(new SelectExprElementCompiledSpec(null, "yy"));
        try
        {
            SelectExprProcessorFactory.verifyNameUniqueness(elements, streams);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }

        // try invalid case
        elements.clear();
        streams.add(new SelectExprElementStreamCompiledSpec("s0", "abc", 2, false));
        try
        {
            SelectExprProcessorFactory.verifyNameUniqueness(elements, streams);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
