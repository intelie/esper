package com.espertech.esper.epl.agg;

public class AggregationSpec
{
    private int streamNum;

    public AggregationSpec(int streamNum)
    {
        this.streamNum = streamNum;
    }

    public int getStreamNum()
    {
        return streamNum;
    }

    public void setStreamNum(int streamNum)
    {
        this.streamNum = streamNum;
    }
}
