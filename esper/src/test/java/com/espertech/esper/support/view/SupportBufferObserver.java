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

package com.espertech.esper.support.view;

import com.espertech.esper.view.internal.BufferObserver;
import com.espertech.esper.collection.FlushedEventBuffer;

public class SupportBufferObserver implements BufferObserver
{
    private boolean hasNewData;
    private int streamId;
    private FlushedEventBuffer newEventBuffer;
    private FlushedEventBuffer oldEventBuffer;

    public boolean getAndResetHasNewData()
    {
        boolean result = hasNewData;
        hasNewData = false;
        return result;
    }

    public void newData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
    {
        if (hasNewData == true)
        {
            throw new IllegalStateException("Observer already has new data");
        }

        hasNewData = true;
        this.streamId = streamId;
        this.newEventBuffer = newEventBuffer;
        this.oldEventBuffer = oldEventBuffer;
    }

    public int getAndResetStreamId()
    {
        int id = streamId;
        streamId = 0;
        return id;
    }

    public FlushedEventBuffer getAndResetNewEventBuffer()
    {
        FlushedEventBuffer buf = newEventBuffer;
        newEventBuffer = null;
        return buf;
    }

    public FlushedEventBuffer getAndResetOldEventBuffer()
    {
        FlushedEventBuffer buf = oldEventBuffer;
        oldEventBuffer = null;
        return buf;
    }
}
