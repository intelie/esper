package com.espertech.esper.regression.client;

import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.pattern.MatchedEventConvertor;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.PatternExpressionUtil;
import com.espertech.esper.pattern.guard.Guard;
import com.espertech.esper.pattern.guard.GuardFactorySupport;
import com.espertech.esper.pattern.guard.GuardParameterException;
import com.espertech.esper.pattern.guard.Quitable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class MyCountToPatternGuardFactory extends GuardFactorySupport
{
    private static final Log log = LogFactory.getLog(MyCountToPatternGuardFactory.class);

    private ExprNode numCountToExpr;
    private MatchedEventConvertor convertor;

    public void setGuardParameters(List<ExprNode> guardParameters, MatchedEventConvertor convertor) throws GuardParameterException
    {
        String message = "Count-to guard takes a single integer-value expression as parameter";
        if (guardParameters.size() != 1)
        {
            throw new GuardParameterException(message);
        }

        if (guardParameters.get(0).getExprEvaluator().getType() != Integer.class)
        {
            throw new GuardParameterException(message);
        }

        this.numCountToExpr = guardParameters.get(0);
        this.convertor = convertor;
    }

    public Guard makeGuard(PatternContext context, MatchedEventMap beginState, Quitable quitable, Object stateNodeId, Object guardState)
    {
        Object parameter = PatternExpressionUtil.evaluate("Count-to guard", beginState, numCountToExpr, convertor, null);
        if (parameter == null)
        {
            throw new EPException("Count-to guard parameter evaluated to a null value");
        }

        Integer numCountTo = (Integer) parameter;
        return new MyCountToPatternGuard(numCountTo, quitable);
    }
}
