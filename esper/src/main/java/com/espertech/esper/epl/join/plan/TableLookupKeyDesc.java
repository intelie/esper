/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

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
