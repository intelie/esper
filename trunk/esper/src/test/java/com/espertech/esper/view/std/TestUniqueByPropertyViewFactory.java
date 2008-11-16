package com.espertech.esper.view.std;

import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewParameterException;
import junit.framework.TestCase;

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
        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"a"}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertTrue(factory.canReuse(new UniqueByPropertyView(new String[] {"a"})));
        assertFalse(factory.canReuse(new UniqueByPropertyView(new String[] {"c"})));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            UniqueByPropertyViewFactory factory = new UniqueByPropertyViewFactory();
            factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {param}));
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
        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {param}));
        UniqueByPropertyView view = (UniqueByPropertyView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldName, view.getUniqueFieldNames()[0]);
    }
}
