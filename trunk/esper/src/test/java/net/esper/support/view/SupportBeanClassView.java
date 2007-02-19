package net.esper.support.view;

import net.esper.event.BeanEventAdapter;
import net.esper.event.EventBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.view.CloneableView;
import net.esper.view.View;
import net.esper.view.ViewServiceContext;

import java.util.List;
import java.util.LinkedList;

public class SupportBeanClassView extends SupportBaseView implements CloneableView
{
    private static List<SupportBeanClassView> instances = new LinkedList<SupportBeanClassView>();
    private Class clazz;

    public SupportBeanClassView()
    {
        instances.add(this);
    }

    public SupportBeanClassView(Class clazz)
    {
        super(SupportEventTypeFactory.createBeanType(clazz));
        this.clazz = clazz;
        instances.add(this);
    }

    public View cloneView(ViewServiceContext context)
    {
        return new SupportBeanClassView(clazz);
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
