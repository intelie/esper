package net.esper.eql.view;

import net.esper.core.InternalEventRouter;
import net.esper.core.StatementContext;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.OutputLimitSpec;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.eql.spec.StatementSpecCompiled;

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
        if (statementSpec.getInsertIntoDesc() != null)
        {
            isRouted = true;
        }

        OutputStrategy outputStrategy;
        if ((statementSpec.getInsertIntoDesc() != null) || statementSpec.getSelectStreamSelectorEnum() != SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH)
        {
            boolean isRouteRStream = false;
            if (statementSpec.getInsertIntoDesc() != null)
            {
                isRouteRStream = !statementSpec.getInsertIntoDesc().isIStream();
            }

            outputStrategy = new OutputStrategyPostProcess(isRouted, isRouteRStream, statementSpec.getSelectStreamSelectorEnum(), internalEventRouter, statementContext.getEpStatementHandle());
        }
        else
        {
            outputStrategy = new OutputStrategySimple();
        }

        // Do we need to enforce an output policy?
        int streamCount = statementSpec.getStreamSpecs().size();
        OutputLimitSpec outputLimitSpec = statementSpec.getOutputLimitSpec();

        try
        {
            if (outputLimitSpec != null)
            {
                return new OutputProcessViewPolicy(resultSetProcessor, outputStrategy, isRouted, streamCount, outputLimitSpec, statementContext);
            }
            return new OutputProcessViewDirect(resultSetProcessor, outputStrategy, isRouted, statementContext.getStatementResultService());
        }
        catch (RuntimeException ex)
        {
            throw new ExprValidationException("Error in the output rate limiting clause: " + ex.getMessage(), ex);
        }
    }
}
