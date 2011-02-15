package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventPropertyGetter;

public abstract class CompositeAccessStrategyRangeBase {
    protected EventPropertyGetter start;
    protected boolean includeStart;
    protected EventPropertyGetter end;
    protected boolean includeEnd;
    protected Class coercionType;

    protected CompositeAccessStrategyRangeBase(EventPropertyGetter start, boolean includeStart, EventPropertyGetter end, boolean includeEnd, Class coercionType) {
        this.start = start;
        this.includeStart = includeStart;
        this.end = end;
        this.includeEnd = includeEnd;
        this.coercionType = coercionType;
    }
}
