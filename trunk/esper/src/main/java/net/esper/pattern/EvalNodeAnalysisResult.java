package net.esper.pattern;

import java.util.List;
import java.util.ArrayList;

public class EvalNodeAnalysisResult
{
    private List<EvalFilterNode> filterNodes = new ArrayList<EvalFilterNode>();
    private List<EvalGuardNode> guardNodes = new ArrayList<EvalGuardNode>();
    private List<EvalObserverNode> observerNodes = new ArrayList<EvalObserverNode>();

    public void add(EvalFilterNode filterNode)
    {
        filterNodes.add(filterNode);
    }
    public void add(EvalGuardNode guardNode)
    {
        guardNodes.add(guardNode);
    }
    public void add(EvalObserverNode observerNode)
    {
        observerNodes.add(observerNode);
    }

    public List<EvalFilterNode> getFilterNodes()
    {
        return filterNodes;
    }

    public List<EvalGuardNode> getGuardNodes()
    {
        return guardNodes;
    }

    public List<EvalObserverNode> getObserverNodes()
    {
        return observerNodes;
    }
}
