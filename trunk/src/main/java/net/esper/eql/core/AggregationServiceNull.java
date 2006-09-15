package net.esper.eql.core;

import net.esper.collection.MultiKey;
import net.esper.event.EventBean;
import net.esper.eql.core.AggregationService;

/**
 * A null object implementation of the AggregationService
 * interface.
 */
public class AggregationServiceNull implements AggregationService {

    public void applyEnter(EventBean[] eventsPerStream,
                           MultiKey optionalGroupKeyPerRow) {
    }

    public void applyLeave(EventBean[] eventsPerStream,
                           MultiKey optionalGroupKeyPerRow) {
    }

    public void setCurrentRow(MultiKey groupKey) {
    }

    public Object getValue(int column) {
        return null;
    }

}
