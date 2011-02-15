package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventPropertyGetter;

public abstract class SortedAccessStrategyRelOpBase {
    protected EventPropertyGetter keyGetter;
    protected  int keyStreamNum;

    protected SortedAccessStrategyRelOpBase(EventPropertyGetter keyGetter, int keyStreamNum) {
        this.keyGetter = keyGetter;
        this.keyStreamNum = keyStreamNum;
    }
}
