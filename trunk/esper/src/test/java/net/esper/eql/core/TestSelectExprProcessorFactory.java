package net.esper.eql.core;

import junit.framework.TestCase;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.SelectClauseExprCompiledSpec;
import net.esper.eql.spec.SelectClauseStreamCompiledSpec;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestSelectExprProcessorFactory extends TestCase
{
    private List<SelectClauseStreamCompiledSpec> listOfStreamsSelected = new ArrayList<SelectClauseStreamCompiledSpec>();

    public void testGetProcessorInvalid() throws Exception
    {
        List<SelectClauseExprCompiledSpec> selectionList = new LinkedList<SelectClauseExprCompiledSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectClauseExprCompiledSpec(identNode, "result"));
        selectionList.add(new SelectClauseExprCompiledSpec(mathNode, "result"));

        try
        {
            SelectExprProcessorFactory.getProcessor(selectionList, listOfStreamsSelected, false, null, null,
                    new SupportStreamTypeSvc3Stream(), null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testGetProcessorWildcard() throws Exception
    {
        List<SelectClauseExprCompiledSpec> selectionList = new LinkedList<SelectClauseExprCompiledSpec>();
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, listOfStreamsSelected, false, null, null,
                new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.getService(), null);
        assertTrue(processor instanceof SelectExprJoinWildcardProcessor);
    }

    public void testGetProcessorValid() throws Exception
    {
        List<SelectClauseExprCompiledSpec> selectionList = new LinkedList<SelectClauseExprCompiledSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectClauseExprCompiledSpec(identNode, "result"));
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, listOfStreamsSelected, false, null, null,
                new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.getService(), null);
        assertTrue(processor != null);
    }

    public void testVerifyNameUniqueness() throws Exception
    {
        // try valid case
        List<SelectClauseExprCompiledSpec> elements = new LinkedList<SelectClauseExprCompiledSpec>();
        elements.add(new SelectClauseExprCompiledSpec(null, "xx"));
        elements.add(new SelectClauseExprCompiledSpec(null, "yy"));

        List<SelectClauseStreamCompiledSpec> streams = new LinkedList<SelectClauseStreamCompiledSpec>();
        streams.add(new SelectClauseStreamCompiledSpec("win", null));
        streams.add(new SelectClauseStreamCompiledSpec("s2", "abc"));

        SelectExprProcessorFactory.verifyNameUniqueness(elements, streams);

        // try invalid case
        elements.add(new SelectClauseExprCompiledSpec(null, "yy"));
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
        streams.add(new SelectClauseStreamCompiledSpec("s0", "abc"));
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
