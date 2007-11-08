package net.esper.view.window;

import net.esper.core.StatementContext;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.eql.named.RemoveStreamViewCapability;
import net.esper.event.EventType;
import net.esper.view.*;

import java.util.List;

/**
 * Factory for {@link net.esper.view.window.KeepAllView}.
 */
public class KeepAllViewFactory implements DataWindowViewFactory
{
    /**
     * The access into the data window.
     */
    protected RandomAccessByIndexGetter randomAccessGetterImpl;

    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Keep-all data window view requires an empty parameter list";
        if (viewParameters.size() != 0)
        {
            throw new ViewParameterException(errorMessage);
        }
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapDataWindowAccess)
        {
            return true;
        }
        return viewCapability instanceof RemoveStreamViewCapability;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return;
        }
        if (randomAccessGetterImpl == null)
        {
            randomAccessGetterImpl = new RandomAccessByIndexGetter();
        }
        resourceCallback.setViewResource(randomAccessGetterImpl);
    }

    public View makeView(StatementContext statementContext)
    {
        IStreamRandomAccess randomAccess = null;

        if (randomAccessGetterImpl != null)
        {
            randomAccess = new IStreamRandomAccess(randomAccessGetterImpl);
            randomAccessGetterImpl.updated(randomAccess);
        }

        return new KeepAllView(this, randomAccess);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof KeepAllView))
        {
            return false;
        }

        KeepAllView myView = (KeepAllView) view;
        return myView.isEmpty();
    }
}
