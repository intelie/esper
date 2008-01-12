package net.esper.core;

import net.esper.view.View;

/**
 * Update dispatch views are required to indicate changes to listeners.
 */
public interface UpdateDispatchView extends View
{
    public void registerCallback(EPStatementListenerSetCallback callback);
}
