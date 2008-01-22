package net.esper.support.view;

import net.esper.event.EventBean;
import net.esper.view.Viewable;
import net.esper.core.UpdateDispatchView;
import net.esper.collection.Pair;

public class SupportSchemaNeutralView extends SupportBaseView implements UpdateDispatchView
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

    public void newResult(Pair<EventBean[], EventBean[]> result)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
