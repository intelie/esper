package net.esper.persist;

import net.esper.client.logstate.LogKey;
import net.esper.client.logstate.LogEntry;
import net.esper.client.logstate.LogEntryType;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class LogServiceUtil
{
    /**
     * Returns the key represented by the node.
     * @param node is the state node
     * @return is the key identifying the node
     */
    public static LogKey getLogKeyForNode(LogContextNode node)
    {
        int[] result = new int[3];
        recursiveFlatten(result, node);
        return new LogKey(result);
    }

    /**
     * Compile a map of the deep node structure with an entry in the map for
     * each node in the tree. Does not include the parent node itself in the map.
     * <p>
     * Uses the LogKey indifier of each node to ensure no duplicates are in the map.
     * @param parentNode is the parent state tree
     * @return map of keys and nodes
     */
    public static Map<LogKey, LogContextNode> getDeepNodeMap(LogContextNode parentNode)
    {
        Map<LogKey, LogContextNode> result = new HashMap<LogKey, LogContextNode>();
        recursiveFlattenMap(parentNode, result);
        return result;
    }

    /**
     * Compile a map of keys and state objects out of log entries.
     * @param logEntries is an array of log entries
     * @return map of log state keys and state
     */
    public static Map<LogKey, Serializable> getStateMap(LogEntry[] logEntries)
    {
        Map<LogKey, Serializable> result = new HashMap<LogKey, Serializable>();
        for (int i = 0; i < logEntries.length; i++)
        {
            LogKey logKey = logEntries[i].getKey();
            if (result.containsKey(logKey))
            {
                throw new IllegalStateException("Key " + logKey + " already in collection");
            }

            result.put(logKey, logEntries[i].getState());
        }
        return result;
    }

    private static void recursiveFlattenMap(LogContextNode node, Map<LogKey, LogContextNode> map)
    {
        for (Object child : node.getChildNodes())
        {
            LogContextNode childNode = (LogContextNode) child;

            // Get key
            LogKey logKey = LogServiceUtil.getLogKeyForNode(childNode);

            // Check map
            if (map.containsKey(logKey))
            {
                throw new IllegalStateException("Already contains node " + logKey);
            }
            map.put(logKey, childNode);

            // It's child nodes
            recursiveFlattenMap(childNode, map);
        }
    }

    private static void recursiveFlatten(int[] result, LogContextNode currNode)
    {
        result[currNode.getContextDepth() - 1] = currNode.getContextNodeNum();
        LogContextNode parent = currNode.getParent();
        if (parent.getType() != LogEntryType.ENGINE)
        {
            recursiveFlatten(result, parent);
        }
    }
}
