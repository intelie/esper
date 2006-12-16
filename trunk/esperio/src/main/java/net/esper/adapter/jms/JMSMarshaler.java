package net.esper.adapter.jms;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.client.EPException;
import net.esper.schedule.ScheduleSlot;

import javax.jms.Message;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 15, 2006
 * Time: 9:53:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface JMSMarshaler
{
   /**
     * Marshals the jms message payload into the event bean, typically as the content
     * property.
     */

    public JMSEventBean marshal(EventType eventType, Message message, long totalDelay, ScheduleSlot scheduleSlot) throws EPException;

    /**
     * Unmarshals the response out of the event bean.
    */

    public MapMessage unmarshal(EventBean eventBean, Session session) throws EPException;

}
