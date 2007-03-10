package net.esper.view.window;

import net.esper.eql.core.ViewResourceCallback;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;
import net.esper.view.*;

import java.util.List;

/**
 * Factory for {@link net.esper.view.window.TimeBatchView}.
 */
public class LengthBatchViewFactory implements ViewFactory
{
    private int size;
    private EventType eventType;
    private RelativeAccessByEventNIndexGetter relativeAccessGetterImpl;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Length window view requires a single integer-type parameter";
        if (viewParameters.size() != 1)
        {
            throw new ViewParameterException(errorMessage);
        }

        Object parameter = viewParameters.get(0);
        if (!(parameter instanceof Number))
        {
            throw new ViewParameterException(errorMessage);
        }
        Number numParam = (Number) parameter;
        if ( (JavaClassHelper.isFloatingPointNumber(numParam)) ||
             (numParam instanceof Long))
        {
            throw new ViewParameterException(errorMessage);
        }

        size =  numParam.intValue();
        if (size <= 0)
        {
            throw new ViewParameterException("Length window requires a positive number");
        }
    }

    public void attach(EventType parentEventType, StatementServiceContext statementServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return viewCapability instanceof ViewCapDataWindowAccess;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }
        if (relativeAccessGetterImpl == null)
        {
            relativeAccessGetterImpl = new RelativeAccessByEventNIndexGetter();
        }
        resourceCallback.setViewResource(relativeAccessGetterImpl);
    }

    public View makeView(StatementServiceContext statementServiceContext)
    {
        IStreamRelativeAccess relativeAccessByEvent = null;

        if (relativeAccessGetterImpl != null)
        {
            relativeAccessByEvent = new IStreamRelativeAccess(relativeAccessGetterImpl);
            relativeAccessGetterImpl.updated(relativeAccessByEvent, null);
        }

        return new LengthBatchView(this, size, relativeAccessByEvent);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof LengthBatchView))
        {
            return false;
        }

        LengthBatchView myView = (LengthBatchView) view;
        if (myView.getSize() != size)
        {
            return false;
        }

        return myView.isEmpty();
    }
}
