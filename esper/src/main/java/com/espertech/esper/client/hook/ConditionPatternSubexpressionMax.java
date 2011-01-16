package com.espertech.esper.client.hook;

public class ConditionPatternSubexpressionMax implements BaseCondition
{
    private final int max;

    public ConditionPatternSubexpressionMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }
}
