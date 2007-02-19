package net.esper.view.window;

import junit.framework.TestCase;

import java.util.Arrays;

import net.esper.view.std.SizeView;
import net.esper.view.ViewParameterException;
import net.esper.support.view.SupportViewContextFactory;

public class TestLengthBatchViewFactory extends TestCase
{
    private LengthBatchViewFactory factory;

    public void setUp()
    {
        factory = new LengthBatchViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {Short.parseShort("10")}, 10);
        tryParameter(new Object[] {100}, 100);

        tryInvalidParameter("price");
        tryInvalidParameter(true);
        tryInvalidParameter(1.1d);
        tryInvalidParameter(0);
        tryInvalidParameter(1000L);
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(Arrays.asList(new Object[] {1000}));
        assertFalse(factory.canReuse(new SizeView(SupportViewContextFactory.makeContext())));
        assertFalse(factory.canReuse(new LengthBatchView(factory, 1, null)));
        assertTrue(factory.canReuse(new LengthBatchView(factory, 1000, null)));
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
        LengthBatchViewFactory factory = new LengthBatchViewFactory();
        factory.setViewParameters(Arrays.asList(param));
        LengthBatchView view = (LengthBatchView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(size, view.getSize());
    }
}
