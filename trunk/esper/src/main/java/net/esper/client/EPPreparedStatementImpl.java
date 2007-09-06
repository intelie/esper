package net.esper.client;

import net.esper.client.soda.EPStatementObjectModel;
import net.esper.eql.spec.SubstitutionParameterExpression;

import java.util.Map;

public class EPPreparedStatementImpl implements EPPreparedStatement
{
    private EPStatementObjectModel model;
    private Map<Integer, SubstitutionParameterExpression> subParams;

    public EPPreparedStatementImpl(EPStatementObjectModel model, Map<Integer, SubstitutionParameterExpression> subParams)
    {
        this.model = model;
        this.subParams = subParams;
    }

    public void setObject(int parameterIndex, Object value) throws EPException
    {
        if (parameterIndex < 1)
        {
            throw new IllegalArgumentException("Substitution parameter index starts at 1");
        }
        if (subParams.size() == 0)
        {
            throw new IllegalArgumentException("Statement does not have substitution parameters indicated by the '?' character");
        }
        if (parameterIndex > subParams.size())
        {
            throw new IllegalArgumentException("Invalid substitution parameter index of " + parameterIndex + " supplied, the maximum for this statement is " + subParams.size());
        }
        SubstitutionParameterExpression subs = subParams.get(parameterIndex);
        subs.setConstant(value);
    }

    public EPStatementObjectModel getModel()
    {
        return model;
    }

    public Map<Integer, SubstitutionParameterExpression> getSubParams()
    {
        return subParams;
    }
}
