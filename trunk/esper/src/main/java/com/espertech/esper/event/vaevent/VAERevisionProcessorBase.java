package com.espertech.esper.event.vaevent;

import com.espertech.esper.event.*;
import com.espertech.esper.epl.expression.ExprValidationException;

import java.util.*;

/**
 * Base revision processor.
 */
public abstract class VAERevisionProcessorBase implements ValueAddEventProcessor
{
    /**
     * Revision type specification.
     */
    protected final RevisionSpec revisionSpec;

    /**
     * Alias of type.
     */
    protected final String revisionEventTypeAlias;

    /**
     * Revision event type.
     */
    protected RevisionEventType revisionEventType;

    /**
     * For interogating nested properties.
     */
    protected EventAdapterService eventAdapterService;

    /**
     * Map of participating type to descriptor.
     */
    protected Map<EventType, RevisionTypeDesc> typeDescriptors;

    /**
     * Ctor.
     * @param revisionSpec specification
     * @param revisionEventTypeAlias alias of event type
     * @param eventAdapterService for nested property handling
     */
    protected VAERevisionProcessorBase(RevisionSpec revisionSpec, String revisionEventTypeAlias, EventAdapterService eventAdapterService)
    {
        this.revisionSpec = revisionSpec;
        this.revisionEventTypeAlias = revisionEventTypeAlias;
        this.eventAdapterService = eventAdapterService;
        this.typeDescriptors = new HashMap<EventType, RevisionTypeDesc>();
    }

    public RevisionEventType getValueAddEventType()
    {
        return revisionEventType;
    }

    public void validateEventType(EventType eventType) throws ExprValidationException
    {
        if (eventType == revisionSpec.getBaseEventType())
        {
            return;
        }
        if (typeDescriptors.containsKey(eventType))
        {
            return;
        }

        if (eventType == null)
        {
            throw new ExprValidationException(getMessage());
        }

        // Check all the supertypes to see if one of the matches the full or delta types
        Iterator<EventType> deepSupers = eventType.getDeepSuperTypes();
        if (deepSupers == null)
        {
            throw new ExprValidationException(getMessage());
        }

        EventType type;
        for (;deepSupers.hasNext();)
        {
            type = deepSupers.next();
            if (type == revisionSpec.getBaseEventType())
            {
                return;
            }
            if (typeDescriptors.containsKey(type))
            {
                return;
            }
        }

        throw new ExprValidationException(getMessage());
    }

    private String getMessage()
    {
        return "Selected event type is not a valid base or delta event type of revision event type '"
                + revisionEventTypeAlias + "'";
    }
}
