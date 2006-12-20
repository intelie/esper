package net.esper.eql.view;

import net.esper.support.view.SupportMapView;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import junit.framework.TestCase;

public class TestIStreamRStreamSelectorView extends TestCase
{
    private IStreamRStreamSelectorView viewIStream;
    private IStreamRStreamSelectorView viewRStream;
    private IStreamRStreamSelectorView viewBoth;
    private SupportMapView childView = new SupportMapView();

    public void setUp()
    {
        viewIStream = new IStreamRStreamSelectorView(SelectClauseStreamSelectorEnum.ISTREAM_ONLY);
        viewRStream = new IStreamRStreamSelectorView(SelectClauseStreamSelectorEnum.RSTREAM_ONLY);
        viewBoth = new IStreamRStreamSelectorView(SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH);

        childView = new SupportMapView();
        viewIStream.addView(childView);
        viewRStream.addView(childView);
        viewBoth.addView(childView);
    }

    public void testUpdate()
    {
        EventBean eventsOld[] = SupportEventBeanFactory.makeEvents(new String[] {"a", "b"});
        EventBean eventsNew[] = SupportEventBeanFactory.makeEvents(new String[] {"c", "d"});

        viewIStream.update(eventsNew, eventsOld);
        assertSame(eventsNew, childView.getLastNewData());
        assertNull(childView.getLastOldData());
        childView.reset();

        viewRStream.update(eventsNew, eventsOld);
        assertSame(eventsOld, childView.getLastNewData());
        assertNull(childView.getLastOldData());
        childView.reset();

        viewBoth.update(eventsNew, eventsOld);
        assertSame(eventsOld, childView.getLastOldData());
        assertSame(eventsNew, childView.getLastNewData());
        childView.reset();
    }
}
