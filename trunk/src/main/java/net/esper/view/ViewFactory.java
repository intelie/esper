package net.esper.view;

import net.esper.event.EventType;
import net.esper.eql.core.ViewFactoryCallback;

import java.util.List;

public interface ViewFactory
{
    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException;
    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException;
    public boolean canProvideCapability(Class capabilityInterfaceClass);
    public void setProvideCapability(Class capabilityInterfaceClass, ViewFactoryCallback factoryCallback);
    public View makeView(ViewServiceContext viewServiceContext);
    public EventType getEventType();
    public boolean canReuse(View view);
}
