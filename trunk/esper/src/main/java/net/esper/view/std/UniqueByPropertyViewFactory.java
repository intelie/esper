package net.esper.view.std;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.eql.named.RemoveStreamViewCapability;
import net.esper.core.StatementContext;

import java.util.List;
import java.util.Arrays;

/**
 * Factory for {@link UniqueByPropertyView} instances. 
 */
public class UniqueByPropertyViewFactory implements DataWindowViewFactory
{
    /**
     * Property name to evaluate unique values.
     */
    protected String[] propertyNames;
    
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        propertyNames = GroupByViewFactory.getFieldNameParams(viewParameters, "Unique");         
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        // Attaches to just about anything as long as all the fields exists
        for (int i = 0; i < propertyNames.length; i++)
        {
            String message = PropertyCheckHelper.exists(parentEventType, propertyNames[i]);
            if (message != null)
            {
                throw new ViewAttachException(message);
            }
        }
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return true;
        }
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return;
        }
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(StatementContext statementContext)
    {
        return new UniqueByPropertyView(propertyNames);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof UniqueByPropertyView))
        {
            return false;
        }

        UniqueByPropertyView myView = (UniqueByPropertyView) view;
        if (!Arrays.deepEquals(myView.getUniqueFieldNames(), propertyNames))
        {
            return false;
        }

        return true;
    }
}
