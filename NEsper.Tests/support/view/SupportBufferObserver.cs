///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.compat;
using net.esper.collection;
using net.esper.view.internals;

namespace net.esper.support.view
{
	public class SupportBufferObserver : BufferObserver
	{
	    private bool hasNewData;
	    private int streamId;
	    private FlushedEventBuffer newEventBuffer;
	    private FlushedEventBuffer oldEventBuffer;

	    public bool GetAndResetHasNewData()
	    {
	        bool result = hasNewData;
	        hasNewData = false;
	        return result;
	    }

	    public void NewData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
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

	    public int GetAndResetStreamId()
	    {
	        int id = streamId;
	        streamId = 0;
	        return id;
	    }

	    public FlushedEventBuffer GetAndResetNewEventBuffer()
	    {
	        FlushedEventBuffer buf = newEventBuffer;
	        newEventBuffer = null;
	        return buf;
	    }

	    public FlushedEventBuffer GetAndResetOldEventBuffer()
	    {
	        FlushedEventBuffer buf = oldEventBuffer;
	        oldEventBuffer = null;
	        return buf;
	    }
	}
} // End of namespace
