package net.esper.core;

import net.esper.client.StatementAwareUpdateListener;
import net.esper.client.UpdateListener;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProvider;
import net.esper.collection.SingleEventIterator;
import net.esper.dispatch.DispatchService;
import net.esper.dispatch.Dispatchable;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.event.EventType;
import net.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Update dispatch views are required to indicate changes to listeners.
 */
public interface EPStatementListenerSetCallback
{
    public void newListenerSet(EPStatementListenerSet epStatementListenerSet);
}
