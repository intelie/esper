package com.espertech.esper.event.rev;

import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.named.NamedWindowIndexRepository;
import com.espertech.esper.epl.named.NamedWindowRootView;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.view.StatementStopCallback;
import com.espertech.esper.view.StatementStopService;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.client.ConfigurationRevisionEventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Base revision processor.
 */
public abstract class RevisionProcessorBase implements RevisionProcessor
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
     * Map of participating type to descriptor.
     */
    protected Map<EventType, RevisionTypeDesc> typeDescriptors;

    /**
     * Ctor.
     * @param revisionSpec specification
     * @param revisionEventTypeAlias alias of event type
     */
    protected RevisionProcessorBase(RevisionSpec revisionSpec, String revisionEventTypeAlias)
    {
        this.revisionSpec = revisionSpec;
        this.revisionEventTypeAlias = revisionEventTypeAlias;
        this.typeDescriptors = new HashMap<EventType, RevisionTypeDesc>();
    }

    public RevisionEventType getEventType()
    {
        return revisionEventType;
    }

    public String getRevisionEventTypeAlias()
    {
        return revisionEventTypeAlias;
    }

    public boolean validateRevisionableEventType(EventType eventType)
    {
        if (eventType == revisionSpec.getBaseEventType())
        {
            return true;
        }
        if (typeDescriptors.containsKey(eventType))
        {
            return true;
        }

        if (eventType == null)
        {
            return false;
        }

        // Check all the supertypes to see if one of the matches the full or delta types
        Iterator<EventType> deepSupers = eventType.getDeepSuperTypes();
        if (deepSupers == null)
        {
            return false;
        }

        EventType type;
        for (;deepSupers.hasNext();)
        {
            type = deepSupers.next();
            if (type == revisionSpec.getBaseEventType())
            {
                return true;
            }
            if (typeDescriptors.containsKey(type))
            {
                return true;
            }
        }

        return false;
    }
}
