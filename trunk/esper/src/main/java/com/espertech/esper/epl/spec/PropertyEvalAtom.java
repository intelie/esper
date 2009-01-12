package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.List;
import java.util.ArrayList;

public class PropertyEvalAtom implements MetaDefItem
{
    private final String propertyName;
    private final String optionalAsName;
    private final SelectClauseSpecRaw optionalSelectClause;
    private final ExprNode optionalWhereClause;

    public PropertyEvalAtom(String propertyName, String optionalAsName, SelectClauseSpecRaw optionalSelectClause, ExprNode optionalWhereClause)
    {
        this.optionalAsName = optionalAsName;
        this.optionalSelectClause = optionalSelectClause;
        this.optionalWhereClause = optionalWhereClause;
        this.propertyName = propertyName;
    }

    public String getOptionalAsName()
    {
        return optionalAsName;
    }

    public SelectClauseSpecRaw getOptionalSelectClause()
    {
        return optionalSelectClause;
    }

    public ExprNode getOptionalWhereClause()
    {
        return optionalWhereClause;
    }

    public String getPropertyName()
    {
        return propertyName;
    }
}
