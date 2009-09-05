package com.espertech.esper.regression.support;

public class StepDesc
{
    private final int step;
    private final Object[][] newDataPerRow;
    private final Object[][] oldDataPerRow;

    public StepDesc(int step, Object[][] newDataPerRow, Object[][] oldDataPerRow) {
        this.step = step;
        this.newDataPerRow = newDataPerRow;
        this.oldDataPerRow = oldDataPerRow;
    }

    public int getStep() {
        return step;
    }

    public Object[][] getNewDataPerRow() {
        return newDataPerRow;
    }

    public Object[][] getOldDataPerRow() {
        return oldDataPerRow;
    }
}
