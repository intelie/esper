package net.esper.eql.expression;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Superclass for filter nodes in a filter expression tree. Allow
 * validation against stream event types and evaluation of events against filter tree.
 */
public abstract class ExprNode implements ExprValidator, ExprEvaluator
{
    private final LinkedList<ExprNode> childNodes;

    /**
     * Returns the expression node rendered as a string.
     * @return string rendering of expression
     */
    public abstract String toExpressionString();

    /**
     * Return true if a expression node semantically equals the current node, or false if not.
     * <p>Concrete implementations should compare the type and any additional information
     * that impact the evaluation of a node.  
     * @param node to compare to
     * @return true if semantically equal, or false if not equals
     */
    public abstract boolean equalsNode(ExprNode node);

    /**
     * Constructor creates a list of child nodes.
     */
    public ExprNode()
    {
        childNodes = new LinkedList<ExprNode>();
    }

    /**
     * Executes validate on filter tree node descendants.
     * @param streamTypeService - serves stream type information
     * @throws ExprValidationException when the validation fails
     */
    public void validateDescendents(StreamTypeService streamTypeService) throws ExprValidationException
    {
        // Depth-first validation ensures that child nodes ascertain their types and initialize their
        // stream number and property getters and such.
        for (ExprNode childNode : childNodes)
        {
            childNode.validateDescendents(streamTypeService);
        }
        validate(streamTypeService);
    }

    /**
     * Accept the visitor. The visitor will first visit the parent then visit all child nodes, then their child nodes.
     * <p>The visitor can decide to skip child nodes by returning false in isVisit.
     * @param visitor to visit each node and each child node.
     */
    public void accept(ExprNodeVisitor visitor)
    {
        if (visitor.isVisit(this))
        {
            visitor.visit(this);

            for (ExprNode childNode : childNodes)
            {
                childNode.accept(visitor);
            }
        }
    }

    /**
     * Adds a child node.
     * @param childNode is the child evaluation tree node to add
     */
    public final void addChildNode(ExprNode childNode)
    {
        childNodes.add(childNode);
    }

    /**
     * Returns list of child nodes.
     * @return list of child nodes
     */
    public final LinkedList<ExprNode> getChildNodes()
    {
        return childNodes;
    }

    /**
     * Recursively print out all nodes.
     * @param prefix is printed out for naming the printed info
     */
    public final void dumpDebug(String prefix)
    {
        log.debug(".dumpDebug " + prefix + this.toString());
        for (ExprNode node : childNodes)
        {
            node.dumpDebug(prefix + "  ");
        }
    }

    /**
     * Compare two expression nodes and their children in exact child-node sequence,
     * returning true if the 2 expression nodes trees are equals, or false if they are not equals.
     * <p>
     * Recursive call since it uses this method to compare child nodes in the same exact sequence.
     * Nodes are compared using the equalsNode method.
     * @param nodeOne - first expression top node of the tree to compare
     * @param nodeTwo - second expression top node of the tree to compare
     * @return false if this or all child nodes are not equal, true if equal
     */
    public static boolean deepEquals(ExprNode nodeOne, ExprNode nodeTwo)
    {
        if (nodeOne.getChildNodes().size() != nodeTwo.getChildNodes().size())
        {
            return false;
        }
        if (!nodeOne.equalsNode(nodeTwo))
        {
            return false;
        }
        for (int i = 0; i < nodeOne.getChildNodes().size(); i++)
        {
            ExprNode childNodeOne = nodeOne.getChildNodes().get(i);
            ExprNode childNodeTwo = nodeTwo.getChildNodes().get(i);

            if (!ExprNode.deepEquals(childNodeOne, childNodeTwo))
            {
                return false;
            }
        }
        return true;
    }

    private static final Log log = LogFactory.getLog(ExprNode.class);
}
