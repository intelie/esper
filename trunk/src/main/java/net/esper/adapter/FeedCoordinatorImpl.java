package net.esper.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.schedule.SchedulingService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An implementation of FeedCoordinator.
 */
public class FeedCoordinatorImpl extends AbstractReadableFeed implements FeedCoordinator
{
	private static final Log log = LogFactory.getLog(FeedCoordinatorImpl.class);
	
	private final Map<SendableEvent, ReadableFeed> eventsFromFeeds = new HashMap<SendableEvent, ReadableFeed>();
	private final Set<ReadableFeed> emptyFeeds = new HashSet<ReadableFeed>();
	private final EPAdapterManager manager;
	private final boolean usingEngineThread;
	
	/**
	 * Ctor.
	 * @param manager - the adapter manager that created this FeedCoordinator
	 * @param runtime - the runtime to send events into
	 * @param schedulingService - used for making callbacks
	 * @param usingEngineThread - true if the coordinator should set time by the scheduling service in the engine, 
	 *                            false if it should set time externally through the calling thread
	 */
	protected FeedCoordinatorImpl(EPAdapterManager manager, EPRuntime runtime, SchedulingService schedulingService, boolean usingEngineThread)
	{
		super(runtime, schedulingService, usingEngineThread);
		this.manager = manager;
		this.usingEngineThread = usingEngineThread;
	}

	
	/* (non-Javadoc)
	 * @see net.esper.adapter.ReadableFeed#read()
	 */
	public SendableEvent read() throws EPException
	{		
		log.debug(".read");
		pollEmptyFeeds();
		
		log.debug(".read eventsToSend.isEmpty==" + eventsToSend.isEmpty());
		log.debug(".read eventsFromFeeds.isEmpty==" + eventsFromFeeds.isEmpty());
		log.debug(".read emptyFeeds.isEmpty==" + emptyFeeds.isEmpty());
		
		if(eventsToSend.isEmpty() && eventsFromFeeds.isEmpty() && emptyFeeds.isEmpty())
		{
			stop();
		}
		
		if(stateManager.getState() == FeedState.DESTROYED || eventsToSend.isEmpty())
		{
			return null;
		}

		SendableEvent result = eventsToSend.first();

		replaceFirstEventToSend();
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.FeedCoordinator#add(net.esper.adapter.Feed)
	 */
	public void coordinate(FeedSpec feedSpec)
	{
		if(feedSpec == null)
		{
			throw new NullPointerException("FeedSpec cannot be null");
		}
		
		feedSpec.setUsingEngineThread(usingEngineThread);
		Feed feed = manager.createFeed(feedSpec);
		
		if(!(feed instanceof ReadableFeed))
		{
			throw new IllegalArgumentException("Cannot coordinate a Feed of type " + feed.getClass());
		}
		if(eventsFromFeeds.values().contains(feed) || emptyFeeds.contains(feed))
		{
			return;
		}
		addNewEvent((ReadableFeed)feed);
	}

	/**
	 * Does nothing.
	 */
	protected void close()
	{
		// Do nothing
	}

	/**
	 * Replace the first member of eventsToSend with the next 
	 * event returned by the read() method of the same Player that
	 * provided the first event.
	 */
	protected void replaceFirstEventToSend()
	{
		log.debug(".replaceFirstEventToSend");
		SendableEvent event = eventsToSend.first();
		eventsToSend.remove(event);
		addNewEvent(eventsFromFeeds.get(event));
		pollEmptyFeeds();
	}
	
	/**
	 * Reset all the changeable state of this ReadableFeed, as if it were just created.
	 */
	protected void reset()
	{
		eventsFromFeeds.clear();
		emptyFeeds.clear();
	}
	private void addNewEvent(ReadableFeed feed)
	{
		log.debug(".addNewEvent eventsFromFeeds==" + eventsFromFeeds);
		SendableEvent event = feed.read();
		if(event != null)
		{
			log.debug(".addNewEvent event==" + event);			
			eventsToSend.add(event);
			eventsFromFeeds.put(event, feed);
		}
		else	
		{
			if(feed.getState() == FeedState.DESTROYED)
			{
				eventsFromFeeds.values().removeAll(Collections.singleton(feed));
			}
			else
			{
				emptyFeeds.add(feed);
			}
		}
	}
	
	private void pollEmptyFeeds()
	{
		log.debug(".pollEmptyFeeds emptyFeeds.size==" + emptyFeeds.size());
		for(Iterator<ReadableFeed> iterator = emptyFeeds.iterator(); iterator.hasNext(); )
		{
			ReadableFeed feed = iterator.next();
			if(feed.getState() == FeedState.DESTROYED)
			{
				iterator.remove();
				continue;
			}
			
			SendableEvent event = feed.read();
			if(event != null)
			{
				eventsToSend.add(event);
				eventsFromFeeds.put(event, feed);
				iterator.remove();
			}
		}
	}
}
