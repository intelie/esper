package net.esper.view.window;

import junit.framework.TestCase;
import net.esper.view.ViewParameterException;
import net.esper.view.std.SizeView;
import net.esper.support.view.SupportViewContextFactory;

import java.util.Arrays;

public class TestLengthWindowViewFactory extends TestCase
{
    private LengthWindowViewFactory factory;

    public void setUp()
    {
        factory = new LengthWindowViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {10}, 10);

        tryInvalidParameter("price");
        tryInvalidParameter(true);
        tryInvalidParameter(1.1d);
        tryInvalidParameter(0);
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(Arrays.asList(new Object[] {1000}));
        assertFalse(factory.canReuse(new SizeView(SupportViewContextFactory.makeContext())));
        assertFalse(factory.canReuse(new LengthWindowView(factory, 1, null)));
        assertTrue(factory.canReuse(new LengthWindowView(factory, 1000, null)));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {

            factory.setViewParameters(Arrays.asList(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] param, int size) throws Exception
    {
        LengthWindowViewFactory factory = new LengthWindowViewFactory();
        factory.setViewParameters(Arrays.asList(param));
        LengthWindowView view = (LengthWindowView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(size, view.getSize());
    }
}
