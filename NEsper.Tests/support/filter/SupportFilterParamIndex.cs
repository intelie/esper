///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.events;
using net.esper.filter;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.support.filter
{
	public class SupportFilterParamIndex : FilterParamIndexPropBase
	{
	    public SupportFilterParamIndex()
	    	: base("intPrimitive", FilterOperator.EQUAL, SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)))
	    {
	    }

		public override EventEvaluator this[object filterConstant]
		{
			get { return null ; }
			set { }
		}
		
	    public override bool Remove(Object expressionValue)
	    {
	        return true;
	    }
	    
		public override int Count
		{
	    	get { return 0 ; }
		}

		public override ReaderWriterLock ReadWriteLock
		{
			get { return null ; }
		}
		
	    public override void MatchEvent(EventBean _event, IList<FilterHandle> matches)
	    {
	    }
	}
} // End of namespace
