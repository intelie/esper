package com.espertech.esper.epl.join.plan;

import java.util.*;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * Model of dependency of lookup, in which one stream supplies values for lookup in another stream.
 */
public class DependencyGraph
{
    private final int numStreams;
    private final Map<Integer, SortedSet<Integer>> dependencies;

    /**
     * Ctor.
     * @param numStreams - number of streams
     */
    public DependencyGraph(int numStreams)
    {
        this.numStreams = numStreams;
        dependencies = new HashMap<Integer, SortedSet<Integer>>();
    }

    /**
     * Returns the number of streams.
     * @return number of streams
     */
    public int getNumStreams()
    {
        return numStreams;
    }

    public String toString()
    {
        StringWriter buf = new StringWriter();
        PrintWriter writer = new PrintWriter(buf);

        int count = 0;
        for (Map.Entry<Integer, SortedSet<Integer>> entry : dependencies.entrySet())
        {
            count++;
            writer.println("Entry " + count + ": from=" + entry.getKey());
            writer.println("  to=" + entry.getValue());
        }

        return buf.toString();
    }

    public void addDependency(int target, SortedSet<Integer> requiredStreams)
    {
        if (requiredStreams.contains(target))
        {
            throw new IllegalArgumentException("Dependency between same streams is not allowed for stream " + target);
        }

        Set<Integer> toSet = dependencies.get(target);
        if (toSet != null)
        {
            throw new IllegalArgumentException("Dependencies from stream " + target + " already in collection");
        }

        dependencies.put(target, requiredStreams);
    }

    public void addDependency(int from, int to)
    {
        if (from == to)
        {
            throw new IllegalArgumentException("Dependency between same streams is not allowed for stream " + from);
        }

        SortedSet<Integer> toSet = dependencies.get(from);
        if (toSet == null)
        {
            toSet = new TreeSet<Integer>();
            dependencies.put(from, toSet);
        }

        toSet.add(to);
    }

    public Map<Integer, SortedSet<Integer>> getDependencies()
    {
        return dependencies;
    }
}
