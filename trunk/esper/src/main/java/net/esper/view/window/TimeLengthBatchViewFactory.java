package net.esper.view.window;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.type.TimePeriodParameter;
import net.esper.util.JavaClassHelper;
import net.esper.core.StatementContext;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.eql.named.RemoveStreamViewCapability;

import java.util.List;

/**
 * Factory for {@link net.esper.view.window.TimeLengthBatchView}.
 */
public class TimeLengthBatchViewFactory implements DataWindowViewFactory
{
    private static final String FORCE_UPDATE_KEYWORD = "force_update";
    private static final String START_EAGER_KEYWORD = "start_eager";

    private EventType eventType;

    /**
     * Number of events to collect before batch fires.
     */
    protected long numberOfEvents;

    /**
     * Number of msec before batch fires (either interval or number of events).
     */
    protected long millisecondsBeforeExpiry;

    /**
     * Indicate whether to output only if there is data, or to keep outputing empty batches.
     */
    protected boolean isForceUpdate;

    /**
     * Indicate whether to output only if there is data, or to keep outputing empty batches.
     */
    protected boolean isStartEager;

    /**
     * The access into the data window.
     */
    protected RelativeAccessByEventNIndexGetter relativeAccessGetterImpl;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Time-length combination batch view requires a numeric or time period parameter as a time interval size, and an integer parameter as a maximal number-of-events, and an optional list of control keywords as a string parameter (please see the documentation)";
        if ((viewParameters.size() != 2) && (viewParameters.size() != 3))
        {
            throw new ViewParameterException(errorMessage);
        }

        // parameter 1
        Object parameter = viewParameters.get(0);
        if (parameter instanceof TimePeriodParameter)
        {
            TimePeriodParameter param = (TimePeriodParameter) parameter;
            millisecondsBeforeExpiry = Math.round(1000d * param.getNumSeconds());
        }
        else if (!(parameter instanceof Number))
        {
            throw new ViewParameterException(errorMessage);
        }
        else
        {
            Number param = (Number) parameter;
            if (JavaClassHelper.isFloatingPointNumber(param))
            {
                millisecondsBeforeExpiry = Math.round(1000d * param.doubleValue());
            }
            else
            {
                millisecondsBeforeExpiry = 1000 * param.longValue();
            }
        }

        // parameter 2
        parameter = viewParameters.get(1);
        if (!(parameter instanceof Number) || (JavaClassHelper.isFloatingPointNumber((Number) parameter)))
        {
            throw new ViewParameterException(errorMessage);
        }
        numberOfEvents = ((Number) parameter).longValue();

        if (millisecondsBeforeExpiry < 100)
        {
            throw new ViewParameterException("Time-length-combination batch view requires a size of at least 100 msec");
        }

        if (viewParameters.size() > 2)
        {
            Object keywords = viewParameters.get(2);
            if (!(keywords instanceof String))
            {
                throw new ViewParameterException(errorMessage);
            }

            String[] keyword = ((String) keywords).split(",");
            for (int i = 0; i < keyword.length; i++)
            {
                String keywordText = keyword[i].toLowerCase().trim();
                if (keywordText.length() == 0)
                {
                    continue;
                }
                if (keywordText.equals(FORCE_UPDATE_KEYWORD))
                {
                    isForceUpdate = true;
                }
                else if (keywordText.equals(START_EAGER_KEYWORD))
                {
                    isForceUpdate = true;
                    isStartEager = true;
                }
                else
                {
                    String keywordRange = FORCE_UPDATE_KEYWORD + "," + START_EAGER_KEYWORD;
                    throw new ViewParameterException("Time-length-combination view encountered an invalid keyword '" + keywordText + "', valid control keywords are: " + keywordRange);
                }
            }
        }
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return true;
        }
        return viewCapability instanceof ViewCapDataWindowAccess;
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
        if (relativeAccessGetterImpl == null)
        {
            relativeAccessGetterImpl = new RelativeAccessByEventNIndexGetter();
        }
        resourceCallback.setViewResource(relativeAccessGetterImpl);
    }

    public View makeView(StatementContext statementContext)
    {
        IStreamRelativeAccess relativeAccessByEvent = null;

        if (relativeAccessGetterImpl != null)
        {
            relativeAccessByEvent = new IStreamRelativeAccess(relativeAccessGetterImpl);
            relativeAccessGetterImpl.updated(relativeAccessByEvent, null);
        }

        return new TimeLengthBatchView(this, statementContext, millisecondsBeforeExpiry, numberOfEvents, isForceUpdate, isStartEager, relativeAccessByEvent);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (relativeAccessGetterImpl != null)
        {
            return false;
        }
        if (!(view instanceof TimeLengthBatchView))
        {
            return false;
        }

        TimeLengthBatchView myView = (TimeLengthBatchView) view;

        if (myView.getMsecIntervalSize() != millisecondsBeforeExpiry)
        {
            return false;
        }

        if (myView.getNumberOfEvents() != numberOfEvents)
        {
            return false;
        }

        if (myView.isForceOutput() != isForceUpdate)
        {
            return false;
        }

        if (myView.isStartEager())  // since it's already started
        {
            return false;
        }

        return myView.isEmpty();
    }
}
