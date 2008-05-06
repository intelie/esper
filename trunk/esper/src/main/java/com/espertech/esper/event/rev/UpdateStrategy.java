package com.espertech.esper.event.rev;

public interface UpdateStrategy
{
    public void handleUpdate(boolean isFullEventType,
                              RevisionStateMerge revisionState,
                              RevisionEventBeanMerge revisionEvent,
                              RevisionTypeDescMerge typesDesc);
}
