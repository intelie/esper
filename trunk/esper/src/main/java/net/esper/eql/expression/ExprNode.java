package net.esper.eql.expression;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

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
     * Validates the expression node subtree that has this
     * node as root. Some of the nodes of the tree, including the 
     * root, might be replaced in the process.
     * @param streamTypeService - serves stream type information
     * @param autoImportService - for resolving class names in library method invocations
     * @param viewResourceDelegate - delegates for view resources to expression nodes
     * @throws ExprValidationException when the validation fails
     * @return the root node of the validated subtree, possibly 
     *         different than the root node of the unvalidated subtree 
     */
    public ExprNode getValidatedSubtree(StreamTypeService streamTypeService, AutoImportService autoImportService,
                                        ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        ExprNode result = this;

        for (int i = 0; i < childNodes.size(); i++)
        {
            childNodes.set(i, childNodes.get(i).getValidatedSubtree(streamTypeService, autoImportService, viewResourceDelegate));
        }

        try
        {
            validate(streamTypeService, autoImportService, viewResourceDelegate);
        }
        catch(ExprValidationException e)
        {
            if(this instanceof ExprIdentNode)
            {
                result = resolveIdentAsStaticMethod(streamTypeService, autoImportService, e);
            }
            else
            {
                throw e;
            }
        }

        return result;
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
    @SuppressWarnings({"StringContatenationInLoop"})
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
        if (nodeOne.childNodes.size() != nodeTwo.childNodes.size())
        {
            return false;
        }
        if (!nodeOne.equalsNode(nodeTwo))
        {
            return false;
        }
        for (int i = 0; i < nodeOne.childNodes.size(); i++)
        {
            ExprNode childNodeOne = nodeOne.childNodes.get(i);
            ExprNode childNodeTwo = nodeTwo.childNodes.get(i);

            if (!ExprNode.deepEquals(childNodeOne, childNodeTwo))
            {
                return false;
            }
        }
        return true;
    }

    // Assumes that this is an ExprIdentNode
    private ExprNode resolveIdentAsStaticMethod(StreamTypeService streamTypeService, AutoImportService autoImportService, ExprValidationException propertyException)
    throws ExprValidationException
    {
        // Reconstruct the original string
        ExprIdentNode identNode = (ExprIdentNode) this;
        StringBuffer name = new StringBuffer(identNode.getUnresolvedPropertyName());
        if(identNode.getStreamOrPropertyName() != null)
        {
            name.insert(0, identNode.getStreamOrPropertyName() + '.');
        }

        // Parse the string to see if it looks like a method invocation
        // (Ident nodes can only have a single string value in parentheses)
        String classNameRegEx = "((\\w+\\.)*\\w+)";
        String methodNameRegEx = "(\\w+)";
        String argsRegEx = "\\s*\\(\\s*[\'\"]\\s*(\\w+)\\s*[\'\"]\\s*\\)\\s*";
        String methodInvocationRegEx = classNameRegEx + "\\." + methodNameRegEx + argsRegEx;

        Pattern pattern = Pattern.compile(methodInvocationRegEx);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches())
        {
            // This property name doesn't look like a method invocation
            throw propertyException;
        }

        // Create a new static method node and add the method
        // argument as a child node
        String className = matcher.group(1);
        String methodName = matcher.group(3);
        String argString = matcher.group(4);
        ExprNode result = new ExprStaticMethodNode(className, methodName);
        result.addChildNode(new ExprConstantNode(argString));

        // Validate
        try
        {
            result.validate(streamTypeService, autoImportService, null);
        }
        catch(ExprValidationException e)
        {
            throw new ExprValidationException("Failed to resolve " + name + " as either an event property or as a static method invocation");
        }

        return result;
    }

    private static final Log log = LogFactory.getLog(ExprNode.class);
}
