package net.esper.client;

import net.esper.client.soda.EPStatementObjectModel;
import net.esper.eql.spec.SubstitutionParameterExpression;

import java.util.Map;

/**
 * Prepared statement implementation that stores the statement object model and
 * a list of substitution parameters, to be mapped into an internal representation upon
 * creation.
 */
public class EPPreparedStatementImpl implements EPPreparedStatement
{
    private EPStatementObjectModel model;
    private Map<Integer, SubstitutionParameterExpression> subParams;

    /**
     * Ctor.
     * @param model is the statement object model
     * @param subParams is the substitution parameter list
     */
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

    /**
     * Returns the statement object model for the prepared statement
     * @return object model
     */
    public EPStatementObjectModel getModel()
    {
        return model;
    }

    /**
     * Returns the indexed substitution parameters.
     * @return map of index and parameter
     */
    public Map<Integer, SubstitutionParameterExpression> getSubParams()
    {
        return subParams;
    }
}
