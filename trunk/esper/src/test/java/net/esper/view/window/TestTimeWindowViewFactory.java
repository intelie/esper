package net.esper.view.window;

import net.esper.eql.parse.TimePeriodParameter;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.std.SizeView;

import java.util.Arrays;

import junit.framework.TestCase;

public class TestTimeWindowViewFactory extends TestCase
{
    private TimeWindowViewFactory factory;

    public void setUp()
    {
        factory = new TimeWindowViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new TimePeriodParameter(2d), 2000);
        tryParameter(4, 4000);
        tryParameter(3.3d, 3300);
        tryParameter(new Float(1.1f), 1100);

        tryInvalidParameter("price");
        tryInvalidParameter(true);
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(Arrays.asList(new Object[] {1000}));
        assertFalse(factory.canReuse(new SizeView()));
        assertFalse(factory.canReuse(new TimeBatchView(1000, null, null)));
        assertTrue(factory.canReuse(new TimeWindowView(1000000, null)));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            TimeWindowViewFactory factory = new TimeWindowViewFactory();
            factory.setViewParameters(Arrays.asList(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object param, long msec) throws Exception
    {
        TimeWindowViewFactory factory = new TimeWindowViewFactory();
        factory.setViewParameters(Arrays.asList(new Object[] {param}));
        TimeWindowView view = (TimeWindowView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(msec, view.getMillisecondsBeforeExpiry());
    }
}
