package net.esper.support.pattern;

import net.esper.pattern.guard.GuardFactory;
import net.esper.pattern.guard.Guard;
import net.esper.pattern.guard.Quitable;
import net.esper.pattern.guard.GuardParameterException;
import net.esper.pattern.PatternContext;

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
