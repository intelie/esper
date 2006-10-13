package net.esper.support.adapter;

import net.esper.adapter.FeedState;
import net.esper.adapter.FeedStateManager;
import net.esper.adapter.ReadableFeed;
import net.esper.adapter.SendableEvent;
import net.esper.client.EPException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SupportReadableFeed implements ReadableFeed
{
	private static final Log log = LogFactory.getLog(SupportReadableFeed.class);
	
	private SendableEvent event;
	private FeedStateManager stateManager = new FeedStateManager();

	public FeedState getState()
	{
		return stateManager.getState();
	}

	public void pause() throws EPException
	{
		stateManager.pause();
	}

	public void resume() throws EPException
	{
		stateManager.resume();
	}

	public void start() throws EPException
	{
		stateManager.start();
	}

	public void stop() throws EPException
	{
		stateManager.stop();
	}
	
	public void destroy() throws EPException
	{
		stateManager.destroy();
	}

	public SendableEvent read() throws EPException
	{
		log.debug(".read result==" + event);
		SendableEvent result = event;
		event = null;
		return result;
	}
	
	public void setEvent(SendableEvent event)
	{
		log.debug(".setEvent sendTime==" + event.getSendTime());
		this.event = event;
	}

}
