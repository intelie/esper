package com.espertech.esper.view.std;

import com.espertech.esper.client.EventType;
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
    }

    public void testCanReuse() throws Exception
    {
        assertFalse(factory.canReuse(new LastElementView()));
        EventType type = SizeView.createEventType(SupportStatementContextFactory.makeContext(), null);
        assertTrue(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext(), type, null)));
    }

    private void tryParameter(Object[] param) throws Exception
    {
        SizeViewFactory factory = new SizeViewFactory();
        factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(param));
        assertTrue(factory.makeView(SupportStatementContextFactory.makeContext()) instanceof SizeView);
    }
}
