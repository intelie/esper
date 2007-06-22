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
using net.esper.collection;
using net.esper.events;
using net.esper.view.window;

namespace net.esper.view.ext
{
	/// <summary>
	/// Provides random access into a sorted-window's data.
	/// </summary>
	public class IStreamSortedRandomAccess : RandomAccessByIndex
	{
		private readonly IStreamRandomAccessUpdateObserver updateObserver;

		private TreeDictionary<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents;
		private int currentSize;

		private IEnumerator<LinkedList<EventBean>> enumerator;
		private EventBean[] cache;
		private int cacheFilledTo;

		/// <summary>Ctor.</summary>
		/// <param name="updateObserver">for indicating updates to</param>
		public IStreamSortedRandomAccess(IStreamRandomAccessUpdateObserver updateObserver)
		{
			this.updateObserver = updateObserver;
		}

		/// <summary>Refreshes the random access data with the updated information.</summary>
		/// <param name="sortedEvents">is the sorted window contents</param>
		/// <param name="currentSize">is the current size of the window</param>
		/// <param name="maxSize">is the maximum size of the window</param>
		public void Refresh(TreeDictionary<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents, int currentSize, int maxSize)
		{
			updateObserver.Updated(this);
			this.sortedEvents = sortedEvents;
			this.currentSize = currentSize;

			this.enumerator = null;
			this.cacheFilledTo = 0;
			if (cache == null)
			{
				cache = new EventBean[maxSize];
			}
		}

        /// <summary>
        /// Returns an new data event given an index.
        /// </summary>
        /// <param name="index">to return new data for</param>
        /// <returns>new data event</returns>
		public EventBean GetNewData(int index)
		{
			if (enumerator == null)
			{
				enumerator = sortedEvents.Values.GetEnumerator();
			}

			// if asking for more then the sorted window currently holds, return no data
			if (index >= currentSize)
			{
				return null;
			}

			// If we have it in cache, serve from cache
			if (index < cacheFilledTo)
			{
				return cache[index];
			}

			// Load more into cache
			while(true)
			{
				if (cacheFilledTo == currentSize)
				{
					break;
				}
				if (!enumerator.MoveNext())
				{
					break;
				}
				LinkedList<EventBean> events = enumerator.Current;
				foreach (EventBean _event in events)
				{
					cache[cacheFilledTo] = _event;
					cacheFilledTo++;
				}

				if (cacheFilledTo > index)
				{
					break;
				}
			}

			// If we have it in cache, serve from cache
			if (index <= cacheFilledTo)
			{
				return cache[index];
			}

			return null;
		}

        /// <summary>
        /// Returns an old data event given an index.
        /// </summary>
        /// <param name="index">to return old data for</param>
        /// <returns>old data event</returns>
		public EventBean GetOldData(int index)
		{
			return null;
		}

		/// <summary>For indicating that the collection has been updated.</summary>
		public interface IStreamRandomAccessUpdateObserver
		{
			/// <summary>Callback to indicate an update</summary>
			/// <param name="iStreamSortedRandomAccess">is the collection</param>
			void Updated(IStreamSortedRandomAccess iStreamSortedRandomAccess);
		}
	}
} // End of namespace
