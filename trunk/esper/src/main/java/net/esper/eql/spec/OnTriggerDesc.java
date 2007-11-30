package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;
import net.esper.util.MetaDefItem;

/**
 * Specification for on-trigger statements.
 */
public abstract class OnTriggerDesc implements MetaDefItem
{
    private OnTriggerType onTriggerType;

    public OnTriggerDesc(OnTriggerType onTriggerType)
    {
        this.onTriggerType = onTriggerType;
    }

    public OnTriggerType getOnTriggerType()
    {
        return onTriggerType;
    }

    public void setOnTriggerType(OnTriggerType onTriggerType)
    {
        this.onTriggerType = onTriggerType;
    }
}
