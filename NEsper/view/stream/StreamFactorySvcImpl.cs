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
using net.esper.core;
using net.esper.events;
using net.esper.filter;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.view.stream
{
	/// <summary>
	/// Service implementation to reuse or not reuse event streams and existing filters depending on
	/// the type of statement.
	/// <p>
	/// For non-join statements, the class manages the reuse of event streams when filters match, and thus
	/// when an event stream is reused such can be the views under the stream. For joins however, this can lead to
	/// problems in multithread-safety since the statement resource lock would then have to be multiple locks,
	/// i.e. the reused statement's resource lock and the join statement's own lock, at a minimum.
	/// </p>
	/// <p>
	/// For join statements, always creating a new event stream and
	/// therefore not reusing view resources, for use with joins.
	/// </p>
	/// <p>
	/// This can be very effective in that if a client applications creates a large number of very similar
	/// statements in terms of filters and views used then these resources are all re-used
	/// across statements.
	/// </p>
	/// <p>
	/// The re-use is multithread-safe in that
	/// (A) statement start/stop is locked against other engine processing
	/// (B) the first statement supplies the lock for shared filters and views, protecting multiple threads
	/// from entering into the same view.
	/// (C) joins statements do not participate in filter and view reuse
	/// </p>
	/// </summary>
	public class StreamFactorySvcImpl : StreamFactoryService
	{
	    private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    // Using identify hash map - ignoring the equals semantics on filter specs
	    // Thus two filter specs objects are always separate entries in the map
	    private readonly IdentityDictionary<FilterSpecCompiled, Pair<EventStream, EPStatementHandleCallback>> eventStreamsIdentity;

	    // Using a reference-counted map for non-join statements
	    private readonly RefCountedMap<FilterSpecCompiled, Pair<EventStream, EPStatementHandleCallback>> eventStreamsRefCounted;

	    private readonly EventAdapterService eventAdapterService;

	    /// <summary>Ctor.</summary>
	    /// <param name="eventAdapterService">is the service to resolve event types</param>
	    public StreamFactorySvcImpl(EventAdapterService eventAdapterService)
	    {
	        this.eventStreamsRefCounted = new RefCountedMap<FilterSpecCompiled, Pair<EventStream, EPStatementHandleCallback>>();
	        this.eventStreamsIdentity = new IdentityDictionary<FilterSpecCompiled, Pair<EventStream, EPStatementHandleCallback>>();
	        this.eventAdapterService = eventAdapterService;
	    }

        /// <summary>
        /// See the method of the same name in {@link net.esper.view.stream.StreamFactoryService}. Always attempts to reuse an existing event stream.
        /// May thus return a new event stream or an existing event stream depending on whether filter criteria match.
        /// </summary>
        /// <param name="filterSpec">is the filter definition</param>
        /// <param name="filterService">filter service to activate filter if not already active</param>
        /// <param name="epStatementHandle">is the statement resource lock</param>
        /// <param name="isJoin">is indicatng whether the stream will participate in a join statement, information
        /// necessary for stream reuse and multithreading concerns</param>
        /// <returns>
        /// newly createdStatement event stream, not reusing existing instances
        /// </returns>
	    public EventStream CreateStream(FilterSpecCompiled filterSpec, FilterService filterService, EPStatementHandle epStatementHandle, bool isJoin)
	    {
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".createStream hashCode=" + filterSpec.GetHashCode() + " filter=" + filterSpec);
	        }

	        // Check if a stream for this filter already exists
	        Pair<EventStream, EPStatementHandleCallback> pair = null;
	        if (isJoin)
	        {
	            pair = eventStreamsIdentity.Fetch(filterSpec);
	        }
	        else
	        {
	            pair = eventStreamsRefCounted[filterSpec];
	        }

	        // If pair exists, either reference count or illegal state
	        if (pair != null)
	        {
	            if (isJoin)
	            {
	                throw new IllegalStateException("Filter spec object already found in collection");
	            }
	            else
	            {
	                log.Debug(".createStream filter already found");
	                eventStreamsRefCounted.Reference(filterSpec);
	                return pair.First;
	            }
	        }

	        // New event stream
	        EventType eventType = filterSpec.EventType;
	        EventStream eventStream = new ZeroDepthStream(eventType);

	        FilterHandleCallback filterCallback =
	            new FilterHandleCallbackImpl(
	                new FilterHandleCallbackDelegate(
	                    delegate(EventBean _event) { eventStream.Insert(_event); }));

	        EPStatementHandleCallback handle = new EPStatementHandleCallback(epStatementHandle, filterCallback);

	        // Store stream for reuse
	        pair = new Pair<EventStream, EPStatementHandleCallback>(eventStream, handle);
	        if (isJoin)
	        {
	        	eventStreamsIdentity[filterSpec] = pair;
	        }
	        else
	        {
	        	eventStreamsRefCounted[filterSpec] = pair;
	        }

	        // Activate filter
	        FilterValueSet filterValues = filterSpec.GetValueSet(null);
	        filterService.Add(filterValues, handle);

	        return eventStream;
	    }

        /// <summary>
        /// See the method of the same name in {@link net.esper.view.stream.StreamFactoryService}.
        /// </summary>
        /// <param name="filterSpec">is the filter definition</param>
        /// <param name="filterService">to be used to deactivate filter when the last event stream is dropped</param>
        /// <param name="isJoin">is indicatng whether the stream will participate in a join statement, information
        /// necessary for stream reuse and multithreading concerns</param>
	    public void DropStream(FilterSpecCompiled filterSpec, FilterService filterService, bool isJoin)
	    {
	        Pair<EventStream, EPStatementHandleCallback> pair = null;

	        if (isJoin)
	        {
	            pair = eventStreamsIdentity.Fetch(filterSpec);
	            if (pair == null)
	            {
	                throw new IllegalStateException("Filter spec object not in collection");
	            }
	            eventStreamsIdentity.Remove(filterSpec);
	            filterService.Remove(pair.Second);
	        }
	        else
	        {
	        	pair = eventStreamsRefCounted[filterSpec];
	            bool isLast = eventStreamsRefCounted.Dereference(filterSpec);
	            if (isLast)
	            {
	                filterService.Remove(pair.Second);
	            }
	        }
	    }
	}
} // End of namespace
