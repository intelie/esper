package com.espertech.esper.support.pattern;

import com.espertech.esper.pattern.EvalStateNodeNumber;
import com.espertech.esper.pattern.guard.GuardFactory;
import com.espertech.esper.pattern.guard.Guard;
import com.espertech.esper.pattern.guard.Quitable;
import com.espertech.esper.pattern.guard.GuardParameterException;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventConvertor;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.List;

public class SupportGuardFactory implements GuardFactory
{
    public void setGuardParameters(List<ExprNode> guardParameters, MatchedEventConvertor convertor) throws GuardParameterException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Guard makeGuard(PatternContext context, MatchedEventMap beginState, Quitable quitable, EvalStateNodeNumber stateNodeId, Object guardState)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
