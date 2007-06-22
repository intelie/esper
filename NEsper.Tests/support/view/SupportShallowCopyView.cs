///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.view;

namespace net.esper.support.view
{
	public class SupportShallowCopyView : ViewSupport
	{
	    private String someReadWriteValue;
	    private String someReadOnlyValue;
	    private String someWriteOnlyValue;

	    public SupportShallowCopyView(String someValue)
	    {
	        this.someReadWriteValue = someValue;
	        this.someReadOnlyValue = someValue;
	        this.someWriteOnlyValue = someValue;
	    }

	    public SupportShallowCopyView()
	    {
	    }

	    public bool IsNullWriteOnlyValue()
	    {
	        return someWriteOnlyValue == null;
	    }

	    public String GetSomeReadWriteValue()
	    {
	        return someReadWriteValue;
	    }

	    public void SetSomeReadWriteValue(String someReadWriteValue)
	    {
	        this.someReadWriteValue = someReadWriteValue;
	    }

	    public String GetSomeReadOnlyValue()
	    {
	        return someReadOnlyValue;
	    }

	    public void SetSomeWriteOnlyValue(String someWriteOnlyValue)
	    {
	        this.someWriteOnlyValue = someWriteOnlyValue;
	    }

	    public void SetParent()
	    {
	        throw new UnsupportedOperationException();
	    }

	    public override void Update(EventBean[] newData, EventBean[] oldData)
	    {
	    }

	    public override EventType EventType
	    {
	    	get { return null; }
	    }

	    public override IEnumerator<EventBean> GetEnumerator()
	    {
	        return null;
	    }
	}
} // End of namespace
