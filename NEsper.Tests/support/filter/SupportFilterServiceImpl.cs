///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.filter;

namespace net.esper.support.filter
{
	public class SupportFilterServiceImpl : FilterService
	{
	    private IList<Pair<FilterValueSet, FilterHandle>> added = new List<Pair<FilterValueSet, FilterHandle>>();
	    private IList<FilterHandle> removed = new List<FilterHandle>();

	    public void Add(FilterValueSet filterValueSet, FilterHandle callback)
	    {
	        added.Add(new Pair<FilterValueSet, FilterHandle>(filterValueSet, callback));
	    }

	    public void Remove(FilterHandle callback)
	    {
	        removed.Add(callback);
	    }

	    public IList<Pair<FilterValueSet, FilterHandle>> GetAdded()
	    {
	        return added;
	    }

	    public IList<FilterHandle> GetRemoved()
	    {
	        return removed;
	    }

        #region FilterService Members

        public long NumEventsEvaluated
        {
            get { throw new UnsupportedOperationException(); }
        }

        public void Evaluate(EventBean _event, IList<FilterHandle> matches)
        {
            throw new UnsupportedOperationException();
        }

        #endregion
    }
} // End of namespace
