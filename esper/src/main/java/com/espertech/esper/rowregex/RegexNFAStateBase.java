package com.espertech.esper.rowregex;

import java.util.List;
import java.util.ArrayList;

public abstract class RegexNFAStateBase implements RegexNFAState 
{
    private final String nodeNumNested;
    private final String variableName;
    private final int streamNum;
    private final boolean multiple;
    private final List<RegexNFAState> nextStates;
    private final Boolean isGreedy;

    private int nodeNumFlat;

    public RegexNFAStateBase(String nodeNum, String variableName, int streamNum, boolean multiple, Boolean isGreedy)
    {
        this.nodeNumNested = nodeNum;
        this.variableName = variableName;
        this.streamNum = streamNum;
        this.multiple =  multiple;
        this.isGreedy = isGreedy;
        nextStates = new ArrayList<RegexNFAState>();
    }

    public int getNodeNumFlat()
    {
        return nodeNumFlat;
    }

    public void setNodeNumFlat(int nodeNumFlat)
    {
        this.nodeNumFlat = nodeNumFlat;
    }

    public String getNodeNumNested() {
        return nodeNumNested;
    }

    public List<RegexNFAState> getNextStates()
    {
        return nextStates;
    }

    public void addState(RegexNFAState next)
    {
        nextStates.add(next);
    }

    public boolean isMultiple() {
        return multiple;
    }

    public String getVariableName() {
        return variableName;
    }

    public int getStreamNum()
    {
        return streamNum;
    }

    public Boolean isGreedy()
    {
        return isGreedy;
    }
}
