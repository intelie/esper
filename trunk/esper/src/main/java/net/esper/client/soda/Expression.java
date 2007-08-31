package net.esper.client.soda;

import java.util.List;
import java.util.Iterator;
import java.io.Serializable;
import java.io.StringWriter;

public interface Expression extends Serializable
{
    public List<Expression> getChildren();

    public void toEQL(StringWriter writer);
}
