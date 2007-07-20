// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.core;
using net.esper.events;
using net.esper.view;

namespace net.esper.regression.client
{
	public class MyTrendSpotterView : ViewSupport
	{
	    private static readonly String PROPERTY_NAME = "trendcount";

	    private readonly StatementContext statementContext;
	    private readonly EventType eventType;
	    private readonly String fieldName;
	    private EventPropertyGetter fieldGetter;

	    private long? trendcount;
	    private double? lastDataPoint;

	    /// <summary>
	    /// Constructor requires the name of the field to use in the parent view to compute a trend.
	    /// </summary>
	    /// <param name="fieldName">
	    /// is the name of the field within the parent view to use to get numeric data points for this view
	    /// </param>
	    /// <param name="statementContext">contains required view services</param>
	    public MyTrendSpotterView(StatementContext statementContext, String fieldName)
	    {
	        this.statementContext = statementContext;
	        this.fieldName = fieldName;
	        eventType = CreateEventType(statementContext);
	    }

	    public void SetParent(Viewable parent)
	    {
	        base.Parent = parent;
	        if (parent != null)
	        {
	            fieldGetter = parent.EventType.GetGetter(fieldName);
	        }
	    }

	    public View CloneView(StatementContext statementContext)
	    {
	        return new MyTrendSpotterView(statementContext, fieldName);
	    }

	    /// <summary>Returns field name of the field to report statistics on.</summary>
	    /// <returns>field name</returns>
	    public String FieldName
	    {
	        get { return fieldName; }
	    }

	    public override void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        EventBean oldDataPost = PopulateMap(trendcount);

	        // add data points
	        if (newData != null)
	        {
	            for (int i = 0; i < newData.Length; i++)
	            {
	                double dataPoint = Convert.ToDouble(fieldGetter.GetValue(newData[i]));

	                if (lastDataPoint == null)
	                {
	                    trendcount = 1L;
	                }
	                else if (lastDataPoint < dataPoint)
	                {
	                    trendcount++;
	                }
	                else if (lastDataPoint > dataPoint)
	                {
	                    trendcount = 0L;
	                }
	                lastDataPoint = dataPoint;
	            }
	        }

	        if (this.HasViews)
	        {
	            EventBean newDataPost = PopulateMap(trendcount);
	            UpdateChildren(new EventBean[] {newDataPost}, new EventBean[] {oldDataPost});
	        }
	    }

	    public override EventType EventType
	    {
            get { return eventType; }
	    }

	    public override IEnumerator<EventBean> GetEnumerator()
	    {
	        EventBean _event = PopulateMap(trendcount);
	        yield return _event;
	    }

	    public override String ToString()
	    {
	        return this.GetType().FullName + " fieldName=" + fieldName;
	    }

	    private EventBean PopulateMap(long? trendcount)
	    {
	        EDictionary<String, Object> result = new HashDictionary<String, Object>();
	        result.Put(PROPERTY_NAME, trendcount);
	        return statementContext.EventAdapterService.CreateMapFromValues(result, eventType);
	    }

	    /// <summary>Creates the event type for this view.</summary>
	    /// <param name="statementContext">is the event adapter service</param>
	    /// <returns>event type of view</returns>
	    public static EventType CreateEventType(StatementContext statementContext)
	    {
	        EDictionary<String, Type> eventTypeMap = new HashDictionary<String, Type>();
	        eventTypeMap.Put(PROPERTY_NAME, typeof(long?));
	        return statementContext.EventAdapterService.CreateAnonymousMapType(eventTypeMap);
	    }
	}
} // End of namespace
