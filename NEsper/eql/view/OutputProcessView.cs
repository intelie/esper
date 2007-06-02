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
	/**
	 * Base output processing view that has the responsibility to serve up event type and
	 * statement iterator.
	 * <p>
	 * Implementation classes may enforce an output rate stabilizing or limiting policy.
	 */
	public abstract class OutputProcessView
		: ViewSupport
		, JoinSetIndicator
		, IEnumerable<EventBean>
	{
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /**
	     * Processes the parent views result set generating events for pushing out to child view.
	     */
	    protected readonly ResultSetProcessor resultSetProcessor;

	    /**
	     * Ctor.
	     * @param resultSetProcessor processes the results posted by parent view or joins
	     */
	    protected OutputProcessView(ResultSetProcessor resultSetProcessor)
	    {
	        this.resultSetProcessor = resultSetProcessor;
	    }

	    public EventType EventType
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

	    public IEnumerator<EventBean> GetEnumerator()
	    {
	    	if(resultSetProcessor != null)
	    	{
	            return new TransformEventIterator(parent.GetEnumerator(), new OutputProcessViewPolicy.OutputProcessTransform(resultSetProcessor));
	    	}
	    	else
	    	{
	    		return parent.GetEnumerator();
	    	}
	    }    
    }
}
