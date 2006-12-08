package net.esper.eql.view;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;

import java.util.Iterator;

/**
 * View for applying a final "rstream" or "istream" selection on the result event rows before
 * publishing to listeners.
 */
public class IStreamRStreamSelectorView extends ViewSupport
{
    private final SelectClauseStreamSelectorEnum selectStreamDirEnum;

    /**
     * Ctor.
     * @param selectStreamDirEnum defines what stream is selected, or both streams
     */
    public IStreamRStreamSelectorView(SelectClauseStreamSelectorEnum selectStreamDirEnum)
    {
        this.selectStreamDirEnum = selectStreamDirEnum;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.RSTREAM_ONLY)
        {
            if (oldData != null)
            {
                // Hand only the old data as new data to child views
                this.updateChildren(oldData, null);
            }
        }
        else if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.ISTREAM_ONLY)
        {
            if (newData != null)
            {
                // Hand only the new data as new data to child views
                this.updateChildren(newData, null);
            }
        }
        else if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH)
        {
            this.updateChildren(newData, oldData);
        }
        else
        {
            throw new IllegalStateException("Unknown stream selector " + selectStreamDirEnum);
        }
    }

    public EventType getEventType()
    {
        return parent.getEventType();
    }

    public Iterator<EventBean> iterator()
    {
        return parent.iterator();
    }
}
