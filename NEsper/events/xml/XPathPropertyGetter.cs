using System;
using System.Xml;
using System.Xml.XPath;

using net.esper.events;

namespace net.esper.events.xml
{
	/// <summary>Getter for properties of DOM xml events.</summary>
	/// <author>pablo</author>
	
    public class XPathPropertyGetter : TypedEventPropertyGetter
	{
        internal XPathExpression expression;
		internal String property;
		internal Type resultType;

		virtual public Type ResultClass
		{
			get { return resultType; }
		}

		/// <summary>
		/// Ctor.
		/// </summary>
		/// <param name="propertyName">is the name of the event property for which this getter gets values</param>
		/// <param name="xPathExpression">is a compile XPath expression</param>
		/// <param name="resultType">is the resulting type</param>
		
        public XPathPropertyGetter( String propertyName, XPathExpression xPathExpression, Type resultType )
		{
			this.expression = xPathExpression;
			this.property = propertyName;
			this.resultType = resultType;
		}

		/// <summary>
		/// Gets the property from the specified event bean.
		/// </summary>
		/// <value></value>

        public virtual Object GetValue(EventBean eventBean)
        {
            XmlNode node = eventBean.Underlying as XmlNode;
            if (node == null)
            {
                throw new PropertyAccessException("XPathPropertyGetter only usable on XmlNode events");
            }

            try
            {
                XPathNavigator navigator = node.CreateNavigator();
                Object result = navigator.Evaluate(expression);

                // The result of the expression (Boolean, number, string, or node set).
                // This maps to Boolean, Double, String, or XPathNodeIterator objects
                // respectively. 

                switch (expression.ReturnType)
                {
                    case XPathResultType.Boolean:
                    case XPathResultType.Number:
                    case XPathResultType.String:
                    case XPathResultType.Any:
                        result = Convert.ChangeType(result, resultType);
                        break;
                    case XPathResultType.NodeSet:
                        {
                            XPathNodeIterator iterator = result as XPathNodeIterator;
                            if (iterator.MoveNext())
                            {
                                XPathNavigator current = iterator.Current;
                                if (resultType == typeof(XmlNode))
                                {
                                    result = current.UnderlyingObject;
                                }
                                else
                                {
                                    result = Convert.ChangeType(iterator.Current.Value, resultType);
                                }
                            }
                            else
                            {
                                result = String.Empty;
                            }
                        }
                        break;
                }

                //Object result = expression.Evaluate(und, resultType);
                return result;
            }
            catch (XPathException e)
            {
                throw new PropertyAccessException("Error getting property " + property, e);
            }
        }
	}
}
