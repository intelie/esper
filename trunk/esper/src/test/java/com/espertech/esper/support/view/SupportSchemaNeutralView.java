package com.espertech.esper.support.view;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.core.UpdateDispatchView;
import com.espertech.esper.collection.Pair;

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
