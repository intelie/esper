package net.esper.view;

/**
 * Views that can work under a group-by must be able to duplicate and are required to implement this interface.
 */
public interface CloneableView
{
    /**
     * Duplicates the view.
     * <p>
     * Expected to return a same view in initialized state for grouping.
     * @param statementServiceContext is services for the view
     * @return duplicated view
     */
    public View cloneView(StatementServiceContext statementServiceContext);
}
