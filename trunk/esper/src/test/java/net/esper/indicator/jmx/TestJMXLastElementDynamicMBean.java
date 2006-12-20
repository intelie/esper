package net.esper.indicator.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.MBeanInfo;
import java.lang.management.ManagementFactory;

import junit.framework.TestCase;
import net.esper.event.EventType;
import net.esper.event.BeanEventAdapter;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;

public class TestJMXLastElementDynamicMBean extends TestCase
{
    private EventType eventType;
    private JMXLastElementDynamicMBean mbean;
    private SupportMarketDataBean dataBean;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);
        mbean = new JMXLastElementDynamicMBean(eventType);
        dataBean = new SupportMarketDataBean("IBM", 100, 190000L, "Reuters");
        mbean.setLastValue(SupportEventBeanFactory.createObject(dataBean));
    }

    public void testExpose() throws Exception
    {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("somepackage.view:type=TestJMXLastElementDynamicMBean");
        mbs.registerMBean(mbean, name);

        // Sleep and give someone a chance to look at a JMX console if an adapter was configured
        //Thread.sleep(Long.MAX_VALUE);
    }

    public void testBeanInfo()
    {
        MBeanInfo mbeanInfo = mbean.getMBeanInfo();
        assertTrue(mbeanInfo.getAttributes().length == 4);

        String[] validKeys = eventType.getPropertyNames();
        for (int i = 0; i < mbeanInfo.getAttributes().length; i++)
        {
            String name = mbeanInfo.getAttributes()[i].getName();
            assertTrue(existsIn(name, validKeys));
        }
    }

    public void testGetValue() throws Exception
    {
        assertEquals(190000L, mbean.getAttribute("volume"));
        assertEquals("Reuters", mbean.getAttribute("feed"));
    }

    private boolean existsIn(String value, Object[] values)
    {
        for (int i = 0; i < values.length; i++)
        {
            if (values[i].equals(value))
            {
                return true;
            }
        }
        return false;
    }
}
