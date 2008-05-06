package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.util.NullableObject;
import com.espertech.esper.client.ConfigurationRevisionEventType;

public abstract class UpdateStrategyBase implements UpdateStrategy
{
    protected final RevisionSpec spec;

    protected UpdateStrategyBase(RevisionSpec spec)
    {
        this.spec = spec;
    }

    protected static NullableObject<Object>[] arrayCopy(NullableObject<Object>[] array)
    {
        if (array == null)
        {
            return null;
        }
        NullableObject<Object>[] result = (NullableObject<Object>[]) new NullableObject[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }
}
