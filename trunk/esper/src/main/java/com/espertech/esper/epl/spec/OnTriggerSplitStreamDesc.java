/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import java.util.List;

/**
 * Specification for the on-select splitstream statement.
 */
public class OnTriggerSplitStreamDesc extends OnTriggerDesc
{
    private boolean isFirst;
    private final List<OnTriggerSplitStream> splitStreams;

    public OnTriggerSplitStreamDesc(OnTriggerType onTriggerType, boolean isFirst, List<OnTriggerSplitStream> splitStreams)
    {
        super(onTriggerType);
        this.isFirst = isFirst;
        this.splitStreams = splitStreams;
    }

    public List<OnTriggerSplitStream> getSplitStreams()
    {
        return splitStreams;
    }

    public boolean isFirst()
    {
        return isFirst;
    }
}
