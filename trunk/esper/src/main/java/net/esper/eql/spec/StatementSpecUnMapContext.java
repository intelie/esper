package net.esper.eql.spec;

import java.util.Map;
import java.util.HashMap;

/**
 * Un-mapping context for mapping from an internal specifications to an SODA object model.
 */
public class StatementSpecUnMapContext
{
    private final Map<Integer, SubstitutionParameterExpression> indexedParams;

    public StatementSpecUnMapContext()
    {
        indexedParams = new HashMap<Integer, SubstitutionParameterExpression>();
    }

    public void add(int index, SubstitutionParameterExpression subsParam)
    {
        if (indexedParams.containsKey(index))
        {
            throw new IllegalStateException("Index '" + index + "' already found in collection");
        }
        indexedParams.put(index, subsParam);
    }

    public Map<Integer, SubstitutionParameterExpression> getIndexedParams()
    {
        return indexedParams;
    }
}
