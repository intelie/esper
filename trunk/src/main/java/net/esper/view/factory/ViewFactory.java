package net.esper.view.factory;

import net.esper.event.EventType;
import net.esper.view.Viewable;
import net.esper.view.ViewServiceContext;
import net.esper.view.View;

import java.util.List;

public interface ViewFactory
{
    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException;
    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException;
    public boolean canProvideCapability(Class capabilityInterfaceClass);
    public void setProvideCapability(Class capabilityInterfaceClass);
    public View makeView(ViewServiceContext viewServiceContext);
    public EventType getEventType();
    public boolean canReuse(View view);

    /**
     * If View retains a reference to ViewFactoryOld, then JavaBean requirement could be lifted:
     *      no default constructor needed
     *      no public getter and setter methods on View
     *      dependency on common.beanutils removed
     */
}
