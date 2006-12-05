package net.esper.view.std;

import junit.framework.TestCase;

import java.util.Arrays;

import net.esper.view.factory.ViewParameterException;
import net.esper.support.view.SupportViewContextFactory;

public class TestLastElementViewFactory extends TestCase
{
    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {});
        tryInvalidParameter(1.1d);
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            LastElementViewFactory factory = new LastElementViewFactory();
            factory.setViewParameters(Arrays.asList(new Object[] {param}));
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
        factory.setViewParameters(Arrays.asList(param));
        assertTrue(factory.makeView(SupportViewContextFactory.makeContext()) instanceof LastElementView);
    }
}
