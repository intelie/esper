package net.esper.support.view;

import java.util.Iterator;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;

public class SupportShallowCopyView extends ViewSupport
{
    private String someReadWriteValue;
    private String someReadOnlyValue;
    private String someWriteOnlyValue;

    public SupportShallowCopyView(String someValue)
    {
        this.someReadWriteValue = someValue;
        this.someReadOnlyValue = someValue;
        this.someWriteOnlyValue = someValue;
    }

    public SupportShallowCopyView()
    {
    }

    public boolean isNullWriteOnlyValue()
    {
        return someWriteOnlyValue == null;
    }
    
    public String getSomeReadWriteValue()
    {
        return someReadWriteValue;
    }

    public void setSomeReadWriteValue(String someReadWriteValue)
    {
        this.someReadWriteValue = someReadWriteValue;
    }

    public String getSomeReadOnlyValue()
    {
        return someReadOnlyValue;
    }

    public void setSomeWriteOnlyValue(String someWriteOnlyValue)
    {
        this.someWriteOnlyValue = someWriteOnlyValue;
    }

    public String attachesTo(Viewable parentViewable)
    {
        return null;
    }

    public void setParent()
    {
        throw new UnsupportedOperationException();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
    }

    public EventType getEventType()
    {
        return null;
    }

    public Iterator<EventBean> iterator()
    {
        return null;
    }
}
