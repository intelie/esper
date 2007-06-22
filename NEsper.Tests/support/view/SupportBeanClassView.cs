///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.events;
using net.esper.support.events;
using net.esper.view;

namespace net.esper.support.view
{
	public class SupportBeanClassView 
        : SupportBaseView
        , CloneableView
	{
	    private static readonly List<SupportBeanClassView> instances = new List<SupportBeanClassView>();
	    private readonly Type clazz;

	    public SupportBeanClassView()
	    {
	        instances.Add(this);
	    }

	    public SupportBeanClassView(Type clazz)
	        : base(SupportEventTypeFactory.CreateBeanType(clazz))
	    {
	        this.clazz = clazz;
	        instances.Add(this);
	    }

	    public View CloneView(StatementContext context)
	    {
	        return new SupportBeanClassView(clazz);
	    }

	    public override void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        base.IsInvoked = true;

	        this.lastNewData = newData;
	        this.lastOldData = oldData;

	        UpdateChildren(newData, oldData);
	    }

	    public static IList<SupportBeanClassView> Instances
	    {
	        get { return instances; }
	    }
	}
} // End of namespace
