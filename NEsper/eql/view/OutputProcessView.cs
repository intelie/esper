using System;
using System.Collections.Generic;

using net.esper.eql;
using net.esper.eql.core;
using net.esper.eql.join;
using net.esper.eql.spec;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.view;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.eql.view
{
	/// <summary>
	/// Base output processing view that has the responsibility to serve up event type and
	/// statement iterator.
	/// &lt;p&gt;
	/// Implementation classes may enforce an output rate stabilizing or limiting policy.
	/// </summary>
	public abstract class OutputProcessView
		: ViewSupport
		, JoinSetIndicator
		, IEnumerable<EventBean>
	{
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /// <summary>
	    /// Processes the parent views result set generating events for pushing out to child view.
	    /// </summary>
	    protected readonly ResultSetProcessor resultSetProcessor;

	    /// <summary>Ctor.</summary>
	    /// <param name="resultSetProcessor">
	    /// processes the results posted by parent view or joins
	    /// </param>
	    protected OutputProcessView(ResultSetProcessor resultSetProcessor)
	    {
	        this.resultSetProcessor = resultSetProcessor;
	    }

	    public override EventType EventType
	    {
			get
			{
		    	if(resultSetProcessor != null)
		    	{
		            EventType eventType = resultSetProcessor.ResultEventType;
		            if (eventType != null)
		            {
		                return eventType;
		            }
		            return parent.EventType;
		    	}
		    	else
		    	{
		    		return parent.EventType;
		    	}
		    }
		}

	    public override IEnumerator<EventBean> GetEnumerator()
	    {
	    	if(resultSetProcessor != null)
	    	{
	    		OutputProcessTransform transform =
	    			new OutputProcessTransform(resultSetProcessor) ;
	    		return TransformEventUtil.TransformEnumerator(
	    			parent.GetEnumerator(),
	    			transform.Transform) ;
	    	}
	    	else
	    	{
	    		return parent.GetEnumerator();
	    	}
	    }

		abstract public void Process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents);
    }
}
