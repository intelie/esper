package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;

import java.util.List;
import java.util.Collections;

public class RegexNFAStateEnd extends RegexNFAStateBase
{
    public RegexNFAStateEnd() {
        super("endstate", null, -1, false, null);
    }

    public boolean matches(EventBean[] eventsPerStream)
    {
        throw new UnsupportedOperationException();
    }

    public List<RegexNFAState> getNextStates()
    {
        return Collections.EMPTY_LIST;
    }
}
