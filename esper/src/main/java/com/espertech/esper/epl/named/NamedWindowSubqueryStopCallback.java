package com.espertech.esper.epl.named;

import com.espertech.esper.epl.lookup.SubordTableLookupStrategy;
import com.espertech.esper.util.StopCallback;

public class NamedWindowSubqueryStopCallback implements StopCallback {

    private final NamedWindowProcessor processor;
    private final SubordTableLookupStrategy namedWindowSubqueryLookup;

    public NamedWindowSubqueryStopCallback(NamedWindowProcessor processor, SubordTableLookupStrategy namedWindowSubqueryLookup) {
        this.processor = processor;
        this.namedWindowSubqueryLookup = namedWindowSubqueryLookup;
    }

    @Override
    public void stop() {
        processor.getRootView().removeSubqueryLookupStrategy(namedWindowSubqueryLookup);
    }
}
