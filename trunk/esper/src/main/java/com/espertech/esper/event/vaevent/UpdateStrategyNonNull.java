package com.espertech.esper.event.vaevent;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.util.NullableObject;

/**
 * Strategy for merging update properties using only non-null values.
 */
public class UpdateStrategyNonNull extends UpdateStrategyBase
{
    /**
     * Ctor.
     * @param spec the specification
     */
    public UpdateStrategyNonNull(RevisionSpec spec)
    {
        super(spec);
    }

    public void handleUpdate(boolean isBaseEventType,
                              RevisionStateMerge revisionState,
                              RevisionEventBeanMerge revisionEvent,
                              RevisionTypeDesc typesDesc)
    {
        EventBean underlyingEvent = revisionEvent.getUnderlyingFullOrDelta();

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
            if (value == null)
            {
                continue;
            }
            changeSetValues[index] = new NullableObject<Object>(value);
        }

        revisionState.setOverlays(changeSetValues);
    }
}
