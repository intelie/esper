package net.esper.eql.spec;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.StreamTypeServiceImpl;
import net.esper.eql.expression.*;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.filter.*;
import net.esper.type.RelationalOpEnum;
import net.esper.view.ViewSpec;
import net.esper.util.JavaClassHelper;

import java.util.*;

public class FilterStreamSpecRaw extends StreamSpecBase implements StreamSpecRaw
{
    private FilterSpecRaw rawFilterSpec;

    public FilterStreamSpecRaw(FilterSpecRaw rawFilterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        super(optionalStreamName, viewSpecs);
        this.rawFilterSpec = rawFilterSpec;
    }

    public FilterSpecRaw getRawFilterSpec()
    {
        return rawFilterSpec;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      AutoImportService autoImportService)
            throws ExprValidationException
    {
        // Determine the event type
        String eventName = rawFilterSpec.getEventTypeAlias();
        EventType eventType = resolveType(eventName, eventAdapterService);

        // Validate all nodes, make sure each returns a boolean and types are good;
        // Also decompose all AND super nodes into individual expressions
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType}, new String[] {"s0"});
        
        FilterSpecCompiled spec = FilterSpecCompiler.makeFilterSpec(eventType, rawFilterSpec.getFilterExpressions(), null,
                streamTypeService, autoImportService);
        
        return new FilterStreamSpecCompiled(spec, this.getViewSpecs(), this.getOptionalStreamName());
    }

    protected static EventType resolveType(String eventName, EventAdapterService eventAdapterService)
            throws ExprValidationException
    {
        EventType eventType = eventAdapterService.getEventType(eventName);

        // The type is not known yet, attempt to add as a JavaBean type with the same alias
        if (eventType == null)
        {
            try
            {
                eventType = eventAdapterService.addBeanType(eventName, eventName);
            }
            catch (EventAdapterException ex)
            {
                throw new ExprValidationException("Failed to resolve event type: " + ex.getMessage());
            }
        }

        return eventType;
    }    
}
