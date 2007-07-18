using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;

using net.esper.compat;
using net.esper.client;
using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents an invocation of a static library method in the expression tree.
    /// </summary>

    public class ExprStaticMethodNode : ExprNode
    {
        /// <summary> Returns the static method.</summary>
        /// <returns> the static method that this node invokes
        /// </returns>
        virtual public MethodInfo StaticMethod
        {
            get { return staticMethod; }
        }

        /// <summary> Returns the class name.</summary>
        /// <returns> the class that declared the static method
        /// </returns>
        virtual public String ClassName
        {
            get { return typeName; }
        }

        /// <summary> Returns the method name.</summary>
        /// <returns> the name of the method
        /// </returns>
        virtual public String MethodName
        {
            get { return methodName; }
        }

        /// <summary> Returns parameter descriptor.</summary>
        /// <returns> the types of the child nodes of this node
        /// </returns>
        virtual public Type[] ParamTypes
        {
            get { return paramTypes; }
        }

	    public override bool IsConstantResult
	    {
	        get { return isConstantParameters; }
	    } 
		
        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override Type ReturnType
        {
            get
            {
                if (staticMethod == null)
                {
                    throw new IllegalStateException("ExprStaticMethodNode has not been validated");
                }
                return TypeHelper.GetBoxedType(staticMethod.ReturnType);
            }
        }

        private readonly String typeName;
        private readonly String methodName;
        private Type[] paramTypes;
        private MethodInfo staticMethod;
	    private bool isConstantParameters;
		private bool isCachedResult;
		private Object cachedResult;

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="typeName">the declaring class for the method that this node will invoke</param>
        /// <param name="methodName">the name of the method that this node will invoke</param>

        public ExprStaticMethodNode(String typeName, String methodName)
        {
            if (typeName == null)
            {
                throw new System.NullReferenceException("Type name is null");
            }
            if (methodName == null)
            {
                throw new System.NullReferenceException("Method name is null");
            }

            this.typeName = typeName;
            this.methodName = methodName;
        }

        /// <summary>
        /// Returns the expression node rendered as a string.
        /// </summary>
        /// <value></value>
        /// <returns> string rendering of expression
        /// </returns>
        public override String ExpressionString
        {
            get
            {
                StringBuilder buffer = new StringBuilder();
                buffer.Append(typeName);
                buffer.Append(".");
                buffer.Append(methodName);

                buffer.Append("(");
                String appendString = "";
                foreach (ExprNode child in ChildNodes)
                {
                    buffer.Append(appendString);
                    buffer.Append(child.ExpressionString);
                    appendString = ", ";
                }
                buffer.Append(")");


                return buffer.ToString();
            }
        }

        /// <summary>
        /// Return true if a expression node semantically equals the current node, or false if not.
        /// Concrete implementations should compare the type and any additional information
        /// that impact the evaluation of a node.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns>
        /// true if semantically equal, or false if not equals
        /// </returns>
        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprStaticMethodNode))
            {
                return false;
            }

            if (staticMethod == null)
            {
                throw new IllegalStateException("ExprStaticMethodNode has not been validated");
            }
            else
            {
                ExprStaticMethodNode otherNode = (ExprStaticMethodNode)node;
                return staticMethod.Equals(otherNode.staticMethod);
            }
        }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">for resolving class names in library method invocations</param>
        /// <param name="viewResourceDelegate">The view resource delegate.</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
        {
            // Get the types of the childNodes
        	IList<ExprNode> childNodes = ChildNodes ;
            paramTypes = new Type[childNodes.Count];
            int count = 0;
			
	        bool allConstants = true;
	        foreach(ExprNode childNode in childNodes)
			{
				paramTypes[count++] = childNode.ReturnType;
	            if (!childNode.IsConstantResult)
	            {
	                allConstants = false;
	            }
	        }
	        isConstantParameters = allConstants;

	        // Try to resolve the method
			try
			{
				staticMethod = methodResolutionService.ResolveMethod(typeName, methodName, paramTypes);
			}
			catch(Exception e)
			{
				throw new ExprValidationException(e.Message);
			}
        }

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <param name="isNewData">indicates whether we are dealing with new data (istream) or old data (rstream)</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>
        public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
        {
		    if ((isConstantParameters) && (isCachedResult))
	        {
	            return cachedResult;
	        }
			
            IList<ExprNode> childNodes = this.ChildNodes;

            Object[] args = new Object[childNodes.Count];
            int count = 0;
            foreach (ExprNode childNode in childNodes)
            {
                args[count++] = childNode.Evaluate(eventsPerStream, isNewData);
            }

            // The method is static so the object it is invoked on
            // can be null
            Object obj = null;
            try
            {
                Object result = staticMethod.Invoke(obj, args);
				if (isConstantParameters)
	            {
	                cachedResult = result;
	                isCachedResult = true;
	            }
	            return result;
            }
            catch (TargetInvocationException e)
            {
                throw new EPException(e);
            }
        }
    }
}
