package com.espertech.esper.view.stream;

import com.espertech.esper.event.EventAdapterService;

/**
 * Static factory for implementations of the StreamFactoryService interface.
 */
public final class StreamFactoryServiceProvider
{
    /**
     * Creates an implementation of the StreamFactoryService interface.
     * @param isReuseViews indicator on whether stream and view resources are to be reused between statements
     * @return implementation
     */
    public static StreamFactoryService newService(boolean isReuseViews)
    {
        return new StreamFactorySvcImpl(isReuseViews);
    }
}
