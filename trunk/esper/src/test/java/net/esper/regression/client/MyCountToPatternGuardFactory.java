package net.esper.regression.client;

import net.esper.pattern.guard.*;
import net.esper.pattern.PatternContext;
import java.util.List;

public class MyCountToPatternGuardFactory extends GuardFactorySupport
{
    private int numCountTo;

    public void setGuardParameters(List<Object> guardParameters) throws GuardParameterException
    {
        if (guardParameters.size() != 1)
        {
            throw new GuardParameterException("Count-to guard takes a single integer parameter");
        }
        if (!(guardParameters.get(0) instanceof Integer))
        {
            throw new GuardParameterException("Count-to guard takes a single integer parameter");
        }
        numCountTo = (Integer) guardParameters.get(0);
    }

    public Guard makeGuard(PatternContext context, Quitable quitable, Object stateNodeId, Object guardState)
    {
        return new MyCountToPatternGuard(numCountTo, quitable);
    }
}
