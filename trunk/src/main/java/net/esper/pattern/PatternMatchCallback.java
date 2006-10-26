package net.esper.pattern;

import net.esper.event.EventBean;
import java.util.Map;

/**
 * Callback interface for anything that requires to be informed of matching events which would be stored
 * in the MatchedEventMap structure passed to the implementation.
 */
public interface PatternMatchCallback
{
    /**
     * Indicate matching events.
     * @param matchEvent contains a map of event tags and event objects
     */
    public void matchFound(Map<String, EventBean> matchEvent);
}
