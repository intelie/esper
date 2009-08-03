package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.Pair;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class MatchCollectorImpl implements MatchCollector
{
    private Map<RegexNFAState, Pair<List<EventBean>, RegexNFAState>> priorStates = new HashMap<RegexNFAState, Pair<List<EventBean>, RegexNFAState>>();

    public void add(RegexNFAState currentState, RegexNFAState nextState, EventBean row)
    {
        Pair<List<EventBean>, RegexNFAState> pair = priorStates.get(nextState);
        if (pair != null)
        {
            pair.getFirst().add(row);
        }
        else
        {
            List<EventBean> arr = new ArrayList<EventBean>();
            arr.add(row);
            pair = new Pair<List<EventBean>, RegexNFAState>(arr, currentState);
            priorStates.put(currentState, pair);
        }
    }
}
