package net.esper.view.std;

import junit.framework.TestCase;

import java.util.Arrays;

import net.esper.view.ViewParameterException;
import net.esper.view.window.TimeWindowView;
import net.esper.support.view.SupportViewContextFactory;

public class TestSizeViewFactory extends TestCase
{
    private SizeViewFactory factory;

    public void setUp()
    {
        factory = new SizeViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {});
        tryInvalidParameter(1.1d);
    }

    public void testCanReuse() throws Exception
    {
        assertFalse(factory.canReuse(new LastElementView()));
        assertTrue(factory.canReuse(new SizeView()));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            SizeViewFactory factory = new SizeViewFactory();
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
        SizeViewFactory factory = new SizeViewFactory();
        factory.setViewParameters(Arrays.asList(param));
        assertTrue(factory.makeView(SupportViewContextFactory.makeContext()) instanceof SizeView);
    }
}
