package com.espertech.esper.support.view;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.view.CloneableView;
import com.espertech.esper.view.View;
import com.espertech.esper.core.StatementContext;

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

    public View cloneView(StatementContext context)
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
