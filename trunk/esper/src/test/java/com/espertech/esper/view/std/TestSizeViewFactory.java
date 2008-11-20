package com.espertech.esper.view.std;

import junit.framework.TestCase;

import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.support.view.SupportStatementContextFactory;

public class TestSizeViewFactory extends TestCase
{
    private SizeViewFactory factory;

    public void setUp()
    {
        factory = new SizeViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {});
        tryInvalidParameter(1.1d);
    }

    public void testCanReuse() throws Exception
    {
        assertFalse(factory.canReuse(new LastElementView()));
        assertTrue(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            SizeViewFactory factory = new SizeViewFactory();
            factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(new Object[] {param}));
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
        factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(param));
        assertTrue(factory.makeView(SupportStatementContextFactory.makeContext()) instanceof SizeView);
    }
}
