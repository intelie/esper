package net.esper.view.internal;

import junit.framework.TestCase;
import net.esper.view.ViewCapPriorEventAccess;
import net.esper.view.internal.PriorEventBufferUnbound;
import net.esper.view.internal.PriorEventBufferMulti;
import net.esper.support.eql.SupportViewResourceCallback;

public class TestPriorEventViewFactory extends TestCase
{
    private PriorEventViewFactory factoryOne;
    private PriorEventViewFactory factoryTwo;
    private SupportViewResourceCallback callbackOne;
    private SupportViewResourceCallback callbackTwo;

    public void setUp()
    {
        factoryOne = new PriorEventViewFactory(false);
        factoryTwo = new PriorEventViewFactory(true);
        callbackOne = new SupportViewResourceCallback();
        callbackTwo = new SupportViewResourceCallback();
    }

    public void testMakeView() throws Exception
    {
        factoryOne.setProvideCapability(new ViewCapPriorEventAccess(1), callbackOne);
        factoryOne.setProvideCapability(new ViewCapPriorEventAccess(2), callbackOne);
        PriorEventView view = (PriorEventView) factoryOne.makeView(null);
        assertTrue(view.getBuffer() instanceof PriorEventBufferMulti);
        assertEquals(2, callbackOne.getResources().size());

        factoryTwo.setProvideCapability(new ViewCapPriorEventAccess(1), callbackTwo);
        view = (PriorEventView) factoryTwo.makeView(null);
        assertTrue(view.getBuffer() instanceof PriorEventBufferUnbound);
        assertEquals(1, callbackTwo.getResources().size());
    }

    public void testSetParameters() throws Exception
    {
        try
        {
            factoryOne.setViewParameters(null, null);
            fail();
        }
        catch (UnsupportedOperationException ex)
        {
            // expected
        }
    }

    public void testCanReuse() throws Exception
    {
        assertFalse(factoryOne.canReuse(null));
    }
}
