package com.espertech.esper.epl.view;

import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.core.ExtensionServicesContext;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNodeVariableVisitor;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.expression.ExprNodeIdentifierVisitor;
import com.espertech.esper.epl.spec.OnTriggerSetAssignment;
import com.espertech.esper.epl.variable.VariableChangeCallback;
import com.espertech.esper.epl.variable.VariableReadWritePackage;
import com.espertech.esper.epl.variable.VariableReader;
import com.espertech.esper.schedule.ScheduleHandleCallback;
import com.espertech.esper.schedule.ScheduleSlot;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventAdapterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class OutputConditionExpression implements OutputCondition, VariableChangeCallback
{
    private static final Log log = LogFactory.getLog(OutputConditionExpression.class);
    private final ExprNode whenExpressionNode;
    private final OutputCallback outputCallback;
    private final ScheduleSlot scheduleSlot;
    private final StatementContext context;
    private final VariableReadWritePackage variableReadWritePackage;

    private boolean isCallbackScheduled;
    private boolean ignoreVariableCallbacks;
    private Map<String, Object> builtinProperties;
    private EventBean[] eventsPerStream;
    private EventType builtinPropertiesEventType;

    // ongoing builtin properties
    private int totalNewEventsCount;
    private int totalOldEventsCount;
    private Long lastOutputTimestamp;

    public OutputConditionExpression(ExprNode whenExpressionNode, List<OnTriggerSetAssignment> assignments, StatementContext context, OutputCallback outputCallback)
            throws ExprValidationException
    {
        this.whenExpressionNode = whenExpressionNode;
        this.outputCallback = outputCallback;
        this.scheduleSlot = context.getScheduleBucket().allocateSlot();
        this.context = context;
        this.eventsPerStream = new EventBean[1];

        // determine if using variables
        ExprNodeVariableVisitor variableVisitor = new ExprNodeVariableVisitor();
        whenExpressionNode.accept(variableVisitor);
        Set<String> variableNames = variableVisitor.getVariableNames();

        // determine if using properties
        ExprNodeIdentifierVisitor propertyVisitor = new ExprNodeIdentifierVisitor(false);
        whenExpressionNode.accept(propertyVisitor);
        if (!propertyVisitor.getExprProperties().isEmpty())
        {
            builtinProperties = new HashMap<String, Object>();
            builtinPropertiesEventType = getBuiltInEventType(context.getEventAdapterService());
            lastOutputTimestamp = context.getSchedulingService().getTime();
        }

        if (variableNames != null)
        {
            // if using variables, register a callback on the change of the variable
            for (String variableName : variableNames)
            {
                VariableReader reader = context.getVariableService().getReader(variableName);
                context.getVariableService().registerCallback(reader.getVariableNumber(), this);
            }
        }

        if (assignments != null)
        {
            variableReadWritePackage = new VariableReadWritePackage(assignments, context.getVariableService());
        }
        else
        {
            variableReadWritePackage = null;
        }
    }

    public void updateOutputCondition(int newEventsCount, int oldEventsCount)
    {
        this.totalNewEventsCount += newEventsCount;
        this.totalOldEventsCount += oldEventsCount;

        boolean isOutput = evaluate();
        if (isOutput)
        {
            outputCallback.continueOutputProcessing(true, false);
            resetBuiltinProperties();
        }
    }

    public void update(Object newValue, Object oldValue)
    {
        if (ignoreVariableCallbacks)
        {
            log.debug(".update Ignoring variable callback");
            return;
        }

        context.getVariableService().setLocalVersion();
        boolean isOutput = evaluate();
        if ((isOutput) && (!isCallbackScheduled))
        {
            scheduleCallback();
        }
    }

    private boolean evaluate()
    {
        if (builtinProperties != null)
        {
            builtinProperties.put("count_insert", totalNewEventsCount);
            builtinProperties.put("count_remove", totalOldEventsCount);
            builtinProperties.put("last_output_timestamp", lastOutputTimestamp);
            eventsPerStream[0] = context.getEventAdapterService().createMapFromValues(builtinProperties, builtinPropertiesEventType);
        }

        boolean result = false;
        Boolean output = (Boolean) whenExpressionNode.evaluate(eventsPerStream, true);
        if ((output != null) && (output))
        {
            result = true;
        }

        return result;
    }

    private void scheduleCallback()
    {
    	isCallbackScheduled = true;
        long current = context.getSchedulingService().getTime();

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".scheduleCallback Scheduled new callback for " +
                    " afterMsec=" + 0 +
                    " now=" + current);
        }

        ScheduleHandleCallback callback = new ScheduleHandleCallback() {
            public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
            {
                OutputConditionExpression.this.isCallbackScheduled = false;
                OutputConditionExpression.this.outputCallback.continueOutputProcessing(true, false);
                resetBuiltinProperties();
            }
        };
        EPStatementHandleCallback handle = new EPStatementHandleCallback(context.getEpStatementHandle(), callback);
        context.getSchedulingService().add(0, handle, scheduleSlot);

        // execute assignments
        if (variableReadWritePackage != null)
        {
            ignoreVariableCallbacks = true;
            try {
                variableReadWritePackage.writeVariables(context.getVariableService(), null, null);
            }
            finally {
                ignoreVariableCallbacks = false;
            }
        }
    }

    public static EventType getBuiltInEventType(EventAdapterService eventAdapterService)
    {
        Map<String, Object> outputLimitProperties = new HashMap<String, Object>();
        outputLimitProperties.put("count_insert", Integer.class);
        outputLimitProperties.put("count_remove", Integer.class);
        outputLimitProperties.put("last_output_timestamp", Long.class);
        return eventAdapterService.createAnonymousMapType(outputLimitProperties);
    }

    private void resetBuiltinProperties()
    {
        if (builtinProperties  != null)
        {
            totalNewEventsCount = 0;
            totalOldEventsCount = 0;
            lastOutputTimestamp = context.getSchedulingService().getTime();
        }
    }
}
