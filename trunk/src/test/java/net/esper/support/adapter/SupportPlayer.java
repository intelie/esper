package net.esper.support.adapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.adapter.Player;
import net.esper.adapter.SendableEvent;
import net.esper.client.EPException;


public class SupportPlayer implements Player
{
	private static final Log log = LogFactory.getLog(SupportPlayer.class);
	
	private SendableEvent event;
	private State state = State.NEW;

	public State getState()
	{
		return state;
	}

	public void pause() throws EPException
	{
		if(state != State.STOPPED)
		{
			state = State.PAUSED;
		}
		
	}

	public void resume() throws EPException
	{
		if(state == State.PAUSED)
		{
			state = State.RUNNING;
		}
		
	}

	public void start() throws EPException
	{
		if(state == State.NEW)
		{
			state = State.RUNNING;
		}
	}

	public void stop() throws EPException
	{
		state = State.STOPPED;
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
