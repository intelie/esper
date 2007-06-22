// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

namespace net.esper.support.bean
{
	public class SupportSelfReferenceEvent
	{
	    private SupportSelfReferenceEvent selfRef;

	    private String value;

	    public SupportSelfReferenceEvent()
	    {
	    }

	    public SupportSelfReferenceEvent GetSelfRef()
	    {
	        return selfRef;
	    }

	    public String GetValue()
	    {
	        return value;
	    }

	    public void SetSelfRef(SupportSelfReferenceEvent selfRef)
	    {
	        this.selfRef = selfRef;
	    }
	}
} // End of namespace
