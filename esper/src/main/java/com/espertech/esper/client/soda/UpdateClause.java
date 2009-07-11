package com.espertech.esper.client.soda;

import com.espertech.esper.collection.Pair;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class UpdateClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String eventType;
    private String optionalAsClauseStreamName;
    private List<Pair<String, Expression>> assignments;
    private Expression optionalWhereClause;

    public static UpdateClause create(String eventType, String propertyName, Expression expression)
    {
        UpdateClause clause = new UpdateClause(eventType, null);
        clause.addAssignment(propertyName, expression);
        return clause;
    }

    /**
     * Ctor.
     */
    public UpdateClause(String eventType, String optionalAsClauseStreamName)
    {
        this.eventType = eventType;
        this.optionalAsClauseStreamName = optionalAsClauseStreamName;
        assignments = new ArrayList<Pair<String, Expression>>();
    }

    /**
     * Adds a property to set to the clause.
     * @param property to set
     * @param expression expression providing the new property value
     * @return clause
     */
    public UpdateClause addAssignment(String property, Expression expression)
    {
        assignments.add(new Pair<String, Expression>(property, expression));
        return this;
    }

    /**
     * Returns the list of property assignments.
     * @return pair of property name and expression
     */
    public List<Pair<String, Expression>> getAssignments()
    {
        return assignments;
    }

    /**
     * Sets a list of property assignments.
     * @param assignments list of pairs of property name and expression
     */
    public void setAssignments(List<Pair<String, Expression>> assignments)
    {
        this.assignments = assignments;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public Expression getOptionalWhereClause()
    {
        return optionalWhereClause;
    }

    public void setOptionalWhereClause(Expression optionalWhereClause)
    {
        this.optionalWhereClause = optionalWhereClause;
    }

    public String getOptionalAsClauseStreamName() {
        return optionalAsClauseStreamName;
    }

    public void setOptionalAsClauseStreamName(String optionalAsClauseStreamName) {
        this.optionalAsClauseStreamName = optionalAsClauseStreamName;
    }

    /**
     * Renders the clause in EPL.
     * @param writer to output to
     */
    public void toEPL(StringWriter writer)
    {
        writer.write("update istream ");
        writer.write(eventType);
        if (this.optionalAsClauseStreamName != null) {
            writer.write(" as ");
            writer.write(optionalAsClauseStreamName);
        }
        writer.write(" set ");
        String delimiter = "";
        for (Pair<String, Expression> pair : assignments)
        {
            writer.write(delimiter);
            writer.write(pair.getFirst());
            writer.write(" = ");
            pair.getSecond().toEPL(writer);
            delimiter = ", ";
        }

        if (optionalWhereClause != null)
        {
            writer.write(" where ");
            optionalWhereClause.toEPL(writer);
        }
    }
}
