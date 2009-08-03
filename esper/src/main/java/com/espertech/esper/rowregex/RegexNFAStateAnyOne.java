package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;

public class RegexNFAStateAnyOne extends RegexNFAStateBase implements RegexNFAState
{
    public RegexNFAStateAnyOne(String nodeNum, String variableName, int streamNum, boolean multiple)
    {
        super(nodeNum, variableName, streamNum, multiple, null);
    }

    public boolean matches(EventBean[] eventsPerStream)
    {
        return true;
    }

    public String toString()
    {
        return "AnyEvent";
    }
}
