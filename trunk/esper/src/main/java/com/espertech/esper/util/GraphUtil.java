package com.espertech.esper.util;

import java.util.*;

public class GraphUtil
{
    public static List<String> getTopDownOrder(Map<String, String> graph)
    {
        Map<String, List<String>> reversedGraph = new HashMap<String, List<String>>();

        // Reverse the graph - build a list of children per parent
        for (Map.Entry<String, String> entry : graph.entrySet())
        {
            String parent = entry.getValue();
            String child = entry.getKey();
            List<String> childList = reversedGraph.get(parent);
            if (childList == null)
            {
                childList = new ArrayList<String>();
                reversedGraph.put(parent, childList);
            }
            childList.add(child);
        }

        // Determine all root nodes, which are those without parent
        List<String> roots = new ArrayList<String>();
        for (String node : graph.values())
        {
            // node not itself a child
            if (!graph.containsKey(node))
            {
                roots.add(node);
            }
        }

        // for each root, recursively add its child nodes
        List<String> graphFlattened = new ArrayList<String>();
        for (String root : roots)
        {
            recusiveAdd(graphFlattened, root, reversedGraph);
        }

        return graphFlattened;
    }

    private static void recusiveAdd(List<String> graphFlattened, String root, Map<String, List<String>> reversedGraph)
    {
        graphFlattened.add(root);
        List<String> childNodes = reversedGraph.get(root);
        if (childNodes == null)
        {
            return;
        }
        for (String child : childNodes)
        {
            recusiveAdd(graphFlattened, child, reversedGraph);
        }
    }
}
