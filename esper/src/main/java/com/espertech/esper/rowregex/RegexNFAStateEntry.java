package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;

public class RegexNFAStateEntry
{
    private final int matchBeginEventSeqNo;
    private final long matchBeginEventTime;
    private final RegexNFAState state;
    private final EventBean[] eventsPerStream;
    private final int[] greedycountPerState;
    private final MultimatchState[] optionalMultiMatches;
    private final MultiKeyUntyped partitionKey;
    private int matchEndEventSeqNo;

    public RegexNFAStateEntry(int matchBeginEventSeqNo, long matchBeginEventTime, RegexNFAState state, EventBean[] eventsPerStream, int[] greedycountPerState, MultimatchState[] optionalMultiMatches, MultiKeyUntyped partitionKey) {
        this.matchBeginEventSeqNo = matchBeginEventSeqNo;
        this.matchBeginEventTime = matchBeginEventTime;
        this.state = state;
        this.eventsPerStream = eventsPerStream;
        this.greedycountPerState = greedycountPerState;
        this.optionalMultiMatches = optionalMultiMatches;
        this.partitionKey = partitionKey;
    }

    public int getMatchBeginEventSeqNo() {
        return matchBeginEventSeqNo;
    }

    public long getMatchBeginEventTime()
    {
        return matchBeginEventTime;
    }

    public RegexNFAState getState() {
        return state;
    }

    public EventBean[] getEventsPerStream() {
        return eventsPerStream;
    }

    public MultimatchState[] getOptionalMultiMatches() {
        return optionalMultiMatches;
    }

    public int[] getGreedycountPerState()
    {
        return greedycountPerState;
    }

    public void setMatchEndEventSeqNo(int matchEndEventSeqNo) {
        this.matchEndEventSeqNo = matchEndEventSeqNo;
    }

    public int getMatchEndEventSeqNo() {
        return matchEndEventSeqNo;
    }

    public MultiKeyUntyped getPartitionKey()
    {
        return partitionKey;
    }

    public String toString()
    {
        return "Entry " + state.toString();
    }
}
