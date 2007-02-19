package net.esper.eql.join.plan;

import net.esper.event.EventType;
import net.esper.eql.join.exec.ExecNode;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.exec.NestedIterationExecNode;
import net.esper.util.IndentWriter;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Plan to perform a nested iteration over child nodes.
 */
public class NestedIterationNode extends QueryPlanNode
{
    private final LinkedList<QueryPlanNode> childNodes;
    private final int[] nestingOrder;

    /**
     * Ctor.
     * @param nestingOrder - order of streams in nested iteration
     */
    public NestedIterationNode(int[] nestingOrder)
    {
        this.nestingOrder = nestingOrder;
        this.childNodes = new LinkedList<QueryPlanNode>();

        if (nestingOrder.length == 0)
        {
            throw new IllegalArgumentException("Invalid empty nesting order");
        }
    }

    /**
     * Adds a child node.
     * @param childNode is the child evaluation tree node to add
     */
    public final void addChildNode(QueryPlanNode childNode)
    {
        childNodes.add(childNode);
    }

    /**
     * Returns list of child nodes.
     * @return list of child nodes
     */
    protected final LinkedList<QueryPlanNode> getChildNodes()
    {
        return childNodes;
    }

    public ExecNode makeExec(EventTable[][] indexPerStream, EventType[] streamTypes)
    {
        if (childNodes.isEmpty())
        {
            throw new IllegalStateException("Zero child nodes for nested iteration");
        }

        NestedIterationExecNode execNode = new NestedIterationExecNode(nestingOrder);
        for (QueryPlanNode child : childNodes)
        {
            ExecNode childExec = child.makeExec(indexPerStream, streamTypes);
            execNode.addChildNode(childExec);
        }
        return execNode;
    }

    public void print(IndentWriter indentWriter)
    {
        indentWriter.println("NestedIterationNode with nesting order " + Arrays.toString(nestingOrder));
        indentWriter.incrIndent();
        for (QueryPlanNode child : childNodes)
        {
            child.print(indentWriter);
        }
        indentWriter.decrIndent();
    }
}
