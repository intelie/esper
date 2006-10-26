package net.esper.view;

import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportMapView;
import net.esper.support.view.SupportShallowCopyView;
import net.esper.support.view.SupportSchemaNeutralView;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.view.View;
import net.esper.view.ViewSupport;

public class TestViewSupport extends TestCase
{
    private SupportSchemaNeutralView top;

    private SupportSchemaNeutralView child_1;
    private SupportSchemaNeutralView child_2;

    private SupportSchemaNeutralView child_2_1;
    private SupportSchemaNeutralView child_2_2;

    private SupportSchemaNeutralView child_2_1_1;
    private SupportSchemaNeutralView child_2_2_1;
    private SupportSchemaNeutralView child_2_2_2;

    public void setUp()
    {
        top = new SupportSchemaNeutralView("top");

        child_1 = new SupportSchemaNeutralView("1");
        child_2 = new SupportSchemaNeutralView("2");
        top.addView(child_1);
        top.addView(child_2);

        child_2_1 = new SupportSchemaNeutralView("2_1");
        child_2_2 = new SupportSchemaNeutralView("2_2");
        child_2.addView(child_2_1);
        child_2.addView(child_2_2);

        child_2_1_1 = new SupportSchemaNeutralView("2_1_1");
        child_2_2_1 = new SupportSchemaNeutralView("2_2_1");
        child_2_2_2 = new SupportSchemaNeutralView("2_2_2");
        child_2_1.addView(child_2_1_1);
        child_2_2.addView(child_2_2_1);
        child_2_2.addView(child_2_2_2);
    }

    public void testShallowCopyView() throws Exception
    {
        // Copy a view based on a class
        SupportBeanClassView viewOne = new SupportBeanClassView(SupportMarketDataBean.class);
        SupportBeanClassView copyOne = (SupportBeanClassView) ViewSupport.shallowCopyView(viewOne);
        assertEquals(viewOne.getEventType(), copyOne.getEventType());

        // Copy a view based on a map
        SupportMapView viewTwo = new SupportMapView(new HashMap<String, Class>());
        viewTwo.setParent(viewOne);
        View copyTwo = ViewSupport.shallowCopyView(viewTwo);
        assertTrue(copyTwo.getParent() == null);
        assertEquals(viewTwo.getEventType(), copyTwo.getEventType());

        // Copy a view with read/write property access
        SupportShallowCopyView viewThree = new SupportShallowCopyView("avalue");
        SupportShallowCopyView copyThree = (SupportShallowCopyView) ViewSupport.shallowCopyView(viewThree);

        assertEquals("avalue", copyThree.getSomeReadWriteValue());
        assertEquals(null, copyThree.getSomeReadOnlyValue());
        assertTrue(copyThree.isNullWriteOnlyValue());
    }

    public void testFindDescendent()
    {
        // Test a deep find
        List<View> descendents = ViewSupport.findDescendent(top, child_2_2_1);
        assertEquals(2, descendents.size());
        assertEquals(child_2, descendents.get(0));
        assertEquals(child_2_2, descendents.get(1));

        descendents = ViewSupport.findDescendent(top, child_2_1_1);
        assertEquals(2, descendents.size());
        assertEquals(child_2, descendents.get(0));
        assertEquals(child_2_1, descendents.get(1));

        descendents = ViewSupport.findDescendent(top, child_2_1);
        assertEquals(1, descendents.size());
        assertEquals(child_2, descendents.get(0));

        // Test a shallow find
        descendents = ViewSupport.findDescendent(top, child_2);
        assertEquals(0, descendents.size());

        // Test a no find
        descendents = ViewSupport.findDescendent(top, new SupportSchemaNeutralView());
        assertEquals(null, descendents);
    }


}
