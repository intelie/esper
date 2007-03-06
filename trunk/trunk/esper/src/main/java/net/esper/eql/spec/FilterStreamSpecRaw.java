package net.esper.eql.spec;

import net.esper.view.ViewSpec;
import net.esper.core.EPServicesContext;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.event.EventAdapterException;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.StreamTypeServiceImpl;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.expression.ExprNode;
import net.esper.filter.FilterSpec;
import net.esper.util.UuidGenerator;

import java.util.List;

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

        // Validate all nodes
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType}, new String[] {"s0"});
        for (ExprNode node : rawFilterSpec.getFilterExpressions())
        {
            node.validate(streamTypeService, autoImportService, null);

            if (node.getType() != Boolean.class)
            {
                
            }
        }
        FilterSpec spec = new FilterSpec(eventType, null);
        return new FilterStreamSpecCompiled(spec, this.getViewSpecs(), this.getOptionalStreamName()); 
    }    
}
