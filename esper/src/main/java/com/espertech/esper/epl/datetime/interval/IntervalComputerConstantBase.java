package com.espertech.esper.epl.datetime.interval;

public abstract class IntervalComputerConstantBase {
    protected final long start;
    protected final long end;

    public IntervalComputerConstantBase(long start, long end) {
        this.start = start;
        this.end = end;
    }
}
