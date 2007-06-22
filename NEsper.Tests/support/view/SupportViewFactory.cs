// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.eql.core;
using net.esper.events;
using net.esper.view;

namespace net.esper.support.view
{
	public abstract class SupportViewFactory : ViewFactory
	{
		public virtual void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

		public virtual void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

		public virtual bool CanProvideCapability(ViewCapability viewCapability)
		{
			return false;  //To change body of implemented methods use File | Settings | File Templates.
		}

		public virtual void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

		public virtual View MakeView(StatementContext statementContext)
		{
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}

		public virtual EventType EventType
		{
			get
			{
				return null;  //To change body of implemented methods use File | Settings | File Templates.
			}
		}

		public virtual bool CanReuse(View view)
		{
			return false;  //To change body of implemented methods use File | Settings | File Templates.
		}
	}
} // End of namespace
