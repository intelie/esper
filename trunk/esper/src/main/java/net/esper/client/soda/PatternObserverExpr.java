package net.esper.client.soda;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Pattern observer expression observes occurances such as timer-at (crontab) and timer-interval. 
 */
public class PatternObserverExpr extends EPBaseNamedObject implements PatternExpr
{
    /**
     * Ctor - for use to create a pattern expression tree, without pattern child expression.
     * @param namespace is the guard object namespace
     * @param name is the guard object name
     * @param parameters is guard object parameters
     */
    public PatternObserverExpr(String namespace, String name, Object[] parameters)
    {
        super(namespace, name, Arrays.asList(parameters));
    }

    /**
     * Ctor - for use to create a pattern expression tree, without pattern child expression.
     * @param namespace is the guard object namespace
     * @param name is the guard object name
     * @param parameters is guard object parameters
     */
    public PatternObserverExpr(String namespace, String name, List<Object> parameters)
    {
        super(namespace, name, parameters);
    }

    public List<PatternExpr> getChildren()
    {
        return new ArrayList<PatternExpr>();
    }
}
