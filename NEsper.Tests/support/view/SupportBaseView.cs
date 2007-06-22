///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.support.view
{
	public abstract class SupportBaseView : ViewSupport
	{
	    protected EventBean[] lastNewData;
	    protected EventBean[] lastOldData;
	    protected EventType eventType;

	    public bool IsInvoked
	    {
	        get { return isInvoked; }
            set { isInvoked = value; }
	    }

	    protected bool isInvoked;

	    /**
	     * Default constructor since views are also beans.
	     */
	    public SupportBaseView()
	    {
	    }

	    public SupportBaseView(EventType eventType)
	    {
	        this.eventType = eventType;
	    }

	    public override EventType EventType
	    {
	    	get { return eventType; }
        }

        public EventType MutableEventType
        {
            get { return eventType; }
            set { eventType = value; }
	    }

	    public void SetEventType(EventType eventType)
	    {
	        this.eventType = eventType;
	    }

	    public override IEnumerator<EventBean> GetEnumerator()
	    {
	        log.Info(".iterator Not implemented");
	        return null;
	    }

	    public EventBean[] LastNewData
	    {
            get { return lastNewData; }
            set { this.lastNewData = value; }
	    }

	    public EventBean[] LastOldData
	    {
	        get { return lastOldData; }
            set { lastOldData = value; }
	    }

	    public void ClearLastNewData()
	    {
	        lastNewData = null;
	    }

	    public void ClearLastOldData()
	    {
	        lastOldData = null;
	    }

	    public bool GetAndClearIsInvoked()
	    {
	        bool invoked = isInvoked;
	        isInvoked = false;
	        return invoked;
	    }

	    public void Reset()
	    {
	        isInvoked = false;
	        lastNewData = null;
	        lastOldData = null;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
