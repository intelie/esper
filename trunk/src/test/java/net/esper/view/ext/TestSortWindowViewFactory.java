package net.esper.view.ext;

import junit.framework.TestCase;
import net.esper.view.factory.ViewAttachException;
import net.esper.view.factory.ViewParameterException;
import net.esper.event.EventType;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.util.ArrayAssertionUtil;

import java.util.Arrays;

public class TestSortWindowViewFactory extends TestCase
{
    private SortWindowViewFactory factory;

    public void setUp()
    {
        factory = new SortWindowViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"price", true, 100},
                     new String[] {"price"}, new Boolean[] {true}, 100);

        tryParameter(new Object[] {new Object[] {"price", true, "volume", false}, 100},
                     new String[] {"price", "volume"}, new Boolean[] {true, false}, 100);

        tryInvalidParameter(new Object[] {new Object[] {"price", "a", "volume", false}, 100});
        tryInvalidParameter(new Object[] {});
        tryInvalidParameter(new Object[] {"price", "x", 100});
        tryInvalidParameter(new Object[] {1.1, "x", 100});
        tryInvalidParameter(new Object[] {"price", true, "x"});
    }

    public void testAttachesTo() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(Arrays.asList(new Object[] {"price", true, 100}));
        factory.attach(parentType, SupportViewContextFactory.makeContext(), null);

        try
        {
            factory.setViewParameters(Arrays.asList(new Object[] {"xxx", true, 100}));
            factory.attach(parentType, null, null);
            fail();
        }
        catch (ViewAttachException ex)
        {
            // expected;
        }
    }

    private void tryInvalidParameter(Object[] params) throws Exception
    {
        try
        {
            factory.setViewParameters(Arrays.asList(params));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String[] fieldNames, Boolean[] ascInd, int size) throws Exception
    {
        factory.setViewParameters(Arrays.asList(params));
        SortWindowView view = (SortWindowView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(size, view.getSortWindowSize());
        ArrayAssertionUtil.assertEqualsExactOrder(fieldNames, view.getSortFieldNames());
        ArrayAssertionUtil.assertEqualsExactOrder(ascInd, view.getIsDescendingValues());
    }
}
