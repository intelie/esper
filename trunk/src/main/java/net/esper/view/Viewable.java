package net.esper.view;

import net.esper.view.EventCollection;
import net.esper.view.View;

import java.util.List;

/**
 * The Viewable interface marks an object as supporting zero, one or more View instances.
 * All implementing classes must call each view's 'update' method when new data enters it.
 * Implementations must take care to synchronize methods of this interface with other methods
 * such that data flow is threadsafe.
 */
public interface Viewable extends EventCollection
{
    /**
     * Add a view to the viewable object.
     * @param view to add
     * @return view to add
     */
    public View addView(View view);

    /**
     * Returns all added views.
     * @return list of added views
     */
    public List<View> getViews();

    /**
     * Remove a view.
     * @param view to remove
     * @return true to indicate that the view to be removed existed within this view, false if the view to
     * remove could not be found
     */
    public boolean removeView(View view);

    /**
     * Test is there are any views to the Viewable.
     * @return true indicating there are child views, false indicating there are no child views
     */
    public boolean hasViews();
}