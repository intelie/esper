package net.esper.support.view;

import net.esper.event.EventBean;
import net.esper.view.Viewable;

public class SupportSchemaNeutralView extends SupportBaseView
{
    public SupportSchemaNeutralView()
    {
    }

    public SupportSchemaNeutralView(String viewName)
    {
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        this.lastNewData = newData;
        this.lastOldData = oldData;

        updateChildren(newData, oldData);
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            setEventType(parent.getEventType());
        }
        else
        {
            setEventType(null);
        }
    }
}
