package net.esper.view.window;

import junit.framework.TestCase;
import net.esper.eql.parse.TimePeriodParameter;
import net.esper.view.factory.ViewParameterException;
import net.esper.support.view.SupportViewContextFactory;

import java.util.Arrays;

public class TestLengthWindowViewFactory extends TestCase
{
    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {10}, 10);

        tryInvalidParameter("price");
        tryInvalidParameter(true);
        tryInvalidParameter(1.1d);
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            LengthWindowViewFactory factory = new LengthWindowViewFactory();
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
