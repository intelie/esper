package net.esper.pattern;

import java.util.List;
import java.util.ArrayList;

/**
 * Result of analysis of pattern expression node tree.
 */
public class EvalNodeAnalysisResult
{
    private List<EvalFilterNode> filterNodes = new ArrayList<EvalFilterNode>();
    private List<EvalGuardNode> guardNodes = new ArrayList<EvalGuardNode>();
    private List<EvalObserverNode> observerNodes = new ArrayList<EvalObserverNode>();

    /**
     * Adds a filter node.
     * @param filterNode filter node to add
     */
    public void add(EvalFilterNode filterNode)
    {
        filterNodes.add(filterNode);
    }
    /**
     * Adds a guard node.
     * @param guardNode node to add
     */
    public void add(EvalGuardNode guardNode)
    {
        guardNodes.add(guardNode);
    }
    /**
     * Adds an observer node.
     * @param observerNode node to add
     */
    public void add(EvalObserverNode observerNode)
    {
        observerNodes.add(observerNode);
    }

    /**
     * Returns filter nodes.
     * @return filter nodes
     */
    public List<EvalFilterNode> getFilterNodes()
    {
        return filterNodes;
    }

    /**
     * Returns guard nodes.
     * @return guard nodes
     */
    public List<EvalGuardNode> getGuardNodes()
    {
        return guardNodes;
    }

    /**
     * Returns observer nodes.
     * @return observer nodes
     */
    public List<EvalObserverNode> getObserverNodes()
    {
        return observerNodes;
    }
}
