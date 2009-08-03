package com.espertech.esper.rowregex;

import java.util.List;

public class RegexNFAStrand
{
    private List<RegexNFAStateBase> startStates;
    private List<RegexNFAStateBase> endStates;
    private List<RegexNFAStateBase> allStates;
    private boolean isPassthrough;

    public RegexNFAStrand(List<RegexNFAStateBase> startStates, List<RegexNFAStateBase> endStates, List<RegexNFAStateBase> allStates, boolean passthrough) {
        this.startStates = startStates;
        this.endStates = endStates;
        this.allStates = allStates;
        isPassthrough = passthrough;
    }

    public List<RegexNFAStateBase> getStartStates() {
        return startStates;
    }

    public List<RegexNFAStateBase> getEndStates() {
        return endStates;
    }

    public List<RegexNFAStateBase> getAllStates() {
        return allStates;
    }

    public boolean isPassthrough() {
        return isPassthrough;
    }
}
