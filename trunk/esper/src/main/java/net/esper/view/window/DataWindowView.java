package net.esper.view.window;

import net.esper.view.View;

/**
 * Tag interface for data window views. Data window views follow the view interface but keep a window over the
 * data received by their parent view. Data window view may keep length windows or time windows or other windows.
 * <p>
 * Data window views generally follow the following behavior:
 * <p>
 * They publish the data that was received as new data from their parent view directly or at a later time as
 * new data to child views.
 * <p>
 * They publish the data that expires out of the window (for length or time reasons or other reasons) as old data to
 * their child views.
 * <p>
 * They do not change event type compared to their parent view.
 * <p>
 * They remove the data they receive as old data from their parent view out of the window and report the data
 * removed as old data to child views (this is an optional capability for performance reasons).
 * <p>
 * Certain views may decide to attach only to data window views directly. One reason for this is that
 * window limit the number of event instances kept in a collection. Without this limitation some views may
 * not work correctly over time as events accumulate but are not removed from the view by means old data updates
 * received from a parent data window. 
 */
public interface DataWindowView extends View
{
}
