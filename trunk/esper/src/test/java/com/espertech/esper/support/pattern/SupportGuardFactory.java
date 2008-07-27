package com.espertech.esper.support.pattern;

import com.espertech.esper.pattern.guard.GuardFactory;
import com.espertech.esper.pattern.guard.Guard;
import com.espertech.esper.pattern.guard.Quitable;
import com.espertech.esper.pattern.guard.GuardParameterException;
import com.espertech.esper.pattern.PatternContext;

import java.util.List;

public class SupportGuardFactory implements GuardFactory
{
    public void setGuardParameters(List<Object> guardParameters) throws GuardParameterException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Guard makeGuard(PatternContext context, Quitable quitable, Object stateObjectId, Object guardState)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
