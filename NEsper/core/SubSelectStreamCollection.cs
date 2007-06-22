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
using net.esper.eql.expression;
using net.esper.view;

namespace net.esper.core
{
	/// <summary>Holds stream information for subqueries.</summary>
	public class SubSelectStreamCollection
	{
	    private EDictionary<ExprSubselectNode, SubSelectHolder> subqueries;

	    /// <summary>Ctor.</summary>
	    public SubSelectStreamCollection()
	    {
	        subqueries = new HashDictionary<ExprSubselectNode, SubSelectHolder>();
	    }

	    /// <summary>Add subquery.</summary>
	    /// <param name="subselectNode">is the subselect expression node</param>
	    /// <param name="streamNumber">is the subquery stream number</param>
	    /// <param name="viewable">is the subquery viewable</param>
	    /// <param name="viewFactoryChain">is the chain of view factories</param>
	    public void Add(ExprSubselectNode subselectNode, int streamNumber, Viewable viewable, ViewFactoryChain viewFactoryChain)
	    {
	        subqueries[subselectNode] = new SubSelectHolder(streamNumber, viewable, viewFactoryChain);
	    }

	    /// <summary>Returns stream number.</summary>
	    /// <param name="subqueryNode">is the subquery node's stream number</param>
	    /// <returns>number of stream</returns>
	    public int GetStreamNumber(ExprSubselectNode subqueryNode)
	    {
	        return subqueries[subqueryNode].StreamNumber;
	    }

	    /// <summary>Returns the subquery viewable, child-most view.</summary>
	    /// <param name="subqueryNode">is the expression node to get this for</param>
	    /// <returns>child viewable</returns>
	    public Viewable GetRootViewable(ExprSubselectNode subqueryNode)
	    {
	        return subqueries[subqueryNode].Viewable;
	    }

	    /// <summary>Returns the subquery's view factory chain.</summary>
	    /// <param name="subqueryNode">is the node to look for</param>
	    /// <returns>view factory chain</returns>
	    public ViewFactoryChain GetViewFactoryChain(ExprSubselectNode subqueryNode)
	    {
	        return subqueries[subqueryNode].ViewFactoryChain;
	    }

	    /// <summary>Entry holding subquery resource references.</summary>
	    public class SubSelectHolder
	    {
	        private int streamNumber;
	        private Viewable viewable;
	        private ViewFactoryChain viewFactoryChain;

	        /// <summary>Ctor.</summary>
	        /// <param name="streamNumber">is the subquery stream number</param>
	        /// <param name="viewable">is the root viewable</param>
	        /// <param name="viewFactoryChain">is the view chain</param>
	        public SubSelectHolder(int streamNumber, Viewable viewable, ViewFactoryChain viewFactoryChain)
	        {
	            this.streamNumber = streamNumber;
	            this.viewable = viewable;
	            this.viewFactoryChain = viewFactoryChain;
	        }

	        /// <summary>Returns subquery stream number.</summary>
	        /// <returns>stream num</returns>
	        public int StreamNumber
	        {
	            get { return streamNumber; }
	        }

	        /// <summary>Returns the subquery child viewable.</summary>
	        /// <returns>child-most viewable</returns>
	        public Viewable Viewable
	        {
                get { return viewable; }
	        }

	        /// <summary>Returns the subquery view factory chain</summary>
	        /// <returns>view factory chain</returns>
	        public ViewFactoryChain ViewFactoryChain
	        {
                get { return viewFactoryChain; }
	        }
	    }
	}
} // End of namespace
