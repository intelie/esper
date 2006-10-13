package net.esper.adapter;

/**
 * The FeedStateManager defines the valid transitions between different FeedStates.
 */
public class FeedStateManager
{
	private FeedState state = FeedState.OPENED;
	
	/**
	 * @return the state
	 */
	public FeedState getState()
	{
		return state;
	}
	
	/**
	 * Transition into the STARTED state (from the OPENED state).
	 * @throws IllegalStateTransitionException if the transition is not allowed
	 */
	public void start() throws IllegalStateTransitionException
	{
		if(state != FeedState.OPENED)
		{
			throw new IllegalStateTransitionException("Cannot start from the " + state + " state");
		}
		state = FeedState.STARTED;
	}
	
	/**
	 * Transition into the OPENED state.
	 * @throws IllegalStateTransitionException if the transition isn't allowed
	 */
	public void stop() throws IllegalStateTransitionException
	{
		if(state != FeedState.STARTED && state != FeedState.PAUSED)
		{
			throw new IllegalStateTransitionException("Cannot stop from the " + state + " state");
		}
		state = FeedState.OPENED;
	}
	
	/**
	 * Transition into the PAUSED state.
	 * @throws IllegalStateTransitionException if the transition isn't allowed
	 */
	public void pause() throws IllegalStateTransitionException
	{
		if(state != FeedState.STARTED)
		{
			throw new IllegalStateTransitionException("Cannot pause from the " + state + " state");
		}
		state = FeedState.PAUSED;
	}
	
	/**
	 * Transition into the STARTED state (from the PAUSED state).
	 * @throws IllegalStateTransitionException
	 */
	public void resume() throws IllegalStateTransitionException
	{
		if(state != FeedState.PAUSED)
		{
			throw new IllegalStateTransitionException("Cannot resume from the " + state + " state");
		}
		state = FeedState.STARTED;
	}
	
	/**
	 * Transition into the DESTROYED state.
	 * @throws IllegalStateTransitionException if the transition isn't allowed
	 */
	public void destroy() throws IllegalStateTransitionException
	{
		if(state == FeedState.DESTROYED)
		{
			throw new IllegalStateTransitionException("Cannot destroy from the " + state + " state");
		}
		state = FeedState.DESTROYED;
	}
}
