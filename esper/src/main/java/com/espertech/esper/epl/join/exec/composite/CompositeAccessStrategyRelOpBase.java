package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventPropertyGetter;

public abstract class CompositeAccessStrategyRelOpBase {
    protected EventPropertyGetter key;
    protected Class coercionType;
    protected int keyStreamNum;

    protected CompositeAccessStrategyRelOpBase(EventPropertyGetter key, Class coercionType, int keyStreamNum) {
        this.key = key;
        this.coercionType = coercionType;
        this.keyStreamNum = keyStreamNum;
    }
}
