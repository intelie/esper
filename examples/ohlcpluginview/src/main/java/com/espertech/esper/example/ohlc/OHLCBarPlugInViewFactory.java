package com.espertech.esper.example.ohlc;

import com.espertech.esper.client.EventType;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.*;

import java.util.List;

public class OHLCBarPlugInViewFactory extends ViewFactorySupport
{
    private ViewFactoryContext viewFactoryContext;
    private List<ExprNode> viewParameters;
    private ExprNode timestampExpression;
    private ExprNode valueExpression;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException
    {
        this.viewFactoryContext = viewFactoryContext;
        if (viewParameters.size() != 2) {
            throw new ViewParameterException("View requires a two parameters: the expression returning timestamps and the expression supplying OHLC data points");
        }
        this.viewParameters = viewParameters;
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        ExprNode[] validatedNodes = ViewFactorySupport.validate("OHLC view", parentEventType, statementContext, viewParameters, false);

        timestampExpression = validatedNodes[0];
        valueExpression = validatedNodes[1];

        if ((timestampExpression.getExprEvaluator().getType() != long.class) && (timestampExpression.getExprEvaluator().getType() != Long.class)) {
            throw new ViewParameterException("View requires long-typed timestamp values in parameter 1");
        }
        if ((valueExpression.getExprEvaluator().getType() != double.class) && (valueExpression.getExprEvaluator().getType() != Double.class)) {
            throw new ViewParameterException("View requires double-typed values for in parameter 2");
        }
    }

    public View makeView(StatementContext statementContext)
    {
        return new OHLCBarPlugInView(statementContext, timestampExpression, valueExpression);
    }

    public EventType getEventType()
    {
        return OHLCBarPlugInView.getEventType(viewFactoryContext.getEventAdapterService());
    }
}
