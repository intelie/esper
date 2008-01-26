package net.esper.eql.core;

import junit.framework.TestCase;
import net.esper.core.StatementResultService;
import net.esper.core.StatementResultServiceImpl;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.SelectClauseElementCompiled;
import net.esper.eql.spec.SelectClauseElementWildcard;
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
    private StatementResultService statementResultService = new StatementResultServiceImpl(); 

    public void testGetProcessorInvalid() throws Exception
    {
        List<SelectClauseElementCompiled> selectionList = new LinkedList<SelectClauseElementCompiled>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectClauseExprCompiledSpec(identNode, "result"));
        selectionList.add(new SelectClauseExprCompiledSpec(mathNode, "result"));

        try
        {
            SelectExprProcessorFactory.getProcessor(selectionList, false, null,
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
        List<SelectClauseElementCompiled> selectionList = new LinkedList<SelectClauseElementCompiled>();
        selectionList.add(new SelectClauseElementWildcard());
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, false, null,
                new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.getService(), statementResultService);
        assertTrue(processor instanceof SelectExprResultProcessor);
    }

    public void testGetProcessorValid() throws Exception
    {
        List<SelectClauseElementCompiled> selectionList = new LinkedList<SelectClauseElementCompiled>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectClauseExprCompiledSpec(identNode, "result"));
        SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, false, null,
                new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.getService(), statementResultService);
        assertTrue(processor != null);
    }

    public void testVerifyNameUniqueness() throws Exception
    {
        // try valid case
        List<SelectClauseElementCompiled> elements = new LinkedList<SelectClauseElementCompiled>();
        elements.add(new SelectClauseExprCompiledSpec(null, "xx"));
        elements.add(new SelectClauseExprCompiledSpec(null, "yy"));
        elements.add(new SelectClauseStreamCompiledSpec("win", null));
        elements.add(new SelectClauseStreamCompiledSpec("s2", "abc"));

        SelectExprProcessorFactory.verifyNameUniqueness(elements);

        // try invalid case
        elements.add(new SelectClauseExprCompiledSpec(null, "yy"));
        try
        {
            SelectExprProcessorFactory.verifyNameUniqueness(elements);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }

        // try invalid case
        elements.clear();
        elements.add(new SelectClauseExprCompiledSpec(null, "abc"));
        elements.add(new SelectClauseStreamCompiledSpec("s0", "abc"));
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
