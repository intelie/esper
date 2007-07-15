package net.esper.core;

import net.esper.client.EPStatement;
import net.esper.client.EPStatementState;
import net.esper.client.UpdateListener;
import net.esper.view.Viewable;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.dispatch.DispatchService;

import java.util.Iterator;
import java.util.Set;

/**
 * Statement SPI for statements operations for state transitions and internal management.
 */
public interface EPStatementSPI extends EPStatement
{
    /**
     * Returns the statement id.
     * @return statement id
     */
    public String getStatementId();

    /**
     * Returns the current set of listeners for read-only operations.
     * @return listener set
     */
    public EPStatementListenerSet getListenerSet();

    /**
     * Sets the statement listeners.
     * <p>
     * Care must be taken in the use of this method as unsynchronized modification to the
     * listeners of a statement can yield problems.
     * @param listeners set
     */
    public void setListeners(EPStatementListenerSet listeners);

    /**
     * Set statement state.
     * @param currentState new current state
     */
    public void setCurrentState(EPStatementState currentState);

    /**
     * Sets the parent view.
     * @param viewable is the statement viewable
     */
    public void setParentView(Viewable viewable);
}
