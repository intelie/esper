package net.esper.client.soda;

import net.esper.collection.Pair;

import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;

public class OnSetClause extends OnClause
{
    private static final long serialVersionUID = 0L;

    private List<Pair<String, Expression>> assignments;

    public static OnSetClause create(String variableName, Expression expression)
    {
        OnSetClause clause = new OnSetClause();
        clause.addAssignment(variableName, expression);
        return clause;
    }

    public OnSetClause()
    {
        assignments = new ArrayList<Pair<String, Expression>>();
    }

    public OnSetClause addAssignment(String variable, Expression expression)
    {
        assignments.add(new Pair<String, Expression>(variable, expression));
        return this;
    }

    public List<Pair<String, Expression>> getAssignments()
    {
        return assignments;
    }

    public void setAssignments(List<Pair<String, Expression>> assignments)
    {
        this.assignments = assignments;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write(" set ");
        String delimiter = "";
        for (Pair<String, Expression> pair : assignments)
        {
            writer.write(delimiter);
            writer.write(pair.getFirst());
            writer.write(" = ");
            pair.getSecond().toEQL(writer);
            delimiter = ", ";
        }        
    }
}
