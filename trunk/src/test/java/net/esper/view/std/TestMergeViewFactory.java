package net.esper.view.std;

import junit.framework.TestCase;

import java.util.Arrays;

import net.esper.view.ViewParameterException;
import net.esper.view.window.TimeWindowView;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.util.ArrayAssertionUtil;

public class TestMergeViewFactory extends TestCase
{
    private MergeViewFactory factory;

    public void setUp()
    {
        factory = new MergeViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"price"}, new String[] {"price"});
        tryParameter(new Object[] {"price", "volume"}, new String[] {"price", "volume"});
        tryParameter(new Object[] {new String[] {"price", "volume"}}, new String[] {"price", "volume"});
        tryParameter(new Object[] {new String[] {"price"}}, new String[] {"price"});

        tryInvalidParameter(new Object[] {"a", 1.1d});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {new String[] {}});
        tryInvalidParameter(new Object[] {new String[] {}, new String[] {}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(Arrays.asList(new Object[] {"a", "b"}));
        assertFalse(factory.canReuse(new SizeView()));
        assertFalse(factory.canReuse(new MergeView(new String[] {"a"})));
        assertTrue(factory.canReuse(new MergeView(new String[] {"a", "b"})));

        factory.setViewParameters(Arrays.asList(new Object[] {new String[] {"a", "b"}}));
        assertFalse(factory.canReuse(new MergeView(new String[] {"a"})));
        assertTrue(factory.canReuse(new MergeView(new String[] {"a", "b"})));
    }

    private void tryInvalidParameter(Object[] params) throws Exception
    {
        try
        {
            MergeViewFactory factory = new MergeViewFactory();
            factory.setViewParameters(Arrays.asList(params));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String[] fieldNames) throws Exception
    {
        MergeViewFactory factory = new MergeViewFactory();
        factory.setViewParameters(Arrays.asList(params));
        MergeView view = (MergeView) factory.makeView(SupportViewContextFactory.makeContext());
        ArrayAssertionUtil.assertEqualsExactOrder(fieldNames, view.getGroupFieldNames());
    }
}
