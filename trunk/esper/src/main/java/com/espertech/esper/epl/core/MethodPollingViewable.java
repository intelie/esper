package com.espertech.esper.epl.core;

import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.db.DataCache;
import com.espertech.esper.epl.db.PollExecStrategy;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.join.PollResultIndexingStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.spec.MethodStreamSpec;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.view.HistoricalEventViewable;
import com.espertech.esper.view.View;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Polling-data provider that calls a static method on a class and passed parameters, and wraps the
 * results as POJO events. 
 */
public class MethodPollingViewable implements HistoricalEventViewable
{
    private final MethodStreamSpec methodStreamSpec;
    private final int myStreamNumber;
    private final PollExecStrategy pollExecStrategy;
    private final List<ExprNode> inputParameters;
    private final DataCache dataCache;
    private final EventType eventType;

    private ExprNode[] validatedExprNodes;

    /**
     * Ctor.
     * @param methodStreamSpec defines class and method names
     * @param myStreamNumber is the stream number
     * @param inputParameters the input parameter expressions
     * @param pollExecStrategy the execution strategy
     * @param dataCache the cache to use
     * @param eventType the type of event returned
     */
    public MethodPollingViewable(
                           MethodStreamSpec methodStreamSpec,
                           int myStreamNumber,
                           List<ExprNode> inputParameters,
                           PollExecStrategy pollExecStrategy,
                           DataCache dataCache,
                           EventType eventType)
    {
        this.methodStreamSpec = methodStreamSpec;
        this.myStreamNumber = myStreamNumber;
        this.inputParameters = inputParameters;
        this.pollExecStrategy = pollExecStrategy;
        this.dataCache = dataCache;
        this.eventType = eventType;
    }

    public void stop()
    {
        pollExecStrategy.destroy();
    }

    public void validate(StreamTypeService streamTypeService,
                         MethodResolutionService methodResolutionService,
                         TimeProvider timeProvider,
                         VariableService variableService) throws ExprValidationException
    {
        Class[] paramTypes = new Class[inputParameters.size()];
        int count = 0;
        validatedExprNodes = new ExprNode[inputParameters.size()];

        for (ExprNode exprNode : inputParameters)
        {
            ExprNode validated = exprNode.getValidatedSubtree(streamTypeService, methodResolutionService, null, timeProvider, variableService);
            validatedExprNodes[count] = validated;
            paramTypes[count] = validated.getType();
            count++;
        }

        // Try to resolve the method, also checking parameter types
		try
		{
			methodResolutionService.resolveMethod(methodStreamSpec.getClassName(), methodStreamSpec.getMethodName(), paramTypes);
		}
		catch(Exception e)
		{
            if (inputParameters.size() == 0)
            {
                throw new ExprValidationException("Method footprint does not match the number or type of expression parameters, expecting no parameters in method: " + e.getMessage());                
            }
            throw new ExprValidationException("Method footprint does not match the number or type of expression parameters, expecting a method where parameters are typed '" +
                    Arrays.toString(paramTypes) + "': " + e.getMessage());
		}
    }

    public EventTable[] poll(EventBean[][] lookupEventsPerStream, PollResultIndexingStrategy indexingStrategy)
    {
        pollExecStrategy.start();

        EventTable[] resultPerInputRow = new EventTable[lookupEventsPerStream.length];

        // Get input parameters for each row
        for (int row = 0; row < lookupEventsPerStream.length; row++)
        {
            Object[] lookupValues = new Object[inputParameters.size()];

            // Build lookup keys
            for (int valueNum = 0; valueNum < inputParameters.size(); valueNum++)
            {
                Object parameterValue = validatedExprNodes[valueNum].evaluate(lookupEventsPerStream[row], true);
                lookupValues[valueNum] = parameterValue;
            }

            // Get the result from cache
            EventTable result = dataCache.getCached(lookupValues);
            if (result != null)     // found in cache
            {
                resultPerInputRow[row] = result;
            }
            else        // not found in cache, get from actual polling (db query)
            {
                try
                {
                    // Poll using the polling execution strategy and lookup values
                    List<EventBean> pollResult = pollExecStrategy.poll(lookupValues);

                    // index the result, if required, using an indexing strategy
                    EventTable indexTable = indexingStrategy.index(pollResult, dataCache.isActive());

                    // assign to row
                    resultPerInputRow[row] = indexTable;

                    // save in cache
                    dataCache.put(lookupValues, indexTable);
                }
                catch (EPException ex)
                {
                    pollExecStrategy.done();
                    throw ex;
                }
            }
        }

        pollExecStrategy.done();

        return resultPerInputRow;
    }

    public View addView(View view)
    {
        return view;
    }

    public List<View> getViews()
    {
        return new LinkedList<View>();
    }

    public boolean removeView(View view)
    {
        throw new UnsupportedOperationException("Subviews not supported");
    }

    public boolean hasViews()
    {
        return false;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        throw new UnsupportedOperationException("Iterator not supported");
    }
}
