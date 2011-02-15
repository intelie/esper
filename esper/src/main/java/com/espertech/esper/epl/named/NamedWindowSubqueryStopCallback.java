package com.espertech.esper.epl.named;

import com.espertech.esper.epl.lookup.SubqTableLookupStrategy;
import com.espertech.esper.util.StopCallback;

public class NamedWindowSubqueryStopCallback implements StopCallback {

    private final NamedWindowProcessor processor;
    private final SubqTableLookupStrategy namedWindowSubqueryLookup;

    public NamedWindowSubqueryStopCallback(NamedWindowProcessor processor, SubqTableLookupStrategy namedWindowSubqueryLookup) {
        this.processor = processor;
        this.namedWindowSubqueryLookup = namedWindowSubqueryLookup;
    }

    @Override
    public void stop() {
        processor.getRootView().removeSubqueryLookupStrategy(namedWindowSubqueryLookup);
    }
}
