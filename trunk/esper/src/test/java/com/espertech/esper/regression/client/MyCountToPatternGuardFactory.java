package com.espertech.esper.regression.client;

import com.espertech.esper.pattern.guard.*;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.epl.variable.VariableReader;
import com.espertech.esper.client.EPException;
import com.espertech.esper.type.VariableParameter;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyCountToPatternGuardFactory extends GuardFactorySupport
{
    private static final Log log = LogFactory.getLog(MyCountToPatternGuardFactory.class);

    private String variableName;
    private VariableReader variableReader;
    private int numCountTo;

    public void setGuardParameters(List<Object> guardParameters) throws GuardParameterException
    {
        String message = "Count-to guard takes a single integer parameter or variable name of an integer variable";

        if (guardParameters.size() != 1)
        {
            throw new GuardParameterException(message);
        }

        if (guardParameters.get(0) instanceof Integer)
        {
            numCountTo = (Integer) guardParameters.get(0);
        }
        else if (guardParameters.get(0) instanceof VariableParameter)
        {
            Object param = guardParameters.get(0);
            variableName = ((VariableParameter) param).getVariableName();
        }
        else
        {
            throw new GuardParameterException(message);
        }
    }

    public Guard makeGuard(PatternContext context, Quitable quitable, Object stateNodeId, Object guardState)
    {
        if (variableName != null)
        {
            if (variableReader == null)
            {
                variableReader = context.getVariableService().getReader(variableName);
                if (variableReader == null)
                {
                    throw new EPException("Variable by name " + variableName + " was not found");
                }
            }

            Integer value = (Integer) variableReader.getValue();
            int numCountTo = 0;
            if (value == null)
            {
                log.error("Invalid null value for variable " + variableName + ", using 0 as default");
            }
            else
            {
                numCountTo = value;
            }
            return new MyCountToPatternGuard(numCountTo, quitable);
        }
        else
        {
            return new MyCountToPatternGuard(numCountTo, quitable);
        }
    }
}
