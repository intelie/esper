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
 * Statement SPI for internal use.
 */
public interface EPStatementSPI extends EPStatement
{
    public String getStatementId();
    public Set<UpdateListener> getListeners();
    public void setListeners(Set<UpdateListener> listeners);
    public void initialize();
    public void setCurrentState(EPStatementState currentState);
    public void setParentView(Viewable viewable);
}
