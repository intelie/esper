package net.esper.view.std;

import junit.framework.TestCase;

import java.util.Arrays;

import net.esper.view.factory.ViewParameterException;
import net.esper.support.view.SupportViewContextFactory;

public class TestSizeViewFactory extends TestCase
{
    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {});
        tryInvalidParameter(1.1d);
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
