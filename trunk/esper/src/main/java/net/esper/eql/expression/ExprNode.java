package net.esper.eql.expression;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.util.MetaDefItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;

/**
 * Superclass for filter nodes in a filter expression tree. Allow
 * validation against stream event types and evaluation of events against filter tree.
 */
public abstract class ExprNode implements ExprValidator, ExprEvaluator, MetaDefItem
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

    // Since static method calls such as "Class.method('a')" and mapped properties "Stream.property('key')"
    // look the same, however as the validation could not resolve "Stream.property('key')" before calling this method,
    // this method tries to resolve the mapped property as a static method.
    // Assumes that this is an ExprIdentNode.
    private ExprNode resolveIdentAsStaticMethod(StreamTypeService streamTypeService, AutoImportService autoImportService, ExprValidationException propertyException)
    throws ExprValidationException
    {
        // Reconstruct the original string
        ExprIdentNode identNode = (ExprIdentNode) this;
        StringBuffer mappedProperty = new StringBuffer(identNode.getUnresolvedPropertyName());
        if(identNode.getStreamOrPropertyName() != null)
        {
            mappedProperty.insert(0, identNode.getStreamOrPropertyName() + '.');
        }

        // Parse the mapped property format into a class name, method and single string parameter
        MappedPropertyParseResult parse = parseMappedProperty(mappedProperty.toString());
        if (parse == null)
        {
            throw propertyException;
        }
        ExprNode result = new ExprStaticMethodNode(parse.getClassName(), parse.getMethodName());
        result.addChildNode(new ExprConstantNode(parse.getArgString()));

        // Validate
        try
        {
            result.validate(streamTypeService, autoImportService, null);
        }
        catch(ExprValidationException e)
        {
            throw new ExprValidationException("Failed to resolve " + mappedProperty + " as either an event property or as a static method invocation");
        }

        return result;
    }

    /**
     * Parse the mapped property into classname, method and string argument.
     * Mind this has been parsed already and is a valid mapped property.
     * @param property is the string property to be passed as a static method invocation
     * @return descriptor object
     */
    protected static MappedPropertyParseResult parseMappedProperty(String property)
    {
        // get argument
        int indexFirstDoubleQuote = property.indexOf("\"");
        int indexFirstSingleQuote = property.indexOf("'");
        int startArg;
        if ((indexFirstSingleQuote == -1) && (indexFirstDoubleQuote == -1))
        {
            return null;
        }        
        if ((indexFirstSingleQuote != -1) && (indexFirstDoubleQuote != -1))
        {
            if (indexFirstSingleQuote < indexFirstDoubleQuote)
            {
                startArg = indexFirstSingleQuote;
            }
            else
            {
                startArg = indexFirstDoubleQuote;    
            }
        }
        else if (indexFirstSingleQuote != -1)
        {
            startArg = indexFirstSingleQuote;
        }
        else
        {
            startArg = indexFirstDoubleQuote;
        }

        int indexLastDoubleQuote = property.lastIndexOf("\"");
        int indexLastSingleQuote = property.lastIndexOf("'");
        int endArg;
        if ((indexLastSingleQuote == -1) && (indexLastDoubleQuote == -1))
        {
            return null;
        }
        if ((indexLastSingleQuote != -1) && (indexLastDoubleQuote != -1))
        {
            if (indexLastSingleQuote > indexLastDoubleQuote)
            {
                endArg = indexLastSingleQuote;
            }
            else
            {
                endArg = indexLastDoubleQuote;
            }
        }
        else if (indexLastSingleQuote != -1)
        {
            endArg = indexLastSingleQuote;
        }
        else
        {
            endArg = indexLastDoubleQuote;
        }
        String argument = property.substring(startArg + 1, endArg);

        // get method
        String splitDots[] = property.toString().split("[\\.]");
        if (splitDots.length < 2)
        {
            return null;
        }
        String method = splitDots[splitDots.length - 1];
        int indexParan = method.indexOf("(");
        if (indexParan == -1)
        {
            return null;
        }
        method = method.substring(0, indexParan);

        // get class
        StringBuffer clazz = new StringBuffer();
        for (int i = 0; i < splitDots.length - 1; i++)
        {
            if (i > 0)
            {
                clazz.append('.');
            }
            clazz.append(splitDots[i]);            
        }

        return new MappedPropertyParseResult(clazz.toString(), method, argument);
    }

    protected static class MappedPropertyParseResult
    {
        private String className;
        private String methodName;
        private String argString;

        public String getClassName()
        {
            return className;
        }

        public String getMethodName()
        {
            return methodName;
        }

        public String getArgString()
        {
            return argString;
        }

        public MappedPropertyParseResult(String className, String methodName, String argString)
        {
            this.className = className;
            this.methodName = methodName;
            this.argString = argString;


        }
    }

    private static final Log log = LogFactory.getLog(ExprNode.class);
}
