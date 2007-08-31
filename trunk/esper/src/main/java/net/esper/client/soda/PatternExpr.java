package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

public interface PatternExpr extends Serializable
{
    public List<PatternExpr> getChildren();

    public void toEQL(StringWriter writer);
}
