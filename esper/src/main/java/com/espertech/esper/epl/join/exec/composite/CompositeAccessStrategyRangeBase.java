package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventPropertyGetter;

public abstract class CompositeAccessStrategyRangeBase {
    protected EventPropertyGetter start;
    protected boolean includeStart;
    protected int startStreamNum;

    protected EventPropertyGetter end;
    protected boolean includeEnd;
    protected int endStreamNum;

    protected Class coercionType;

    protected CompositeAccessStrategyRangeBase(EventPropertyGetter start, boolean includeStart, int startStreamNum, EventPropertyGetter end, boolean includeEnd, int endStreamNum, Class coercionType) {
        this.start = start;
        this.includeStart = includeStart;
        this.startStreamNum = startStreamNum;
        this.end = end;
        this.includeEnd = includeEnd;
        this.endStreamNum = endStreamNum;
        this.coercionType = coercionType;
    }
}
