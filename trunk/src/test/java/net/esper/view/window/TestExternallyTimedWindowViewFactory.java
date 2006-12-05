package net.esper.view.window;

import junit.framework.TestCase;
import net.esper.eql.parse.TimePeriodParameter;
import net.esper.view.factory.ViewParameterException;
import net.esper.support.view.SupportViewContextFactory;

import java.util.Arrays;

public class TestExternallyTimedWindowViewFactory extends TestCase
{
    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"a", new TimePeriodParameter(2d)}, "a", 2000);
        tryParameter(new Object[] {"a", 10L}, "a", 10000);
        tryParameter(new Object[] {"a", 11}, "a", 11000);
        tryParameter(new Object[] {"a", 2.2}, "a", 2200);

        tryInvalidParameter(new Object[] {new TimePeriodParameter(2d)});
        tryInvalidParameter(new Object[] {"a"});
    }

    private void tryInvalidParameter(Object[] param) throws Exception
    {
        try
        {
            ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
            factory.setViewParameters(Arrays.asList(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String fieldName, long msec) throws Exception
    {
        ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
        factory.setViewParameters(Arrays.asList(params));
        ExternallyTimedWindowView view = (ExternallyTimedWindowView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(fieldName, view.getTimestampFieldName());
        assertEquals(msec, view.getMillisecondsBeforeExpiry());
    }
}
