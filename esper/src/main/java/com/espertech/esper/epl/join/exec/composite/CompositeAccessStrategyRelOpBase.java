package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventPropertyGetter;

public abstract class CompositeAccessStrategyRelOpBase {
    protected EventPropertyGetter key;
    protected Class coercionType;

    protected CompositeAccessStrategyRelOpBase(EventPropertyGetter key, Class coercionType) {
        this.key = key;
        this.coercionType = coercionType;
    }
}
