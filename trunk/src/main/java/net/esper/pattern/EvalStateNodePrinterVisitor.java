package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Visitor class for printing out an evaluation state tree where each node is printed indented according
 * to its depth in the tree.
 */
public final class EvalStateNodePrinterVisitor implements EvalStateNodeVisitor
{
    private int level;

    public final Object visit(EvalStateNode node, Object data)
    {
        log.debug("visit " + indent(level++) + node.toString());
        node.childrenAccept(this, data);
        level--;
        return data;
    }

    private static String indent(int level)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < level; i++)
        {
            buffer.append("  ");
        }
        return buffer.toString();
    }

    private static final Log log = LogFactory.getLog(EvalStateNodePrinterVisitor.class);
}
