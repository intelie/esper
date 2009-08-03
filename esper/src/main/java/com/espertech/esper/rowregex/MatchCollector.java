package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;

public interface MatchCollector
{
    public void add(RegexNFAState currentState, RegexNFAState nextState, EventBean row);
}
