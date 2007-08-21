package net.esper.client.soda;

import java.util.List;
import java.io.Serializable;

public interface Expression extends Serializable
{
    public List<Expression> getChildren();
}
