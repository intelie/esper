package net.esper.view;

import java.util.List;

/**
 * Interface for use by views to indicate that the view must couple to parent views.
 */
public interface ParentAwareView
{
    /**
     * Called to indicate the parent views.
     * @param parentViews is a list of parent views in top-down order 
     */
    public void setParentAware(List<View> parentViews);
}
