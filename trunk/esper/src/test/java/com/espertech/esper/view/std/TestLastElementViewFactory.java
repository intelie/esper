package com.espertech.esper.view.std;

import junit.framework.TestCase;

import java.util.Arrays;

import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.support.view.SupportStatementContextFactory;

public class TestLastElementViewFactory extends TestCase
{
    private LastElementViewFactory factory;

    public void setUp()
    {
        factory = new LastElementViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {});
        tryInvalidParameter(1.1d);
    }

    public void testCanReuse() throws Exception
    {
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertTrue(factory.canReuse(new LastElementView()));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            LastElementViewFactory factory = new LastElementViewFactory();
            factory.setViewParameters(null, Arrays.asList(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] param) throws Exception
    {
        LastElementViewFactory factory = new LastElementViewFactory();
        factory.setViewParameters(null, Arrays.asList(param));
        assertTrue(factory.makeView(SupportStatementContextFactory.makeContext()) instanceof LastElementView);
    }
}
