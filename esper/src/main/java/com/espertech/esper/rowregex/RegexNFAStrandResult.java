package com.espertech.esper.rowregex;

import java.util.List;

public class RegexNFAStrandResult
{
    private List<RegexNFAState> startStates;
    private List<RegexNFAStateBase> allStates;

    public RegexNFAStrandResult(List<RegexNFAState> startStates, List<RegexNFAStateBase> allStates)
    {
        this.startStates = startStates;
        this.allStates = allStates;
    }

    public List<RegexNFAState> getStartStates() {
        return startStates;
    }

    public List<RegexNFAStateBase> getAllStates() {
        return allStates;
    }
}
