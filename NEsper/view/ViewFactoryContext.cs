///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.core;
using net.esper.events;
using net.esper.schedule;

namespace net.esper.view
{
	/// <summary>
	/// Context calss for specific views within a statement. Each view in a statement gets it's own context
	/// containing the statement context.
	/// </summary>
	public class ViewFactoryContext
	{
	    private StatementContext statementContext;
	    private readonly int streamNum;
	    private readonly int viewNum;
	    private readonly String namespaceName;
	    private readonly String viewName;

	    /// <summary>Ctor.</summary>
	    /// <param name="statementContext">is the statement-level services</param>
	    /// <param name="streamNum">is the stream number from zero to N</param>
	    /// <param name="viewNum">is the view number from zero to N</param>
	    /// <param name="namespaceName">is the view namespace</param>
	    /// <param name="viewName">is the view name</param>
	    public ViewFactoryContext(StatementContext statementContext, int streamNum, int viewNum, String namespaceName, String viewName)
	    {
	        this.statementContext = statementContext;
	        this.streamNum = streamNum;
	        this.viewNum = viewNum;
	        this.namespaceName = namespaceName;
	        this.viewName = viewName;
	    }

	    /// <summary>Returns service to use for schedule evaluation.</summary>
	    /// <returns>schedule evaluation service implemetation</returns>
	    public SchedulingService SchedulingService
	    {
            get { return statementContext.SchedulingService; }
	    }

	    /// <summary>Returns service for generating events and handling event types.</summary>
	    /// <returns>event adapter service</returns>
	    public EventAdapterService EventAdapterService
	    {
            get { return statementContext.EventAdapterService; }
	    }

	    /// <summary>
	    /// Returns the schedule bucket for ordering schedule callbacks within this pattern.
	    /// </summary>
	    /// <returns>schedule bucket</returns>
	    public ScheduleBucket ScheduleBucket
	    {
            get { return statementContext.ScheduleBucket; }
	    }

	    /// <summary>Returns the statement's resource locks.</summary>
	    /// <returns>statement resource lock/handle</returns>
	    public EPStatementHandle EpStatementHandle
	    {
            get { return statementContext.EpStatementHandle; }
	    }

	    /// <summary>Returns extension svc.</summary>
	    /// <returns>svc</returns>
	    public ExtensionServicesContext ExtensionServicesContext
	    {
            get { return statementContext.ExtensionServicesContext; }
	    }

	    /// <summary>Returns statement stop svc.</summary>
	    /// <returns>snc</returns>
	    public StatementStopService StatementStopService
	    {
            get { return statementContext.StatementStopService; }
	    }

	    /// <summary>Returns the statement id.</summary>
	    /// <returns>statement id</returns>
	    public String StatementId
	    {
	        get { return statementContext.StatementId; }
	    }

	    /// <summary>Returns the stream number.</summary>
	    /// <returns>stream number</returns>
	    public int StreamNum
	    {
	        get { return streamNum; }
	    }

	    /// <summary>Returns the view number</summary>
	    /// <returns>view number</returns>
	    public int ViewNum
	    {
            get { return viewNum; }
	    }

	    /// <summary>Returns the view namespace name.</summary>
	    /// <returns>namespace name</returns>
	    public String NamespaceName
	    {
            get { return namespaceName; }
	    }

	    /// <summary>Returns the view name.</summary>
	    /// <returns>view name</returns>
	    public String ViewName
	    {
            get { return viewName; }
	    }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
	    public override String ToString()
	    {
	        return  statementContext.ToString() +
	                " streamNum=" + streamNum +
	                " viewNum=" + viewNum +
	                " namespaceName=" + namespaceName +
	                " viewName=" + viewName;
	    }
	}
} // End of namespace
