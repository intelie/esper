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

package com.espertech.esper.epl.named;

import com.espertech.esper.epl.lookup.SubordTableLookupStrategy;
import com.espertech.esper.util.StopCallback;

public class NamedWindowSubqueryStopCallback implements StopCallback {

    private final NamedWindowProcessor processor;
    private final SubordTableLookupStrategy namedWindowSubqueryLookup;

    public NamedWindowSubqueryStopCallback(NamedWindowProcessor processor, SubordTableLookupStrategy namedWindowSubqueryLookup) {
        this.processor = processor;
        this.namedWindowSubqueryLookup = namedWindowSubqueryLookup;
    }

    @Override
    public void stop() {
        processor.getRootView().removeSubqueryLookupStrategy(namedWindowSubqueryLookup);
    }
}
