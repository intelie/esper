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
using net.esper.core;
using net.esper.events;
using net.esper.view;
using org.apache.commons.logging;

namespace net.esper.view.window
{
	/// <summary>
	/// A data view that aggregates events in a stream and releases them in one batch when a maximum number of events has
	/// been collected.
	/// <p>
	/// The view works similar to a length_window but is not continuous, and similar to a time_batch however is not time-based
	/// but reacts to the number of events.
	/// <p>
	/// The view releases the batched events, when a certain number of batched events has been reached or exceeded,
	/// as new data to child views. The prior batch if
	/// not empty is released as old data to any child views. The view doesn't release intervals with no old or new data.
	/// It also does not collect old data published by a parent view.
	/// <p>
	/// If there are no events in the current and prior batch, the view will not invoke the update method of child views.
	/// </summary>
	public sealed class LengthBatchView : ViewSupport, CloneableView, DataWindowView
	{
	    // View parameters
	    private readonly LengthBatchViewFactory lengthBatchViewFactory;
	    private readonly int size;
	    private readonly ViewUpdatedCollection viewUpdatedCollection;

	    // Current running windows
	    private LinkedList<EventBean> lastBatch = null;
	    private LinkedList<EventBean> currentBatch = new LinkedList<EventBean>();

	    /// <summary>Constructor.</summary>
	    /// <param name="size">is the number of events to batch</param>
	    /// <param name="viewUpdatedCollection">
	    /// is a collection that the view must update when receiving events
	    /// </param>
	    /// <param name="lengthBatchViewFactory">for copying this view in a group-by</param>
	    public LengthBatchView(LengthBatchViewFactory lengthBatchViewFactory,
	                         int size,
	                         ViewUpdatedCollection viewUpdatedCollection)
	    {
	        this.lengthBatchViewFactory = lengthBatchViewFactory;
	        this.size = size;
	        this.viewUpdatedCollection = viewUpdatedCollection;

	        if (size <= 0)
	        {
	            throw new IllegalArgumentException("Invalid size parameter, size=" + size);
	        }
	    }

	    public View CloneView(StatementContext statementContext)
	    {
	        return lengthBatchViewFactory.MakeView(statementContext);
	    }

	    /// <summary>Returns the number of events to batch (data window size).</summary>
	    /// <returns>batch size</returns>
	    public int Count
	    {
            get { return size; }
	    }

	    public EventType EventType
	    {
            get { return parent.EventType; }
	    }

	    public void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        if (log.IsDebugEnabled())
	        {
	            log.Debug(".update Received update, " +
	                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
	                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
	        }

	        // we don't care about removed data from a prior view
	        if ((newData == null) || (newData.length == 0))
	        {
	            return;
	        }

	        // add data points to the current batch
	        for (int i = 0; i < newData.length; i++)
	        {
	            currentBatch.Add(newData[i]);
	        }

	        // check if we reached the minimum size
	        if (currentBatch.Size() < size)
	        {
	            // done if no overflow
	            return;
	        }

	        SendBatch();
	    }

	    /// <summary>This method updates child views and clears the batch of events.</summary>
	    protected void SendBatch()
	    {
	        if (log.IsDebugEnabled())
	        {
	            log.Debug(".sendBatch Update child views");
	        }

	        // If there are child views and the batch was filled, fireStatementStopped update method
	        if (this.HasViews())
	        {
	            // Convert to object arrays
	            EventBean[] newData = null;
	            EventBean[] oldData = null;
	            if (!currentBatch.IsEmpty())
	            {
	                newData = currentBatch.ToArray(new EventBean[0]);
	            }
	            if ((lastBatch != null) && (!lastBatch.IsEmpty()))
	            {
	                oldData = lastBatch.ToArray(new EventBean[0]);
	            }

	            // Post new data (current batch) and old data (prior batch)
	            if (viewUpdatedCollection != null)
	            {
	                viewUpdatedCollection.Update(newData, oldData);
	            }
	            if ((newData != null) || (oldData != null))
	            {
	                UpdateChildren(newData, oldData);
	            }
	        }

	        if (log.IsDebugEnabled())
	        {
	            log.Debug(".sendBatch Published updated data, ....newData size=" + currentBatch.Size());
	            foreach (Object item in currentBatch)
	            {
	                log.Debug(".sendBatch object=" + item);
	            }
	        }

	        lastBatch = currentBatch;
	        currentBatch = new LinkedList<EventBean>();
	    }

	    /// <summary>Returns true if the window is empty, or false if not empty.</summary>
	    /// <returns>true if empty</returns>
	    public bool IsEmpty
	    {
            get
            {
                if (lastBatch != null)
                {
                    if (!lastBatch.IsEmpty())
                    {
                        return false;
                    }
                }
                return currentBatch.IsEmpty();
            }
	    }

	    public IEnumerator<EventBean> GetEnumerator()
	    {
	        return currentBatch.GetEnumerator();
	    }

	    public override String ToString()
	    {
	        return this.Class.Name +
	                " size=" + size;
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
