package net.esper.client.soda;

import net.esper.type.NumberSetParameter;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;

public class PatternGuardExpr extends EPBaseNamedObject implements PatternExpr
{
    private List<PatternExpr> guarded;

    public PatternGuardExpr(String namespace, String name, List<Object> parameters)
    {
        super(namespace, name, parameters);
        this.guarded = new ArrayList<PatternExpr>();
    }

    public PatternGuardExpr(String namespace, String name, Object[] parameters, PatternExpr guarded)
    {
        this(namespace, name, Arrays.asList(parameters), guarded);
    }

    public PatternGuardExpr(String namespace, String name, List<Object> parameters, PatternExpr guardedPattern)
    {
        super(namespace, name, parameters);
        this.guarded = new ArrayList<PatternExpr>();
        guarded.add(guardedPattern);
    }

    public List<PatternExpr> getChildren()
    {
        return guarded;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write('(');
        guarded.get(0).toEQL(writer);        
        writer.write(") ");
        writer.write(" where ");
        super.toEQL(writer);
    }

}
