/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.db;

import com.espertech.esper.client.ConfigurationInformation;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.IterablesArrayIterator;
import com.espertech.esper.epl.core.EngineImportService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.join.pollindex.PollResultIndexingStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTableList;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.view.HistoricalEventViewable;
import com.espertech.esper.view.View;

import java.util.*;

/**
 * Implements a poller viewable that uses a polling strategy, a cache and
 * some input parameters extracted from event streams to perform the polling.
 */
public class DatabasePollingViewable implements HistoricalEventViewable
{
    private final int myStreamNumber;
    private final PollExecStrategy pollExecStrategy;
    private final List<String> inputParameters;
    private final DataCache dataCache;
    private final EventType eventType;
    private final ThreadLocal<DataCache> dataCacheThreadLocal = new ThreadLocal<DataCache>();

    private ExprEvaluator[] evaluators;
    private SortedSet<Integer> subordinateStreams;
    private ExprEvaluatorContext exprEvaluatorContext;

    private static final EventBean[][] NULL_ROWS;
    static {
        NULL_ROWS = new EventBean[1][];
        NULL_ROWS[0] = new EventBean[1];
    }
    private static final PollResultIndexingStrategy iteratorIndexingStrategy = new PollResultIndexingStrategy()
    {
        public EventTable index(List<EventBean> pollResult, boolean isActiveCache)
        {
            return new UnindexedEventTableList(pollResult);
        }

        public String toQueryPlan() {
            return this.getClass().getSimpleName() + " unindexed";
        }
    };

    /**
     * Ctor.
     * @param myStreamNumber is the stream number of the view
     * @param inputParameters are the event property names providing input parameter keys
     * @param pollExecStrategy is the strategy to use for retrieving results
     * @param dataCache is looked up before using the strategy
     * @param eventType is the type of events generated by the view
     */
    public DatabasePollingViewable(int myStreamNumber,
                           List<String> inputParameters,
                           PollExecStrategy pollExecStrategy,
                           DataCache dataCache,
                           EventType eventType)
    {
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

    public void validate(EngineImportService engineImportService,
                         StreamTypeService streamTypeService,
                         MethodResolutionService methodResolutionService,
                         TimeProvider timeProvider,
                         VariableService variableService,
                         ExprEvaluatorContext exprEvaluatorContext,
                         ConfigurationInformation configSnapshot,
                         SchedulingService schedulingService,
                         String engineURI,
                         Map<Integer, List<ExprNode>> sqlParameters,
                         EventAdapterService eventAdapterService) throws ExprValidationException
    {
        evaluators = new ExprEvaluator[inputParameters.size()];
        subordinateStreams = new TreeSet<Integer>();
        this.exprEvaluatorContext = exprEvaluatorContext;

        int count = 0;
        for (String inputParam : inputParameters)
        {
            ExprNode raw = findSQLExpressionNode(myStreamNumber, count, sqlParameters);
            if (raw == null) {
                throw new ExprValidationException("Internal error find expression for historical stream parameter " + count + " stream " + myStreamNumber);
            }
            ExprNode evaluator = raw.getValidatedSubtree(streamTypeService, methodResolutionService, null, timeProvider, variableService, exprEvaluatorContext, eventAdapterService);
            evaluators[count++] = evaluator.getExprEvaluator();

            ExprNodeIdentifierCollectVisitor visitor = new ExprNodeIdentifierCollectVisitor();
            visitor.visit(evaluator);
            for (ExprIdentNode identNode : visitor.getExprProperties()) {
                if (identNode.getStreamId() == myStreamNumber) {
                    throw new ExprValidationException("Invalid expression '" + inputParam + "' resolves to the historical data itself");
                }
                subordinateStreams.add(identNode.getStreamId());
            }
        }
    }

    public EventTable[] poll(EventBean[][] lookupEventsPerStream, PollResultIndexingStrategy indexingStrategy, ExprEvaluatorContext exprEvaluatorContext)
    {
        DataCache localDataCache = dataCacheThreadLocal.get();
        boolean strategyStarted = false;

        EventTable[] resultPerInputRow = new EventTable[lookupEventsPerStream.length];

        // Get input parameters for each row
        EventBean[] eventsPerStream;
        for (int row = 0; row < lookupEventsPerStream.length; row++)
        {
            Object[] lookupValues = new Object[inputParameters.size()];

            // Build lookup keys
            for (int valueNum = 0; valueNum < inputParameters.size(); valueNum++)
            {
                eventsPerStream = lookupEventsPerStream[row];
                Object lookupValue = evaluators[valueNum].evaluate(eventsPerStream, true, exprEvaluatorContext);
                lookupValues[valueNum] = lookupValue;
            }

            EventTable result = null;

            // try the threadlocal iteration cache, if set
            if (localDataCache != null)
            {
                result = localDataCache.getCached(lookupValues);
            }

            // try the connection cache
            if (result == null)
            {
                result = dataCache.getCached(lookupValues);
                if ((result != null) && (localDataCache != null))
                {
                    localDataCache.put(lookupValues, result);
                }
            }

            // use the result from cache
            if (result != null)     // found in cache
            {
                resultPerInputRow[row] = result;
            }
            else        // not found in cache, get from actual polling (db query)
            {
                try
                {
                    if (!strategyStarted)
                    {
                        pollExecStrategy.start();
                        strategyStarted = true;
                    }

                    // Poll using the polling execution strategy and lookup values
                    List<EventBean> pollResult = pollExecStrategy.poll(lookupValues);

                    // index the result, if required, using an indexing strategy
                    EventTable indexTable = indexingStrategy.index(pollResult, dataCache.isActive());

                    // assign to row
                    resultPerInputRow[row] = indexTable;

                    // save in cache
                    dataCache.put(lookupValues, indexTable);

                    if (localDataCache != null)
                    {
                        localDataCache.put(lookupValues, indexTable);
                    }
                }
                catch (EPException ex)
                {
                    if (strategyStarted)
                    {
                        pollExecStrategy.done();
                    }
                    throw ex;
                }
            }
        }

        if (strategyStarted)
        {
            pollExecStrategy.done();
        }

        return resultPerInputRow;
    }

    public View addView(View view)
    {
        view.setParent(this);
        return view;
    }

    public List<View> getViews()
    {
        return Collections.emptyList();
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
        return new IterablesArrayIterator(poll(NULL_ROWS, iteratorIndexingStrategy, exprEvaluatorContext));
    }

    public SortedSet<Integer> getRequiredStreams()
    {
        return subordinateStreams;
    }

    public boolean hasRequiredStreams()
    {
        return !subordinateStreams.isEmpty();
    }

    public ThreadLocal<DataCache> getDataCacheThreadLocal()
    {
        return dataCacheThreadLocal;
    }

    public void removeAllViews()
    {
        throw new UnsupportedOperationException("Subviews not supported");
    }

    private static ExprNode findSQLExpressionNode(int myStreamNumber, int count, Map<Integer, List<ExprNode>> sqlParameters)
    {
        if ((sqlParameters == null) || (sqlParameters.isEmpty())) {
            return null;
        }
        List<ExprNode> params = sqlParameters.get(myStreamNumber);
        if ((params == null) || (params.isEmpty()) || (params.size() < (count + 1))) {
            return null;
        }
        return params.get(count);
    }
}
