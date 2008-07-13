package com.espertech.esper.epl.join.plan;

import java.util.*;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * Model of dependency of lookup, in which one stream supplies values for lookup in another stream.
 */
public class HistoricalDependencyGraph
{
    private final int numStreams;
    private final Map<Integer, SortedSet<Integer>> dependencies;

    /**
     * Ctor.
     * @param numStreams - number of streams
     */
    public HistoricalDependencyGraph(int numStreams)
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

    public void addDependency(int target, int from)
    {
        if (target == from)
        {
            throw new IllegalArgumentException("Dependency between same streams is not allowed for stream " + target);
        }

        SortedSet<Integer> toSet = dependencies.get(target);
        if (toSet == null)
        {
            toSet = new TreeSet<Integer>();
            dependencies.put(target, toSet);
        }

        toSet.add(from);
    }

    public boolean hasDependency(int stream)
    {
        SortedSet<Integer> dep = dependencies.get(stream);
        if (dep != null)
        {
            return !dep.isEmpty();
        }
        return false;        
    }

    public Map<Integer, SortedSet<Integer>> getDependencies()
    {
        return dependencies;
    }

    /**
     * Returns a set of stream numbers that are not a dependency of any stream.
     * @return
     */
    public Set<Integer> getRootNodes()
    {
        Set<Integer> rootNodes = new HashSet<Integer>();

        for (int i = 0; i < numStreams; i++)
        {
            boolean found = false;
            for (Map.Entry<Integer, SortedSet<Integer>> entry : dependencies.entrySet())
            {
                if (entry.getValue().contains(i))
                {
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                rootNodes.add(i);
            }
        }

        return rootNodes;
    }

    public Stack<Integer> getFirstCircularDependency()
    {
        for (int i = 0; i < numStreams; i++)
        {
            Stack<Integer> deepDependencies = new Stack<Integer>();
            deepDependencies.push(i);
            
            boolean isCircular = recursiveDeepDepends(deepDependencies, i);
            if (isCircular)
            {
                return deepDependencies;
            }
        }
        return null;
    }

    private boolean recursiveDeepDepends(Stack<Integer> deepDependencies, int currentStream)
    {
        Set<Integer> required = dependencies.get(currentStream);
        if (required == null)
        {
            return false;
        }

        for (Integer stream : required)
        {
            if (deepDependencies.contains(stream))
            {
                return true;
            }
            deepDependencies.push(stream);
            boolean isDeep = recursiveDeepDepends(deepDependencies, stream);
            if (isDeep)
            {
                return true;
            }
            deepDependencies.pop();
        }

        return false;
    }
}
