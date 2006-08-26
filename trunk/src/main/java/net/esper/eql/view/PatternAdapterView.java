package net.esper.eql.view;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.eql.expression.PatternStreamSpec;
import net.esper.pattern.PatternContext;
import net.esper.pattern.EvalRootNode;
import net.esper.pattern.PatternStopCallback;
import net.esper.pattern.PatternMatchCallback;

import java.util.Iterator;
import java.util.Map;

public class PatternAdapterView extends ViewSupport implements PatternMatchCallback
{
    private final EventType eventType;
    private final PatternStopCallback patternStopMethod;

    public PatternAdapterView(PatternStreamSpec patternStreamSpec, PatternContext patternContext)
    {
        // determine event type
        Map<String, EventType> eventTypes = patternStreamSpec.getTaggedEventTypes();
        eventType = patternContext.getEventAdapterService().createAnonymousMapTypeUnd(eventTypes);

        EvalRootNode rootNode = new EvalRootNode();
        rootNode.addChildNode(patternStreamSpec.getEvalNode());
        patternStopMethod = rootNode.start(this, patternContext);
    }

    public String attachesTo(Viewable parentViewable)
    {
        return null;
    }

    public void matchFound(Map<String, EventBean> matchEvent)
    {
        
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        throw new UnsupportedOperationException();
    }

    public void stop()
    {
        patternStopMethod.stop();
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}
