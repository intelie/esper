package net.esper.support.view;

import net.esper.event.BeanEventAdapter;
import net.esper.event.EventBean;
import net.esper.support.event.SupportEventTypeFactory;

import java.util.List;
import java.util.LinkedList;

public class SupportBeanClassView extends SupportBaseView
{
    private static List<SupportBeanClassView> instances = new LinkedList<SupportBeanClassView>();

    public SupportBeanClassView()
    {
        instances.add(this);
    }

    public SupportBeanClassView(Class clazz)
    {
        super(SupportEventTypeFactory.createBeanType(clazz));
        instances.add(this);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        super.setInvoked(true);
        this.lastNewData = newData;
        this.lastOldData = oldData;

        updateChildren(newData, oldData);
    }

    public static List<SupportBeanClassView> getInstances()
    {
        return instances;
    }
}
