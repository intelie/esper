package com.espertech.esper.view.window;

import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.std.SizeView;
import junit.framework.TestCase;

public class TestLengthWindowViewFactory extends TestCase
{
    private LengthWindowViewFactory factory;

    public void setUp()
    {
        factory = new LengthWindowViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {10}, 10);

        tryInvalidParameter("price");
        tryInvalidParameter(true);
        tryInvalidParameter(1.1d);
        tryInvalidParameter(0);
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {1000}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new LengthWindowView(factory, 1, null)));
        assertTrue(factory.canReuse(new LengthWindowView(factory, 1000, null)));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {

            factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {param}));
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
        factory.setViewParameters(null, TestViewSupport.toExprList(param));
        LengthWindowView view = (LengthWindowView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(size, view.getSize());
    }
}
