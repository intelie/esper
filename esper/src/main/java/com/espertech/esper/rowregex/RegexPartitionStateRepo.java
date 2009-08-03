package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;

public interface RegexPartitionStateRepo
{
    public RegexPartitionState getState(MultiKeyUntyped key);
    public RegexPartitionState getState(EventBean event, boolean isCollect);
    public void removeOld(EventBean[] events, boolean isEmpty, boolean found[]);
    public RegexPartitionStateRepo copyForIterate();
}
