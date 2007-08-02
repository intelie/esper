package net.esper.event;

import net.esper.collection.Pair;

import java.util.Map;

public interface DecoratingEventBean
{
    public Map<String, Object> getDecoratingProperties();
}
