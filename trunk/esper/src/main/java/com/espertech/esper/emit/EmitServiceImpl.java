/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.emit;

import com.espertech.esper.client.EmittedListener;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of the event emit service.
 */
public final class EmitServiceImpl implements EmitService
{
    private final HashMap<String, ArrayDequeJDK6Backport<EmittedListener>> channelEmitListeners = new HashMap<String, ArrayDequeJDK6Backport<EmittedListener>>();
    private final ReadWriteLock channelEmitListenersRWLock = new ReentrantReadWriteLock();
    private final AtomicInteger numEventsEmitted = new AtomicInteger();

    /**
     * Constructor.
     */
    protected EmitServiceImpl()
    {
    }

    public final void addListener(EmittedListener listener, String channel)
    {
        channelEmitListenersRWLock.writeLock().lock();

        try
        {
            // Check if the listener already exists, to make sure the same listener
            // doesn't subscribe twice to the same or the default channel
            for (Map.Entry<String, ArrayDequeJDK6Backport<EmittedListener>> entry : channelEmitListeners.entrySet())
            {
                if (entry.getValue().contains(listener))
                {
                    // If already subscribed to the default channel, do not add
                    // If already subscribed to the same channel, do not add
                    if ((entry.getKey() == null) ||
                        ((channel != null) && (channel.equals(entry.getKey()))))
                    {
                        return;
                    }

                    // If subscribing to default channel, remove from existing channel
                    if (channel == null)
                    {
                        entry.getValue().remove(listener);
                    }
                }
            }

            // Add listener, its a new listener or new channel for an existing listener
            ArrayDequeJDK6Backport<EmittedListener> listeners = channelEmitListeners.get(channel);
            if (listeners == null)
            {
                listeners = new ArrayDequeJDK6Backport<EmittedListener>();
                channelEmitListeners.put(channel, listeners);
            }

            listeners.add(listener);
        }
        finally
        {
            channelEmitListenersRWLock.writeLock().unlock();
        }
    }

    public final void clearListeners()
    {
        channelEmitListenersRWLock.writeLock().lock();
        try
        {
            channelEmitListeners.clear();
        }
        finally
        {
            channelEmitListenersRWLock.writeLock().unlock();
        }
    }

    public final void emitEvent(Object object, String channel)
    {
        channelEmitListenersRWLock.readLock().lock();

        try
        {
            // Emit to specific channel first
            if (channel != null)
            {
                ArrayDequeJDK6Backport<EmittedListener> listeners = channelEmitListeners.get(channel);
                if (listeners != null)
                {
                    for (EmittedListener listener : listeners)
                    {
                        listener.emitted(object);
                    }
                }
            }

            // Emit to default channel if there are any listeners
            ArrayDequeJDK6Backport<EmittedListener> listeners = channelEmitListeners.get(null);
            if (listeners != null)
            {
                for (EmittedListener listener : listeners)
                {
                    listener.emitted(object);
                }
            }
        }
        finally
        {
            channelEmitListenersRWLock.readLock().unlock();
        }

        numEventsEmitted.incrementAndGet();
    }

    public final int getNumEventsEmitted()
    {
        return numEventsEmitted.get();
    }
}
