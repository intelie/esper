package net.esper.view.std;

import net.esper.view.factory.ViewParameterException;
import net.esper.support.view.SupportViewContextFactory;
import java.util.Arrays;

import junit.framework.TestCase;

public class TestUniqueByPropertyViewFactory extends TestCase
{
    public void testSetParameters() throws Exception
    {
        tryParameter("price", "price");
        tryInvalidParameter(1.1d);
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            UniqueByPropertyViewFactory factory = new UniqueByPropertyViewFactory();
            factory.setViewParameters(Arrays.asList(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object param, String fieldName) throws Exception
    {
        UniqueByPropertyViewFactory factory = new UniqueByPropertyViewFactory();
        factory.setViewParameters(Arrays.asList(new Object[] {param}));
        UniqueByPropertyView view = (UniqueByPropertyView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(fieldName, view.getUniqueFieldName());
    }
}
