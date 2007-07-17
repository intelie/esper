package net.esper.view.window;

import junit.framework.TestCase;
import net.esper.eql.parse.TimePeriodParameter;
import net.esper.view.ViewParameterException;
import net.esper.view.std.SizeView;
import net.esper.support.view.SupportStatementContextFactory;

import java.util.Arrays;

public class TestTimeBatchViewFactory extends TestCase
{
    private TimeBatchViewFactory factory;

    public void setUp()
    {
        factory = new TimeBatchViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {new TimePeriodParameter(2d)}, 2000, null);
        tryParameter(new Object[] {4}, 4000, null);
        tryParameter(new Object[] {3.3d}, 3300, null);
        tryParameter(new Object[] {new Float(1.1f)}, 1100, null);
        tryParameter(new Object[] {99.9d, 364466464L}, 99900, 364466464L);

        tryInvalidParameter("price");
        tryInvalidParameter(true);
        tryInvalidParameter(0);
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, Arrays.asList(new Object[] {1000}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new TimeBatchView(factory, SupportStatementContextFactory.makeContext(), 1, null, null)));
        assertTrue(factory.canReuse(new TimeBatchView(factory, SupportStatementContextFactory.makeContext(), 1000000, null, null)));

        factory.setViewParameters(null, Arrays.asList(new Object[] {1000, 2000L}));
        assertFalse(factory.canReuse(new TimeBatchView(factory, SupportStatementContextFactory.makeContext(), 1, null, null)));
        assertTrue(factory.canReuse(new TimeBatchView(factory, SupportStatementContextFactory.makeContext(), 1000000, 2000L, null)));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            TimeBatchViewFactory factory = new TimeBatchViewFactory();
            factory.setViewParameters(null, Arrays.asList(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] param, long msec, Long referencePoint) throws Exception
    {
        TimeBatchViewFactory factory = new TimeBatchViewFactory();
        factory.setViewParameters(null, Arrays.asList(param));
        TimeBatchView view = (TimeBatchView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(msec, view.getMsecIntervalSize());
        assertEquals(referencePoint, view.getInitialReferencePoint());
    }
}
