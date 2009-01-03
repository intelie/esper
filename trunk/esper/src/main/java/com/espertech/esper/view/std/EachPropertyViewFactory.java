package com.espertech.esper.view.std;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprIdentNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.*;

import java.util.List;

public class EachPropertyViewFactory implements ViewFactory, DataWindowViewFactory
{
    private List<ExprNode> expressionParameters;
    private EventType selfEventType;
    private EventPropertyGetter fragmentGetter;
    private ExprNode[] exprNodes;
    private boolean isIndexed;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        this.expressionParameters = expressionParameters;
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        if (expressionParameters.size() != 1)
        {
            throw new ViewParameterException("'Each Property' view takes a single expression that returns the nested property value(s)");
        }

        exprNodes = ViewFactorySupport.validate("'First element' view", parentEventType, statementContext, expressionParameters, false);
        if (!(exprNodes[0] instanceof ExprIdentNode))
        {
            throw new ViewParameterException("'Each Property' view parameter does not return nested property value(s), expecting a property name");            
        }
        ExprIdentNode node = (ExprIdentNode) exprNodes[0];
        FragmentEventType fragmentType = parentEventType.getFragmentType(node.getResolvedPropertyName());
        if (fragmentType == null)
        {
            throw new ViewParameterException("'Each Property' view takes a single expression that returns the nested property value(s)");
        }

        selfEventType = fragmentType.getFragmentType();
        isIndexed = fragmentType.isIndexed();
        fragmentGetter = parentEventType.getGetter(node.getResolvedPropertyName());
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(StatementContext statementContext)
    {
        return new EachPropertyView(exprNodes, selfEventType, fragmentGetter, isIndexed);
    }

    public EventType getEventType()
    {
        return selfEventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof EachPropertyView))
        {
            return false;
        }

        EachPropertyView myView = (EachPropertyView) view;
        if (!ExprNode.deepEquals(exprNodes, myView.getExprNodes()))
        {
            return false;
        }

        return true;
    }
}
