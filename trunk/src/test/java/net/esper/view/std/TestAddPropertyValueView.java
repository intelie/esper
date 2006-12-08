package net.esper.view.std;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.BeanEventAdapter;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportMapView;
import net.esper.support.view.SupportSchemaNeutralView;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.view.ViewSupport;
import net.esper.view.ViewServiceContext;

public class TestAddPropertyValueView extends TestCase
{
    private AddPropertyValueView myView;
    private SupportMapView parentView;
    private SupportSchemaNeutralView childView;
    private EventType parentEventType;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new AddPropertyValueView(new String[] {"symbol"}, new Object[] {"IBM"});
        myView.setViewServiceContext(SupportViewContextFactory.makeContext());

        Map<String, Class> schema = new HashMap<String, Class>();
        schema.put("STDDEV", Double.class);
        parentEventType = SupportEventTypeFactory.createMapType(schema);

        parentView = new SupportMapView(schema);
        parentView.addView(myView);

        childView = new SupportSchemaNeutralView();
        myView.addView(childView);
    }

    public void testViewUpdate()
    {
        Map<String, Object> eventData = new HashMap<String, Object>();

        // Generate some events
        eventData.put("STDDEV", 100);
        EventBean eventBeanOne = SupportEventBeanFactory.createMapFromValues(eventData, parentEventType);
        eventData.put("STDDEV", 0);
        EventBean eventBeanTwo = SupportEventBeanFactory.createMapFromValues(eventData, parentEventType);
        eventData.put("STDDEV", 99999);
        EventBean eventBeanThree = SupportEventBeanFactory.createMapFromValues(eventData, parentEventType);

        // Send events
        parentView.update(new EventBean[] { eventBeanOne, eventBeanTwo},
                          new EventBean[] { eventBeanThree });

        // Checks
        EventBean[] newData = childView.getLastNewData();
        assertEquals(2, newData.length);
        assertEquals("IBM", newData[0].get("symbol"));
        assertEquals(100, newData[0].get("STDDEV"));
        assertEquals("IBM", newData[1].get("symbol"));
        assertEquals(0, newData[1].get("STDDEV"));

        EventBean[] oldData = childView.getLastOldData();
        assertEquals(1, oldData.length);
        assertEquals("IBM", oldData[0].get("symbol"));
        assertEquals(99999, oldData[0].get("STDDEV"));
    }

    public void testCopyView() throws Exception
    {
        AddPropertyValueView copied = (AddPropertyValueView) ViewSupport.shallowCopyView(myView);
        assertEquals(myView.getPropertyNames(), copied.getPropertyNames());
        assertEquals(myView.getPropertyValues(), copied.getPropertyValues());
    }

    public void TestAddProperty()
    {
        Map<String, Object> eventData = new HashMap<String, Object>();
        eventData.put("STDDEV", 100);
        EventBean eventBean = SupportEventBeanFactory.createMapFromValues(eventData, parentEventType);

        EventType newEventType = SupportEventAdapterService.getService().createAddToEventType(parentEventType, new String[] {"test"}, new Class[] {Integer.class});
        EventBean newBean = AddPropertyValueView.addProperty(eventBean, new String[] {"test"}, new Object[] {2}, newEventType, SupportEventAdapterService.getService());

        assertEquals(2, newBean.get("test"));
        assertEquals(100, newBean.get("STDDEV"));
    }
}