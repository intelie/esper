using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;

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

        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        override public Type ReturnType
        {
            get
            {
                if (staticMethod == null)
                {
                    throw new SystemException("ExprStaticMethodNode has not been validated");
                }
                return staticMethod.ReturnType;
            }
        }

        private readonly String typeName;
        private readonly String methodName;
        private Type[] paramTypes;
        private MethodInfo staticMethod;

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
                throw new SystemException("ExprStaticMethodNode has not been validated");
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
        /// <param name="autoImportService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            // Get the types of the childNodes
        	IList<ExprNode> childNodes = ChildNodes ;
            paramTypes = new Type[childNodes.Count];
            int count = 0;
            foreach (ExprNode childNode in childNodes)
            {
                paramTypes[count++] = childNode.ReturnType;
            }

            // Try to resolve the method
            try
            {
                MethodInfo method = StaticMethodResolver.ResolveMethod(typeName, methodName, paramTypes, autoImportService);
                staticMethod = method;
            }
            catch (System.Exception e)
            {
                throw new ExprValidationException(e.Message);
            }
        }

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <returns>
        /// evaluation result, a boolean value for OR/AND-type evalution nodes.
        /// </returns>
        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            IList<ExprNode> childNodes = this.ChildNodes;

            Object[] args = new Object[childNodes.Count];
            int count = 0;
            foreach (ExprNode childNode in childNodes)
            {
                args[count++] = childNode.Evaluate(eventsPerStream);
            }

            // The method is static so the object it is invoked on
            // can be null
            Object obj = null;
            try
            {
                return staticMethod.Invoke(obj, args);
            }
            catch (TargetInvocationException e)
            {
                throw new EPException(e);
            }
        }
    }
}
