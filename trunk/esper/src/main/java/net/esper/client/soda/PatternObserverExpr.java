package net.esper.client.soda;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class PatternObserverExpr extends EPBaseNamedObject implements PatternExpr
{
    public PatternObserverExpr(String namespace, String name, Object[] parameters)
    {
        super(namespace, name, Arrays.asList(parameters));
    }

    public PatternObserverExpr(String namespace, String name, List<Object> parameters)
    {
        super(namespace, name, parameters);
    }

    public List<PatternExpr> getChildren()
    {
        return new ArrayList<PatternExpr>();
    }
}
