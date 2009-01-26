package com.espertech.esper.view.internal;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportViewResourceCallback;
import com.espertech.esper.view.ViewCapPriorEventAccess;
import com.espertech.esper.view.ViewParameterException;

import java.util.Arrays;

public class TestPriorEventViewFactory extends TestCase
{
    private PriorEventViewFactory factoryOne;
    private PriorEventViewFactory factoryTwo;
    private SupportViewResourceCallback callbackOne;
    private SupportViewResourceCallback callbackTwo;

    public void setUp() throws Exception
    {
        factoryOne = new PriorEventViewFactory();
        factoryOne.setViewParameters(null, Arrays.asList((Object)(Boolean)false));
        factoryTwo = new PriorEventViewFactory();
        factoryTwo.setViewParameters(null, Arrays.asList((Object)(Boolean)true));
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
            factoryOne.setViewParameters(null, Arrays.asList());
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    public void testCanReuse() throws Exception
    {
        assertFalse(factoryOne.canReuse(null));
    }
}
