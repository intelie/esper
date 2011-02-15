package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventPropertyGetter;

public abstract class SortedAccessStrategyRangeBase  {
    protected EventPropertyGetter start;
    protected boolean includeStart;
    protected EventPropertyGetter end;
    protected boolean includeEnd;
    protected int streamNumStart;
    protected int streamNumEnd;

    protected SortedAccessStrategyRangeBase(EventPropertyGetter start, boolean includeStart, EventPropertyGetter end, boolean includeEnd, int streamNumStart, int streamNumEnd) {
        this.start = start;
        this.includeStart = includeStart;
        this.end = end;
        this.includeEnd = includeEnd;
        this.streamNumStart = streamNumStart;
        this.streamNumEnd = streamNumEnd;
    }
}
