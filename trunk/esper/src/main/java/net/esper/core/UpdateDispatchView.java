package net.esper.core;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.View;
import net.esper.dispatch.Dispatchable;
import net.esper.dispatch.DispatchService;
import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.EventBeanUtility;
import net.esper.collection.SingleEventIterator;

import java.util.Set;
import java.util.LinkedList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public interface UpdateDispatchView extends Dispatchable, View
{
}
