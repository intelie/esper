package net.esper.eql.core;

import net.esper.eql.expression.ExprEvaluator;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.SelectClauseElementCompiled;
import net.esper.eql.spec.SelectClauseElementWildcard;
import net.esper.eql.spec.SelectClauseExprCompiledSpec;
import net.esper.eql.spec.SelectClauseStreamCompiledSpec;
import net.esper.event.EventBean;
import net.esper.event.EventType;

import java.util.ArrayList;
import java.util.List;

public class BindProcessor
{
    private ExprEvaluator[] expressionNodes;
    private Class[] expressionTypes;

    public BindProcessor(List<SelectClauseElementCompiled> selectionList,
                         EventType[] typesPerStream)
            throws ExprValidationException
    {
        ArrayList<ExprEvaluator> expressions = new ArrayList<ExprEvaluator>();
        ArrayList<Class> types = new ArrayList<Class>();

        for (SelectClauseElementCompiled element : selectionList)
        {
            // handle wildcards by outputting each stream's underlying event
            if (element instanceof SelectClauseElementWildcard)
            {
                for (int i = 0; i < typesPerStream.length; i++)
                {
                    final int streamNum = i;
                    expressions.add(new ExprEvaluator() {

                        public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
                        {
                            EventBean event = eventsPerStream[streamNum];
                            if (event != null)
                            {
                                return event.getUnderlying();
                            }
                            else
                            {
                                return null;
                            }
                        }
                    });
                    types.add(typesPerStream[streamNum].getUnderlyingType());
                }
            }

            // handle stream wildcards by outputting the stream underlying event
            else if (element instanceof SelectClauseStreamCompiledSpec)
            {
                final SelectClauseStreamCompiledSpec streamSpec = (SelectClauseStreamCompiledSpec) element;
                expressions.add(new ExprEvaluator() {

                    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
                    {
                        EventBean event = eventsPerStream[streamSpec.getStreamNumber()];
                        if (event != null)
                        {
                            return event.getUnderlying();
                        }
                        else
                        {
                            return null;
                        }
                    }
                });
                types.add(typesPerStream[streamSpec.getStreamNumber()].getUnderlyingType());
            }

            // handle expressions
            else if (element instanceof SelectClauseExprCompiledSpec)
            {
                SelectClauseExprCompiledSpec expr = (SelectClauseExprCompiledSpec) element;
                expressions.add(expr.getSelectExpression());
                types.add(expr.getSelectExpression().getType());
            }
            else
            {
                throw new IllegalStateException("Unrecognized select expression element of type " + element.getClass());
            }
        }

        expressionNodes = expressions.toArray(new ExprEvaluator[0]);
        expressionTypes = types.toArray(new Class[0]);
    }

    public Object[] process(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object[] parameters = new Object[expressionNodes.length];

        for (int i = 0; i < parameters.length; i++)
        {
            Object result = expressionNodes[i].evaluate(eventsPerStream, isNewData);
            parameters[i] = result;
        }

        return parameters;
    }
}
