package com.espertech.esper.epl.join.plan;

import java.util.List;

public class TableLookupKeyDesc {
    private List<QueryGraphValueEntryHashKeyed> hashes;
    private List<QueryGraphValueEntryRange> ranges;

    public TableLookupKeyDesc(List<QueryGraphValueEntryHashKeyed> hashes, List<QueryGraphValueEntryRange> ranges) {
        this.hashes = hashes;
        this.ranges = ranges;
    }

    public List<QueryGraphValueEntryHashKeyed> getHashes() {
        return hashes;
    }

    public List<QueryGraphValueEntryRange> getRanges() {
        return ranges;
    }
}
