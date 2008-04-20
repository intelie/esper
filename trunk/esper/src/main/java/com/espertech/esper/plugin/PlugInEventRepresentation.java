package com.espertech.esper.plugin;

public interface PlugInEventRepresentation
{
    public void init(PlugInEventRepresentationContext eventRepresentationContext);

    /**
     * Called when
     *   (1) a new event type is registered and the representationChildURI matches the representation URI
     *   (2) a new statement is created with an alias not yet associated with an event type
     * 
     * @return
     */
    public boolean acceptsType(PlugInEventTypeHandlerContext acceptTypeContext);

    /**
     * A handler handles a specific event type.
     * @param eventTypeContext
     * @return
     */
    public PlugInEventTypeHandler getTypeHandler(PlugInEventTypeHandlerContext eventTypeContext);


    public boolean acceptsEventBeanResolution(PlugInEventBeanReflectorContext context);

    public PlugInEventBeanFactory getEventBeanFactory(PlugInEventBeanReflectorContext uri);
}
