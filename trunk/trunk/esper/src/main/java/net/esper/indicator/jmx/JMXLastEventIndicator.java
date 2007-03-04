package net.esper.indicator.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.DynamicMBean;
import java.lang.management.ManagementFactory;

import net.esper.client.UpdateListener;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.view.ViewFieldEnum;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * This listeners can be used to indicate events received through a JMX console.
 * The class utilizes DynamicMBean beans. In presents the last event only, and does not
 * expose the notion of a history.
 *
 * There are 2 implementations for the DynamicMBean interface that the class chooses from, depending
 * on the event type:
 * one for events that don't contain an OLAP cube {@link JMXLastElementDynamicMBean}
 * and one for cubes {@link JMXLastCubeElementDynamicMBean}.
 *
 *      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
 *      ObjectName name = new ObjectName(domainName + ":" + keyProperties + ",indexProperty=" + indexProperty);
 *      mbs.registerMBean(this, name);
 */
public final class JMXLastEventIndicator implements UpdateListener
{
    private static int indexProperty;
    private JMXLastElementObserver observerMbean;

    /**
     * Constructor.
     * @param eventType type of event to indicate
     * @param domainName is the domain name to use to constract a JMX ObjectName for the MBean.
     * @param keyProperties is the properties to use to constract a JMX ObjectName for the MBean
     */
    public JMXLastEventIndicator(EventType eventType, String domainName, String keyProperties)
    {
        indexProperty = indexProperty + 1;

        if (eventType.isProperty(ViewFieldEnum.MULTIDIM_OLAP__CUBE.getName()))
        {
            observerMbean = new JMXLastCubeElementDynamicMBean();
        }
        else
        {
            observerMbean = new JMXLastElementDynamicMBean(eventType);
        }

        // Determine object name
        String objectName = domainName;
        if (keyProperties.length() != 0)
        {
            objectName += ':' + keyProperties + ",indexProperty=" + indexProperty;
        }
        else
        {
            objectName += ":indexProperty=" + indexProperty;
        }

        if (log.isDebugEnabled())
        {
            log.debug(".setParent Registering mbean with name " + objectName);
        }

        // Attempt to register
        registerMBean(objectName, observerMbean);
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((newData != null) && ((newData.length > 0)))
        {
            observerMbean.setLastValue(newData[newData.length - 1]);
        }
    }

    private static void registerMBean(String objectName, DynamicMBean dynamicMBean)
    {
        try
        {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName(objectName);
            mbs.registerMBean(dynamicMBean, name);
        }
        catch (Exception ex)
        {
            log.fatal(".registerMBean Unexpected exception registering MBean with object name " + objectName, ex);
        }
    }

    private static final Log log = LogFactory.getLog(JMXLastEventIndicator.class);
}
