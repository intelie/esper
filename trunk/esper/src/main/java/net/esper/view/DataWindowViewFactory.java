package net.esper.view;

import net.esper.event.EventType;
import net.esper.core.StatementContext;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;

/**
 * Marker interface for use with view factories that create data window views only.
 * <p>
 * Please {@link DataWindowView} for details on views that meet data window requirements.
 */
public interface DataWindowViewFactory extends ViewFactory
{
    
}
