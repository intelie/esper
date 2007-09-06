package net.esper.eql.spec;

import net.esper.client.soda.EPStatementObjectModel;

import java.util.Map;
import java.util.HashMap;

public class StatementSpecUnMapResult
{
    private final EPStatementObjectModel objectModel;
    private final Map<Integer, SubstitutionParameterExpression> indexedParams;

    public StatementSpecUnMapResult(EPStatementObjectModel objectModel, Map<Integer, SubstitutionParameterExpression> indexedParams)
    {
        this.objectModel = objectModel;
        this.indexedParams = indexedParams;
    }

    public EPStatementObjectModel getObjectModel()
    {
        return objectModel;
    }

    public Map<Integer, SubstitutionParameterExpression> getIndexedParams()
    {
        return indexedParams;
    }
}
