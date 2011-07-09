package com.espertech.esper.epl.datetime.interval;

public abstract class IntervalComputerConstantBase {
    protected final long start;
    protected final long end;

    public IntervalComputerConstantBase(IntervalStartEndParameterPair pair) {
        this.start = pair.getStart().getOptionalConstant();
        this.end = pair.getEnd().getOptionalConstant();
    }
}
