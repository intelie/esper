package net.esper.view.std;

import junit.framework.TestCase;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.view.ViewParameterException;

import java.util.Arrays;

public class TestUniqueByPropertyViewFactory extends TestCase
{
    private UniqueByPropertyViewFactory factory;

    public void setUp()
    {
        factory = new UniqueByPropertyViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter("price", "price");
        tryInvalidParameter(1.1d);
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, Arrays.asList(new Object[] {"a"}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertTrue(factory.canReuse(new UniqueByPropertyView("a")));
        assertFalse(factory.canReuse(new UniqueByPropertyView("c")));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            UniqueByPropertyViewFactory factory = new UniqueByPropertyViewFactory();
            factory.setViewParameters(null, Arrays.asList(new Object[] {param}));
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
        factory.setViewParameters(null, Arrays.asList(new Object[] {param}));
        UniqueByPropertyView view = (UniqueByPropertyView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldName, view.getUniqueFieldName());
    }
}
