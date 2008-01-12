package net.esper.eql.view;

import net.esper.core.ActiveObjectSpace;
import net.esper.core.InternalEventRouter;
import net.esper.core.StatementContext;
import net.esper.dispatch.DispatchService;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.ActiveObjectSpec;
import net.esper.eql.spec.OutputLimitSpec;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.eql.spec.StatementSpecCompiled;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
 * Factory for output processing views.
 */
public class OutputProcessViewFactory
{
    /**
     * Creates an output processor view depending on the presence of output limiting requirements.
     * @param resultSetProcessor is the processing for select-clause and grouping
     * @param statementContext is the statement-level services
     * @return output processing view
     * @throws ExprValidationException to indicate 
     */
    public static OutputProcessView makeView(ResultSetProcessor resultSetProcessor,
                          StatementSpecCompiled statementSpec,
                          StatementContext statementContext,
                          ActiveObjectSpace activeObjectSpace,
                          DispatchService dispatchService,
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

        // Generate an outer/wrapping output strategy for method delivery
        if (statementSpec.getActiveObjectSpec() != null)
        {
            // TODO: error handling
            ActiveObjectSpec spec = statementSpec.getActiveObjectSpec();
            Object target = activeObjectSpace.getSubscriber(spec.getSubscriberUuid());
            FastClass fastClass = FastClass.create(target.getClass());
            FastMethod fastMethod = fastClass.getMethod(spec.getMethodName(), spec.getParameters());
            outputStrategy = new OutputStrategyNatural(statementSpec.getSelectStreamSelectorEnum(), dispatchService, outputStrategy, target, fastMethod);
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
            if (resultSetProcessor == null)
            {
                return new OutputProcessViewHandThrough(outputStrategy, isRouted);
            }
            return new OutputProcessViewDirect(resultSetProcessor, outputStrategy, isRouted);
        }
        catch (RuntimeException ex)
        {
            throw new ExprValidationException("Error in the output rate limiting clause: " + ex.getMessage(), ex);
        }

        /**
         *  TODO
            Object subscriber = null;
            Method method = null;
            if (activeObjectSpec != null)
            {
                subscriber = activeObjectSpace.getSubscriber(activeObjectSpec.getSubscriberUuid());
                try
                {
                    method = subscriber.getClass().getMethod(activeObjectSpec.getMethodName(), activeObjectSpec.getParameters());
                }
                catch (NoSuchMethodException e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        */
    }
}
