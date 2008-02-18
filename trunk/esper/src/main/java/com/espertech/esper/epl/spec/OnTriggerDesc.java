package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;

/**
 * Specification for on-trigger statements.
 */
public abstract class OnTriggerDesc implements MetaDefItem
{
    private OnTriggerType onTriggerType;

    /**
     * Ctor.
     * @param onTriggerType the type of on-trigger
     */
    public OnTriggerDesc(OnTriggerType onTriggerType)
    {
        this.onTriggerType = onTriggerType;
    }

    /**
     * Returns the type of the on-trigger statement.
     * @return trigger type
     */
    public OnTriggerType getOnTriggerType()
    {
        return onTriggerType;
    }
}
