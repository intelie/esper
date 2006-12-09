package net.esper.adapter;

import net.esper.client.UpdateListener;
import net.esper.client.EPException;
import net.esper.event.EventBean;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 30, 2006
 * Time: 8:17:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface OutputAdapter
{

    // Send method to external systems.
    public void send(final EventBean eventBean_) throws EPException;

    /* Returns the active output adapter */
    public UpdateListener getEventBeanListener();

}
