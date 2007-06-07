/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.emit;

import net.esper.client.EmittedListener;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of the event emit service.
 */
public final class EmitServiceImpl implements EmitService
{
    private final HashMap<String, List<EmittedListener>> channelEmitListeners = new HashMap<String, List<EmittedListener>>();
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

        // Check if the listener already exists, to make sure the same listener
        // doesn't subscribe twice to the same or the default channel
        for (Map.Entry<String, List<EmittedListener>> entry : channelEmitListeners.entrySet())
        {
            if (entry.getValue().contains(listener))
            {
                // If already subscribed to the default channel, do not add
                // If already subscribed to the same channel, do not add
                if ((entry.getKey() == null) ||
                    ((channel != null) && (channel.equals(entry.getKey()))))
                {
                    channelEmitListenersRWLock.writeLock().unlock();
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
        List<EmittedListener> listeners = channelEmitListeners.get(channel);
        if (listeners == null)
        {
            listeners = new LinkedList<EmittedListener>();
            channelEmitListeners.put(channel, listeners);
        }

        listeners.add(listener);

        channelEmitListenersRWLock.writeLock().unlock();
    }

    public final void clearListeners()
    {
        channelEmitListenersRWLock.writeLock().lock();
        channelEmitListeners.clear();
        channelEmitListenersRWLock.writeLock().unlock();
    }

    public final void emitEvent(Object object, String channel)
    {
        channelEmitListenersRWLock.readLock().lock();

        // Emit to specific channel first
        if (channel != null)
        {
            List<EmittedListener> listeners = channelEmitListeners.get(channel);
            if (listeners != null)
            {
                for (EmittedListener listener : listeners)
                {
                    listener.emitted(object);
                }
            }
        }

        // Emit to default channel if there are any listeners
        List<EmittedListener> listeners = channelEmitListeners.get(null);
        if (listeners != null)
        {
            for (EmittedListener listener : listeners)
            {
                listener.emitted(object);
            }
        }

        channelEmitListenersRWLock.readLock().unlock();

        numEventsEmitted.incrementAndGet();
    }

    public final int getNumEventsEmitted()
    {
        return numEventsEmitted.get();
    }
}
