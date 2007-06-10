using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

using net.esper.compat;
using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary> Superclass for filter nodes in a filter expression tree. Allow
    /// validation against stream event types and evaluation of events against filter tree.
    /// </summary>

    public abstract class ExprNode
		: ExprValidator
		, ExprEvaluator
		, MetaDefItem
    {
        private readonly IList<ExprNode> childNodes;

        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>

        public abstract Type ReturnType
        {
            get;
        }

        /// <summary> Returns the expression node rendered as a string.</summary>
        /// <returns> string rendering of expression
        /// </returns>

        public abstract String ExpressionString
        {
            get;
        }

	    /**
	     * Returns true if the expression node's evaluation value doesn't depend on any events data,
	     * as must be determined at validation time, which is bottom-up and therefore
	     * reliably allows each node to determine constant value.
	     * @return true for constant evaluation value, false for non-constant evaluation value
	     */
	    public abstract bool IsConstantResult
		{
			get;
		}
		
		/// <summary> Returns list of child nodes.</summary>
		/// <returns> list of child nodes
		/// </returns>

		public IList<ExprNode> ChildNodes
		{
			get { return childNodes; }
		}

		/// <summary>
        /// Return true if a expression node semantically equals the current node, or false if not.
        ///
        /// Concrete implementations should compare the type and any additional information
        /// that impact the evaluation of a node.
        /// </summary>
        /// <param name="node">to compare to
        /// </param>
        /// <returns> true if semantically equal, or false if not equals
        /// </returns>
        public abstract bool EqualsNode(ExprNode node);

        /// <summary>
        /// Constructor creates a list of child nodes.
        /// </summary>

        protected ExprNode()
        {
            childNodes = new List<ExprNode>();
        }

	    /**
	     * Validates the expression node subtree that has this
	     * node as root. Some of the nodes of the tree, including the
	     * root, might be replaced in the process.
	     * @param streamTypeService - serves stream type information
	     * @param methodResolutionService - for resolving class names in library method invocations
	     * @param viewResourceDelegate - delegates for view resources to expression nodes
	     * @throws ExprValidationException when the validation fails
	     * @return the root node of the validated subtree, possibly
	     *         different than the root node of the unvalidated subtree
	     */
		public ExprNode GetValidatedSubtree(
			StreamTypeService streamTypeService,
			MethodResolutionService methodResolutionService,
			ViewResourceDelegate viewResourceDelegate)
		{
            ExprNode result = this;

            for (int i = 0; i < childNodes.Count; i++)
            {
                childNodes[i] = childNodes[i].GetValidatedSubtree(streamTypeService, methodResolutionService, viewResourceDelegate);
            }

            try
            {
                Validate(streamTypeService, methodResolutionService, viewResourceDelegate);
            }
            catch (ExprValidationException e)
            {
                if (this is ExprIdentNode)
                {
                    result = ResolveIdentAsStaticMethod(streamTypeService, methodResolutionService, e);
                }
                else
                {
                    throw;
                }
            }

            return result;
        }

        /// <summary>
        /// Accept the visitor. The visitor will first visit the parent then visit all child nodes, then their child nodes.
        ///
        /// The visitor can decide to skip child nodes by returning false in isVisit.
        /// </summary>
        /// <param name="visitor">to visit each node and each child node.
        /// </param>
        public virtual void Accept(ExprNodeVisitor visitor)
        {
            if (visitor.IsVisit(this))
            {
                visitor.Visit(this);

                foreach (ExprNode childNode in childNodes)
                {
                    childNode.Accept(visitor);
                }
            }
        }

        /// <summary>
        /// Adds a child node.
        /// </summary>
        /// <param name="childNode">is the child evaluation tree node to add
        /// </param>
        public void AddChildNode(ExprNode childNode)
        {
            childNodes.Add(childNode);
        }

        /// <summary> Recursively print out all nodes.</summary>
        /// <param name="prefix">is printed out for naming the printed info
        /// </param>
        public void DumpDebug(String prefix)
        {
            log.Debug(".DumpDebug " + prefix + this.ToString());
            foreach (ExprNode node in childNodes)
            {
                node.DumpDebug(prefix + "  ");
            }
        }

        /// <summary> Compare two expression nodes and their children in exact child-node sequence,
        /// returning true if the 2 expression nodes trees are equals, or false if they are not equals.
        ///
        /// Recursive call since it uses this method to compare child nodes in the same exact sequence.
        /// Nodes are compared using the EqualsNode method.
        /// </summary>
        /// <param name="nodeOne">first expression top node of the tree to compare
        /// </param>
        /// <param name="nodeTwo">second expression top node of the tree to compare
        /// </param>
        /// <returns> false if this or all child nodes are not equal, true if equal
        /// </returns>

        public static bool DeepEquals(ExprNode nodeOne, ExprNode nodeTwo)
        {
            if (nodeOne.childNodes.Count != nodeTwo.childNodes.Count)
            {
                return false;
            }
            if (!nodeOne.EqualsNode(nodeTwo))
            {
                return false;
            }
            for (int i = 0; i < nodeOne.childNodes.Count; i++)
            {
                ExprNode childNodeOne = nodeOne.childNodes[i];
                ExprNode childNodeTwo = nodeTwo.childNodes[i];

                if (!ExprNode.DeepEquals(childNodeOne, childNodeTwo))
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
	    private ExprNode ResolveIdentAsStaticMethod(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ExprValidationException propertyException)
        {
	        // Reconstruct the original string
	        ExprIdentNode identNode = (ExprIdentNode) this;
	        StringBuilder mappedProperty = new StringBuilder(identNode.UnresolvedPropertyName);
	        if(identNode.StreamOrPropertyName != null)
	        {
				mappedProperty.Insert(0, '.');
	            mappedProperty.Insert(0, identNode.StreamOrPropertyName);
	        }

	        // Parse the mapped property format into a class name, method and single string parameter
	        MappedPropertyParseResult parse = ParseMappedProperty(mappedProperty.ToString());
	        if (parse == null)
	        {
	            throw propertyException;
	        }

	        // If there is a type name, assume a static method is possible
	        if (parse.TypeName != null)
	        {
	            ExprNode result = new ExprStaticMethodNode(parse.TypeName, parse.MethodName);
	            result.AddChildNode(new ExprConstantNode(parse.ArgString));

	            // Validate
	            try
	            {
	                result.Validate(streamTypeService, methodResolutionService, null);
	            }
	            catch(ExprValidationException e)
	            {
	                throw new ExprValidationException("Failed to resolve " + mappedProperty + " as either an event property or as a static method invocation");
	            }

	            return result;
	        }

	        // There is no class name, try an aggregation function
	        try
	        {
	            AggregationSupport aggregation = methodResolutionService.ResolveAggregation(parse.MethodName);
	            ExprNode result = new ExprPlugInAggFunctionNode(false, aggregation, parse.MethodName);
	            result.AddChildNode(new ExprConstantNode(parse.ArgString));

	            // Validate
	            try
	            {
	                result.Validate(streamTypeService, methodResolutionService, null);
	            }
	            catch (Exception e)
	            {
	                throw new ExprValidationException("Plug-in aggregation function '" + parse.MethodName + "' failed validation: " + e.Message);
	            }

	            return result;
	        }
	        catch (EngineImportUndefinedException e)
	        {
	            // Not an aggregation function
	        }
	        catch (EngineImportException e)
	        {
	            throw new IllegalStateException("Error resolving aggregation: " + e.getMessage(), e);
	        }

	        // absolutly cannot be resolved
	        throw propertyException;
	    }

	    /**
	     * Parse the mapped property into classname, method and string argument.
	     * Mind this has been parsed already and is a valid mapped property.
	     * @param property is the string property to be passed as a static method invocation
	     * @return descriptor object
	     */
	    protected static MappedPropertyParseResult ParseMappedProperty(String property)
	    {
	        // get argument
	        int indexFirstDoubleQuote = property.IndexOf("\"");
	        int indexFirstSingleQuote = property.IndexOf("'");
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

	        int indexLastDoubleQuote = property.LastIndexOf("\"");
	        int indexLastSingleQuote = property.LastIndexOf("'");
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
	        String argument = property.Substring(startArg + 1, endArg);

	        // get method
	        String[] splitDots = property.ToString().Split("[\\.]");
	        if (splitDots.Length == 0)
	        {
	            return null;
	        }

	        String method = splitDots[splitDots.Length - 1];
	        int indexParan = method.IndexOf("(");
	        if (indexParan == -1)
	        {
	            return null;
	        }
	        method = method.Substring(0, indexParan);
	        if (method.Length == 0)
	        {
	            return null;
	        }

	        if (splitDots.Length == 1)
	        {
	            // no type name
	            return new MappedPropertyParseResult(null, method, argument);
	        }

	        // get type
	        StringBuilder type = new StringBuilder();
	        for (int i = 0; i < splitDots.Length - 1; i++)
	        {
	            if (i > 0)
	            {
	                type.Append('.');
	            }
	            type.Append(splitDots[i]);
	        }

	        return new MappedPropertyParseResult(type.ToString(), method, argument);
	    }

	    /**
	     * Encapsulates the parse result parsing a mapped property as a type and method name with args.
	     */
	    protected class MappedPropertyParseResult
	    {
	        private String typeName;
	        private String methodName;
	        private String argString;

	        /**
	         * Returns class name.
	         * @return name of class
	         */
	        public String TypeName
	        {
	            get { return typeName; }
	        }

	        /**
	         * Returns the method name.
	         * @return method name
	         */
	        public String MethodName
	        {
	            get { return methodName; }
	        }

	        /**
	         * Returns the method argument.
	         * @return arg
	         */
	        public String ArgString
	        {
	            get { return argString; }
	        }

	        /**
	         * Returns the parse result of the mapped property.
	         * @param typeName is the type name, or null if there isn't one
	         * @param methodName is the method name
	         * @param argString is the argument
	         */
	        public MappedPropertyParseResult(String typeName, String methodName, String argString)
	        {
	            this.typeName = typeName;
	            this.methodName = methodName;
	            this.argString = argString;
	        }
		}

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <returns>evaluation result, a bool value for OR/AND-type evalution nodes.</returns>

        public abstract Object Evaluate(EventBean[] eventsPerStream, bool isNewData);

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>

        public abstract void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate);

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
