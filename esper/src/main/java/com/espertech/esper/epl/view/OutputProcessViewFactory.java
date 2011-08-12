/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.view;

import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.expression.ExprValidationContext;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.*;

/**
 * Factory for output processing views.
 */
public class OutputProcessViewFactory
{
    /**
     * Creates an output processor view depending on the presence of output limiting requirements.
     * @param resultSetProcessor is the processing for select-clause and grouping
     * @param statementContext is the statement-level services
     * @param internalEventRouter service for routing events internally
     * @param statementSpec the statement specification
     * @return output processing view
     * @throws ExprValidationException to indicate
     */
    public static OutputProcessView makeView(ResultSetProcessor resultSetProcessor,
                          StatementSpecCompiled statementSpec,
                          StatementContext statementContext,
                          InternalEventRouter internalEventRouter)
            throws ExprValidationException
    {
        boolean isRouted = false;
        boolean routeToFront = false;
        if (statementSpec.getInsertIntoDesc() != null)
        {
            isRouted = true;
            routeToFront = statementContext.getNamedWindowService().isNamedWindow(statementSpec.getInsertIntoDesc().getEventTypeName());
        }

        OutputStrategy outputStrategy;
        if ((statementSpec.getInsertIntoDesc() != null) || (statementSpec.getSelectStreamSelectorEnum() == SelectClauseStreamSelectorEnum.RSTREAM_ONLY))
        {
            boolean isRouteRStream = false;
            if (statementSpec.getInsertIntoDesc() != null)
            {
                isRouteRStream = !statementSpec.getInsertIntoDesc().isIStream();
            }

            outputStrategy = new OutputStrategyPostProcess(isRouted, isRouteRStream, statementSpec.getSelectStreamSelectorEnum(), internalEventRouter, statementContext.getEpStatementHandle(), statementContext, routeToFront);
        }
        else
        {
            outputStrategy = new OutputStrategySimple();
        }

        // Do we need to enforce an output policy?
        int streamCount = statementSpec.getStreamSpecs().size();
        OutputLimitSpec outputLimitSpec = statementSpec.getOutputLimitSpec();
        boolean isDistinct = statementSpec.getSelectClauseSpec().isDistinct();
        boolean isGrouped = statementSpec.getGroupByExpressions() != null && !statementSpec.getGroupByExpressions().isEmpty();

        if (outputLimitSpec != null) {
            ExprValidationContext validationContext = new ExprValidationContext(null, statementContext.getMethodResolutionService(), null, statementContext.getTimeProvider(), statementContext.getVariableService(), statementContext, statementContext.getEventAdapterService(), statementContext.getStatementName(), statementContext.getStatementId(), statementContext.getAnnotations());
            if (outputLimitSpec.getAfterTimePeriodExpr() != null) {
                outputLimitSpec.getAfterTimePeriodExpr().validate(validationContext);
            }
            if (outputLimitSpec.getTimePeriodExpr() != null) {
                outputLimitSpec.getTimePeriodExpr().validate(validationContext);
            }
        }

        OutputProcessView outputProcessView;
        try
        {
            if (outputLimitSpec == null)
            {
                if (!isDistinct)
                {
                    outputProcessView = new OutputProcessViewDirect(resultSetProcessor, outputStrategy, isRouted, statementContext, false, null, null);
                }
                else
                {
                    outputProcessView = new OutputProcessViewDistinctOrAfter(resultSetProcessor, outputStrategy, isRouted, statementContext, true, null, null);
                }
            }
            else if (outputLimitSpec.getRateType() == OutputLimitRateType.AFTER)
            {
                outputProcessView = new OutputProcessViewDistinctOrAfter(resultSetProcessor, outputStrategy, isRouted, statementContext, isDistinct, outputLimitSpec.getAfterTimePeriodExpr(), outputLimitSpec.getAfterNumberOfEvents());
            }
            else
            {
                boolean isWithHavingClause = statementSpec.getHavingExprRootNode() != null;
                if (outputLimitSpec.getDisplayLimit() == OutputLimitLimitType.SNAPSHOT)
                {
                    outputProcessView = new OutputProcessViewSnapshot(resultSetProcessor, outputStrategy, isRouted, streamCount, outputLimitSpec, statementContext, isDistinct, isGrouped, isWithHavingClause);
                }
                // For FIRST without groups we are using a special logic that integrates the first-flag, in order to still conveniently use all sorts of output conditions.
                // FIRST with group-by is handled by setting the output condition to null (OutputConditionNull) and letting the ResultSetProcessor handle first-per-group.
                // Without having-clause there is no required order of processing, thus also use regular policy.
                else if (outputLimitSpec.getDisplayLimit() == OutputLimitLimitType.FIRST && statementSpec.getGroupByExpressions().isEmpty() && isWithHavingClause) {
                    outputProcessView = new OutputProcessViewPolicyFirst(resultSetProcessor, outputStrategy, isRouted, streamCount, outputLimitSpec, statementContext, isDistinct, false, isWithHavingClause);
                }
                else
                {
                    outputProcessView = new OutputProcessViewPolicy(resultSetProcessor, outputStrategy, isRouted, streamCount, outputLimitSpec, statementContext, isDistinct, isGrouped, isWithHavingClause);
                }
            }
        }
        catch (RuntimeException ex)
        {
            throw new ExprValidationException("Error in the output rate limiting clause: " + ex.getMessage(), ex);
        }

        return outputProcessView;
    }
}
