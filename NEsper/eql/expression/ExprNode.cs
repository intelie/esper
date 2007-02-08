using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

using net.esper.eql.core;
using net.esper.events;

namespace net.esper.eql.expression
{
    /// <summary> Superclass for filter nodes in a filter expression tree. Allow
    /// validation against stream event types and evaluation of events against filter tree.
    /// </summary>

    public abstract class ExprNode : ExprValidator, ExprEvaluator
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

		/// <summary> Returns list of child nodes.</summary>
		/// <returns> list of child nodes
		/// </returns>

		public IList<ExprNode> ChildNodes
		{
			get { return childNodes; }
		}
		
		/// <summary> Return true if a expression node semantically equals the current node, or false if not.
        /// <p>Concrete implementations should compare the type and any additional information
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

        public ExprNode()
        {
            childNodes = new List<ExprNode>();
        }

        /// <summary> Validates the expression node subtree that has this
        /// node as root. Some of the nodes of the tree, including the 
        /// root, might be replaced in the process.
        /// </summary>
        /// <param name="streamTypeService">- serves stream type information
        /// </param>
        /// <param name="autoImportService">- for resolving class names in library method invocations
        /// </param>
        /// <throws>  ExprValidationException when the validation fails </throws>
        /// <returns> the root node of the validated subtree, possibly 
        /// different than the root node of the unvalidated subtree 
        /// </returns>

        public virtual ExprNode GetValidatedSubtree(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            ExprNode result = this;

            for (int i = 0; i < childNodes.Count; i++)
            {
                childNodes[i] = childNodes[i].GetValidatedSubtree(streamTypeService, autoImportService);
            }

            try
            {
                validate(streamTypeService, autoImportService);
            }
            catch (ExprValidationException e)
            {
                if (this is ExprIdentNode)
                {
                    result = ResolveIdentAsStaticMethod(streamTypeService, autoImportService, e);
                }
                else
                {
                    throw e;
                }
            }

            return result;
        }

        /// <summary> Accept the visitor. The visitor will first visit the parent then visit all child nodes, then their child nodes.
        /// <p>The visitor can decide to skip child nodes by returning false in isVisit.
        /// </summary>
        /// <param name="visitor">to visit each node and each child node.
        /// </param>
        public virtual void Accept(ExprNodeVisitor visitor)
        {
            if (visitor.isVisit(this))
            {
                visitor.visit(this);

                foreach (ExprNode childNode in childNodes)
                {
                    childNode.Accept(visitor);
                }
            }
        }

        /// <summary> Adds a child node.</summary>
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
        /// <p>
        /// Recursive call since it uses this method to compare child nodes in the same exact sequence.
        /// Nodes are compared using the EqualsNode method.
        /// </summary>
        /// <param name="nodeOne">- first expression top node of the tree to compare
        /// </param>
        /// <param name="nodeTwo">- second expression top node of the tree to compare
        /// </param>
        /// <returns> false if this or all child nodes are not equal, true if equal
        /// </returns>

        public static bool DeepEquals(ExprNode nodeOne, ExprNode nodeTwo)
        {
            if (nodeOne.ChildNodes.Count != nodeTwo.ChildNodes.Count)
            {
                return false;
            }
            if (!nodeOne.EqualsNode(nodeTwo))
            {
                return false;
            }
            for (int i = 0; i < nodeOne.ChildNodes.Count; i++)
            {
                ExprNode childNodeOne = nodeOne.ChildNodes[i];
                ExprNode childNodeTwo = nodeTwo.ChildNodes[i];

                if (!ExprNode.DeepEquals(childNodeOne, childNodeTwo))
                {
                    return false;
                }
            }
            return true;
        }

        // Assumes that this is an ExprIdentNode
        private ExprNode ResolveIdentAsStaticMethod(StreamTypeService streamTypeService, AutoImportService autoImportService, ExprValidationException propertyException)
        {
            // Reconstruct the original string
            ExprIdentNode identNode = (ExprIdentNode)this;
            StringBuilder name = new StringBuilder(identNode.UnresolvedPropertyName);
            if (identNode.StreamOrPropertyName != null)
            {
                name.Insert(0, identNode.StreamOrPropertyName + ".");
            }

            // Parse the string to see if it looks like a method invocation
            // (Ident nodes can only have a single string value in parentheses)
            String classNameRegEx = "((\\w+\\.)*\\w+)";
            String methodNameRegEx = "(\\w+)";
            String argsRegEx = "\\s*\\(\\s*[\'\"]\\s*(\\w+)\\s*[\'\"]\\s*\\)\\s*";
            String methodInvocationRegEx = classNameRegEx + "\\." + methodNameRegEx + argsRegEx;

            Regex regex = new Regex(methodInvocationRegEx);
			Match match = regex.Match( name.ToString() );
            if ( ! match.Success )
            {
                // This property name doesn't look like a method invocation
                throw propertyException;
            }

            // Create a new static method node and add the method
            // argument as a child node
            String className = match.Groups[1].Value;
            String methodName = match.Groups[3].Value;
            String argString = match.Groups[4].Value;
            ExprNode result = new ExprStaticMethodNode(className, methodName);
            result.AddChildNode(new ExprConstantNode(argString));

            // Validate
            try
            {
                result.validate(streamTypeService, autoImportService);
            }
            catch (ExprValidationException)
            {
                throw new ExprValidationException(
                    "Failed to resolve " + name +
                    " as either an event property or as a static method invocation");
            }

            return result;
        }

        public abstract Object Evaluate(EventBean[] param1);
        public abstract void validate(StreamTypeService param1, AutoImportService param2);

        private static readonly Log log = LogFactory.GetLog(typeof(ExprNode));
    }
}