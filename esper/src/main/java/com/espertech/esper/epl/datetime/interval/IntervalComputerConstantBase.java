package com.espertech.esper.epl.datetime.interval;

public abstract class IntervalComputerConstantBase {
    protected final long start;
    protected final long end;

    public IntervalComputerConstantBase(IntervalStartEndParameterPair pair, boolean allowSwitch) {
        long startVal = pair.getStart().getOptionalConstant();
        long endVal = pair.getEnd().getOptionalConstant();

        if (startVal > endVal && allowSwitch) {
            start = endVal;
            end = startVal;
        }
        else {
            start = startVal;
            end = endVal;
        }
    }
}
