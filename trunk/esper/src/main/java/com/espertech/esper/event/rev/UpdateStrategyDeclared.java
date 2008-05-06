package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.util.NullableObject;

public class UpdateStrategyDeclared extends UpdateStrategyBase
{
    public UpdateStrategyDeclared(RevisionSpec spec)
    {
        super(spec);
    }

    public void handleUpdate(boolean isFullEventType,
                              RevisionStateMerge revisionState,
                              RevisionEventBeanMerge revisionEvent,
                              RevisionTypeDescMerge typesDesc)
    {
        EventBean underlyingEvent = revisionEvent.getUnderlyingFullOrDelta();

        // Previously-seen full event
        if (isFullEventType)
        {
            // If delta types don't add properties, simply set the overlay to null
            NullableObject<Object>[] changeSetValues;
            if (!spec.isDeltaTypesAddProperties())
            {
                changeSetValues = null;
            }
            // If delta types do add properties, set a new overlay
            else
            {
                changeSetValues = revisionState.getOverlays();
                if (changeSetValues == null)    // optimization - the full event sets it to null, deltas all get a new one
                {
                    changeSetValues = new NullableObject[spec.getChangesetPropertyNames().length];
                }
                else
                {
                    changeSetValues = arrayCopy(changeSetValues);   // preserve the last revisions
                }

                // reset properties not contributed by any delta, leaving all delta-contributed properties in place
                boolean[] changesetPropertyDeltaContributed = spec.getChangesetPropertyDeltaContributed();
                for (int i = 0; i < changesetPropertyDeltaContributed.length; i++)
                {
                    // if contributed then leave the value, else override
                    if (!changesetPropertyDeltaContributed[i])
                    {
                        changeSetValues[i] = null;
                    }
                }
            }
            revisionState.setOverlays(changeSetValues);
            revisionState.setFullEventUnderlying(underlyingEvent);
        }
        // Delta event to existing full event merge
        else
        {
            NullableObject<Object>[] changeSetValues = revisionState.getOverlays();

            if (changeSetValues == null)    // optimization - the full event sets it to null, deltas all get a new one
            {
                changeSetValues = new NullableObject[spec.getChangesetPropertyNames().length];
            }
            else
            {
                changeSetValues = arrayCopy(changeSetValues);   // preserve the last revisions
            }

            // apply all properties of the delta event
            int[] indexes = typesDesc.getChangesetPropertyIndex();
            EventPropertyGetter[] getters = typesDesc.getChangesetPropertyGetters();
            for (int i = 0; i < indexes.length; i++)
            {
                int index = indexes[i];
                Object value = getters[i].get(underlyingEvent);
                changeSetValues[index] = new NullableObject<Object>(value);
            }

            revisionState.setOverlays(changeSetValues);
        }        
    }
}
