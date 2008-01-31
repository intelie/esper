package com.espertech.esper.eql.spec;

import com.espertech.esper.client.soda.EPStatementObjectModel;

import java.util.Map;
import java.util.HashMap;

/**
 * Return result for unmap operators unmapping an intermal statement representation to the SODA object model.
 */
public class StatementSpecUnMapResult
{
    private final EPStatementObjectModel objectModel;
    private final Map<Integer, SubstitutionParameterExpression> indexedParams;

    /**
     * Ctor.
     * @param objectModel of the statement
     * @param indexedParams a map of parameter index and parameter
     */
    public StatementSpecUnMapResult(EPStatementObjectModel objectModel, Map<Integer, SubstitutionParameterExpression> indexedParams)
    {
        this.objectModel = objectModel;
        this.indexedParams = indexedParams;
    }

    /**
     * Returns the object model.
     * @return object model
     */
    public EPStatementObjectModel getObjectModel()
    {
        return objectModel;
    }

    /**
     * Returns the substitution paremeters keyed by the parameter's index.
     * @return map of index and parameter
     */
    public Map<Integer, SubstitutionParameterExpression> getIndexedParams()
    {
        return indexedParams;
    }
}
