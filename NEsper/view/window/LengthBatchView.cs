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
	/// </p>
	/// <p>
	/// The view releases the batched events, when a certain number of batched events has been reached or exceeded,
	/// as new data to child views. The prior batch if
	/// not empty is released as old data to any child views. The view doesn't release intervals with no old or new data.
	/// It also does not collect old data published by a parent view.
	/// </p>
	/// <p>
	/// If there are no events in the current and prior batch, the view will not invoke the update method of child views.
	/// </p>
	/// </summary>
	public sealed class LengthBatchView : ViewSupport, CloneableView, DataWindowView
	{
	    // View parameters
	    private readonly LengthBatchViewFactory lengthBatchViewFactory;
	    private readonly int size;
	    private readonly ViewUpdatedCollection viewUpdatedCollection;

	    // Current running windows
	    private List<EventBean> lastBatch = null;
	    private List<EventBean> currentBatch = new List<EventBean>();

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
	            throw new ArgumentException("Invalid size parameter, size=" + size);
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

        /// <summary>
        /// Provides metadata information about the type of object the event collection contains.
        /// </summary>
        /// <value></value>
        /// <returns>
        /// metadata for the objects in the collection
        /// </returns>
	    public override EventType EventType
	    {
            get { return parent.EventType; }
	    }

	    public override void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".update Received update, " +
	                    "  newData.Length==" + ((newData == null) ? 0 : newData.Length) +
	                    "  oldData.Length==" + ((oldData == null) ? 0 : oldData.Length));
	        }

	        // we don't care about removed data from a prior view
	        if ((newData == null) || (newData.Length == 0))
	        {
	            return;
	        }

	        // add data points to the current batch
	        for (int i = 0; i < newData.Length; i++)
	        {
	            currentBatch.Add(newData[i]);
	        }

	        // check if we reached the minimum size
	        if (currentBatch.Count < size)
	        {
	            // done if no overflow
	            return;
	        }

	        SendBatch();
	    }

        /// <summary>
        /// This method updates child views and clears the batch of events.
        /// </summary>
	    internal void SendBatch()
	    {
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".sendBatch Update child views");
	        }

	        // If there are child views and the batch was filled, fireStatementStopped update method
	        if (this.HasViews)
	        {
	            // Convert to object arrays
	            EventBean[] newData = null;
	            EventBean[] oldData = null;
	            if (currentBatch.Count != 0)
	            {
	                newData = currentBatch.ToArray();
	            }
	            if ((lastBatch != null) && (lastBatch.Count != 0))
	            {
	                oldData = lastBatch.ToArray();
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

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".sendBatch Published updated data, ....newData size=" + currentBatch.Count);
	            foreach (EventBean item in currentBatch)
	            {
	                log.Debug(".sendBatch object=" + item);
	            }
	        }

	        lastBatch = currentBatch;
	        currentBatch = new List<EventBean>();
	    }

	    /// <summary>Returns true if the window is empty, or false if not empty.</summary>
	    /// <returns>true if empty</returns>
	    public bool IsEmpty
	    {
            get
            {
                if (lastBatch != null)
                {
                    if (lastBatch.Count != 0)
                    {
                        return false;
                    }
                }
                return (currentBatch.Count == 0);
            }
	    }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
	    public override IEnumerator<EventBean> GetEnumerator()
	    {
	        return currentBatch.GetEnumerator();
	    }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
	    public override String ToString()
	    {
	        return this.GetType().FullName +
	                " size=" + size;
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
