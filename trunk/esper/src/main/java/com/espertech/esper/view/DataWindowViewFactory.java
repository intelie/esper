package com.espertech.esper.view;

import com.espertech.esper.event.EventType;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.eql.core.ViewResourceCallback;

import java.util.List;

/**
 * Marker interface for use with view factories that create data window views only.
 * <p>
 * Please {@link DataWindowView} for details on views that meet data window requirements.
 */
public interface DataWindowViewFactory extends ViewFactory
{
    
}
