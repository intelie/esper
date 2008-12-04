package com.espertech.esper.view.window;

import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.std.FirstElementView;
import junit.framework.TestCase;

public class TestLengthBatchViewFactory extends TestCase
{
    private LengthBatchViewFactory factory;

    public void setUp()
    {
        factory = new LengthBatchViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {Short.parseShort("10")}, 10);
        tryParameter(new Object[] {100}, 100);

        tryInvalidParameter("string");
        tryInvalidParameter(true);
        tryInvalidParameter(1.1d);
        tryInvalidParameter(0);
        tryInvalidParameter(1000L);
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(new Object[] {1000}));
        assertFalse(factory.canReuse(new FirstElementView()));
        assertFalse(factory.canReuse(new LengthBatchView(factory, 1, null)));
        assertTrue(factory.canReuse(new LengthBatchView(factory, 1000, null)));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] param, int size) throws Exception
    {
        LengthBatchViewFactory factory = new LengthBatchViewFactory();
        factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(param));
        LengthBatchView view = (LengthBatchView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(size, view.getSize());
    }
}
