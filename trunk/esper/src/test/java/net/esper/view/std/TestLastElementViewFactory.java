package net.esper.view.std;

import junit.framework.TestCase;

import java.util.Arrays;

import net.esper.view.ViewParameterException;
import net.esper.view.window.TimeWindowView;
import net.esper.support.view.SupportViewContextFactory;

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
        assertFalse(factory.canReuse(new SizeView(SupportViewContextFactory.makeContext())));
        assertTrue(factory.canReuse(new LastElementView()));
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
