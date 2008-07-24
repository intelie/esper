package com.espertech.esper.util;

import java.util.*;

public class GraphUtil
{
    public static Set<String> getTopDownOrder(Map<String, Set<String>> graph) throws GraphCircularDependencyException
    {
        Stack<String> circularDependency = getFirstCircularDependency(graph);
        if (circularDependency != null)
        {
            throw new GraphCircularDependencyException("Circular dependency detected between " + circularDependency);
        }

        Map<String, Set<String>> reversedGraph = new HashMap<String, Set<String>>();

        // Reverse the graph - build a list of children per parent
        for (Map.Entry<String, Set<String>> entry : graph.entrySet())
        {
            Set<String> parents = entry.getValue();
            String child = entry.getKey();

            for (String parent : parents)
            {
                Set<String> childList = reversedGraph.get(parent);
                if (childList == null)
                {
                    childList = new LinkedHashSet<String>();
                    reversedGraph.put(parent, childList);
                }
                childList.add(child);
            }
        }

        // Determine all root nodes, which are those without parent
        TreeSet<String> roots = new TreeSet<String>();
        for (Set<String> parents : graph.values())
        {
            if (parents == null)
            {
                continue;
            }
            for (String parent : parents)
            {
                // node not itself a child
                if (!graph.containsKey(parent))
                {
                    roots.add(parent);
                }
            }
        }

        // for each root, recursively add its child nodes
        Set<String> graphFlattened = new LinkedHashSet<String>();
        for (String root : roots)
        {
            recusiveAdd(graphFlattened, root, reversedGraph);
        }

        return graphFlattened;
    }

    private static void recusiveAdd(Set<String> graphFlattened, String root, Map<String, Set<String>> reversedGraph)
    {
        graphFlattened.add(root);
        Set<String> childNodes = reversedGraph.get(root);
        if (childNodes == null)
        {
            return;
        }
        for (String child : childNodes)
        {
            recusiveAdd(graphFlattened, child, reversedGraph);
        }
    }

    /**
     * Returns any circular dependency as a stack of stream numbers, or null if none exist.
     * @return circular dependency stack
     */
    private static Stack<String> getFirstCircularDependency(Map<String, Set<String>> graph)
    {
        for (String child : graph.keySet())
        {
            Stack<String> deepDependencies = new Stack<String>();
            deepDependencies.push(child);

            boolean isCircular = recursiveDeepDepends(deepDependencies, child, graph);
            if (isCircular)
            {
                return deepDependencies;
            }
        }
        return null;
    }

    private static boolean recursiveDeepDepends(Stack<String> deepDependencies, String currentChild, Map<String, Set<String>> graph)
    {
        Set<String> required = graph.get(currentChild);
        if (required == null)
        {
            return false;
        }

        for (String parent : required)
        {
            if (deepDependencies.contains(parent))
            {
                return true;
            }
            deepDependencies.push(parent);
            boolean isDeep = recursiveDeepDepends(deepDependencies, parent, graph);
            if (isDeep)
            {
                return true;
            }
            deepDependencies.pop();
        }

        return false;
    }
}
