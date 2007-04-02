using System;
using System.Collections.Generic;
using System.Text;

using antlr.collections;

using net.esper.core;
using net.esper.compat;
using net.esper.eql.generated;
using net.esper.events;
using net.esper.filter;
using net.esper.type;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
	/// <summary>
    /// Builds a filter specification from filter AST nodes.
    /// </summary>
	
    public class ASTFilterSpecHelper : EqlTokenTypes
	{
        /// <summary> Returns the name tag for the event in the filter spec, if any, else null.</summary>
		/// <param name="filterAST">
		/// </param>
		/// <returns> event name tag or null if none specified
		/// </returns>
		
        public static String getEventNameTag(AST filterAST)
		{
			AST tagNode = filterAST.getFirstChild();
			if ((tagNode != null) && (tagNode.Type == EqlTokenTypes.EVENT_FILTER_NAME_TAG))
			{
				return tagNode.getText();
			}
			return null;
		}
		
		/// <summary> Creates a filter specification for the AST representing the filter expression.</summary>
		/// <param name="filterAST">root filter AST node
		/// </param>
		/// <param name="optionalTaggedEventTypes">event type for each named event if named events are allowed in filter
		/// </param>
		/// <param name="eventAdapterService">service for resolving event names to known event types
		/// </param>
		/// <returns> filter spec
		/// </returns>
		/// <throws>  ASTFilterSpecValidationException if the filter spec cannot be validate </throws>
		public static FilterSpec buildSpec(AST filterAST, EDictionary<String, EventType> optionalTaggedEventTypes, EventAdapterService eventAdapterService) 
        {
			// Ignore the event name tag if the
			AST startNode = filterAST.getFirstChild();
			if (startNode.Type == EqlTokenTypes.EVENT_FILTER_NAME_TAG)
			{
				startNode = startNode.getNextSibling();
			}
			
			String eventName = startNode.getText();
			EventType eventType = eventAdapterService.GetEventType(eventName);
			
			// The type is not known yet, attempt to add as a JavaBean type with the same alias
			if (eventType == null)
			{
				try
				{
					eventType = eventAdapterService.AddBeanType(eventName, eventName);
				}
				catch (EventAdapterException ex)
				{
					throw new ASTFilterSpecValidationException("Failed to resolve event type: " + ex.Message, ex);
				}
			}
			
			// Create parameter list
			AST paramNodeAST = startNode.getNextSibling();
			IList<FilterSpecParam> parameters = new List<FilterSpecParam>();
			while (paramNodeAST != null)
			{
				if (paramNodeAST.Type != EqlTokenTypes.EVENT_FILTER_PARAM)
				{
					throw new SystemException("Expected filter parameter node but received node type " + paramNodeAST.Type);
				}
				FilterSpecParam filterParam = makeParameter(paramNodeAST, eventType);
				parameters.Add(filterParam);
				paramNodeAST = paramNodeAST.getNextSibling();
			}
			
			FilterSpec filterSpec = new FilterSpec(eventType, parameters);
			FilterSpecValidator.Validate(filterSpec, optionalTaggedEventTypes);
			
			return filterSpec;
		}

		private static FilterSpecParam makeParameter(AST filterParamNode, EventType eventType)
		{
			AST propertyIdentNode = filterParamNode.getFirstChild();
			String propertyName;
			// The parameter name can be a simple identifier or event property expression (mapped/indexed/nested/combined)
			if (propertyIdentNode.Type == EqlTokenTypes.IDENT)
			{
				propertyName = propertyIdentNode.getText();
			}
			else if (propertyIdentNode.Type == EqlTokenTypes.EVENT_PROP_EXPR)
			{
				propertyName = getPropertyName(propertyIdentNode.getFirstChild());
			}
			else
			{
				throw new SystemException("AST property name node type unknown, type=" + propertyIdentNode.Type);
			}
			
			// get property type, check it exists
			Type propertyType = eventType.GetPropertyType(propertyName);
			if (propertyType == null)
			{
				throw new ASTFilterSpecValidationException("Property named '" + propertyName + "' not found in class " + eventType.UnderlyingType.FullName);
			}
			
			// Get type representation
			PrimitiveValue primitiveValue = PrimitiveValueFactory.Create(propertyType);
			if (primitiveValue == null)
			{
				throw new ASTFilterSpecValidationException("Property named '" + propertyName + "' of type '" + propertyType.FullName + "' is not supported type");
			}
			
			AST filterCompareNode = filterParamNode.getFirstChild().getNextSibling();
			int nodeType = filterCompareNode.Type;
			if (nodeType == EqlTokenTypes.IN_SET)
			{
				if (!TypeHelper.IsNumeric(propertyType))
				{
					throw new ASTFilterSpecValidationException("Property named '" + propertyName + "' of type '" + propertyType.FullName + "' not numeric as required for ranges");
				}
				return createRangeParam(propertyName, primitiveValue, filterCompareNode);
			}
			else if ((nodeType == EqlTokenTypes.EQUALS) || (nodeType == EqlTokenTypes.NOT_EQUAL) || (nodeType == EqlTokenTypes.LT_) || (nodeType == EqlTokenTypes.LE) || (nodeType == EqlTokenTypes.GT) || (nodeType == EqlTokenTypes.GE))
			{
				return createNonRangeParam(propertyName, primitiveValue, filterCompareNode);
			}
			else
			{
				throw new SystemException("Invalid node type for filter parameter, type=" + nodeType);
			}
		}
		
		/// <summary> Return the generated property name that is defined by the AST child node and it's siblings.</summary>
		/// <param name="propertyNameExprChildNode">is the child node from which to Start putting the property name together
		/// </param>
		/// <returns> property name, ie. indexed[1] or mapped('key') or nested.nested or a combination or just 'simple'.
		/// </returns>
		
        public static String getPropertyName(AST propertyNameExprChildNode)
		{
			StringBuilder buffer = new StringBuilder();
			String delimiter = "";
			AST child = propertyNameExprChildNode;
			
			do 
			{
				buffer.Append(delimiter);
				
				switch (child.Type)
				{
					
					case EqlTokenTypes.EVENT_PROP_SIMPLE: 
						buffer.Append(child.getFirstChild().getText());
						break;
					
					case EqlTokenTypes.EVENT_PROP_MAPPED: 
						buffer.Append(child.getFirstChild().getText());
						buffer.Append("(");
						buffer.Append(child.getFirstChild().getNextSibling().getText());
						buffer.Append(")");
						break;
					
					case EqlTokenTypes.EVENT_PROP_INDEXED: 
						buffer.Append(child.getFirstChild().getText());
						buffer.Append("[");
						buffer.Append(child.getFirstChild().getNextSibling().getText());
						buffer.Append("]");
						break;
					
					default: 
						throw new SystemException("Event property AST node not recognized, type=" + child.Type);
					
				}
				
				delimiter = ".";
				child = child.getNextSibling();
			}
			while (child != null);
			
			return buffer.ToString();
		}
		
		private static FilterSpecParam createNonRangeParam(String propertyName, PrimitiveValue primitiveValue, AST filterParamNode)
		{
			FilterOperator? _operator = FilterOperatorHelper.ParseComparisonOperator(filterParamNode.getText());
			
			// Deal with use-result filter parameters
			if (filterParamNode.getFirstChild().Type == EqlTokenTypes.EVENT_FILTER_IDENT)
			{
				AST identRoot = filterParamNode.getFirstChild();
				String eventAsName = identRoot.getFirstChild().getText();
				AST propertyRoot = identRoot.getFirstChild().getNextSibling().getFirstChild();
				String eventProperty = getPropertyName(propertyRoot);
				return new FilterSpecParamEventProp(propertyName, _operator.Value, eventAsName, eventProperty);
			}
			
			// Deal with constants
			AST constantNode = filterParamNode.getFirstChild();
			int constantType = constantNode.Type;
			if (!ASTConstantHelper.canConvert(constantType, primitiveValue.Type))
			{
				String message = getConversionErrorMsg(constantType, primitiveValue.Type, propertyName);
				throw new ASTFilterSpecValidationException(message);
			}
			
			String stringValue = constantNode.getText();
			try
			{
				primitiveValue.Parse(stringValue);
			}
			catch (SystemException ex)
			{
				String message =
                    "Conversion from datatype '" + ASTConstantHelper.getConstantTypeName(constantType) +
                    "' to '" + primitiveValue.Type.ToString() + 
                    "' for property '" + propertyName +
                    "' failed with error:" + ex.Message;
				log.Debug(".createNonRangeParam " + message, ex);
				throw new ASTFilterSpecValidationException(ex.Message);
			}
			Object value = primitiveValue.ValueObject;
			
			return new FilterSpecParamConstant(propertyName, _operator.Value, value);
		}
		
		private static FilterSpecParam createRangeParam(String propertyName, PrimitiveValue primitiveValue, AST filterParamNode)
		{
			// Deal with Ranges
			AST ast = filterParamNode.getFirstChild();
			bool lowInclusive = ast.Type == EqlTokenTypes.LBRACK;
			
			ast = ast.getNextSibling();
			if ((ast.Type != EqlTokenTypes.EVENT_FILTER_IDENT) && (!ASTConstantHelper.canConvert(ast.Type, primitiveValue.Type)))
			{
				String message = getConversionErrorMsg(ast.Type, primitiveValue.Type, propertyName);
				throw new ASTFilterSpecValidationException(message);
			}
			FilterSpecParamRangeValue valueMin = getRangePoint(ast, primitiveValue);
			
			ast = ast.getNextSibling();
			if ((ast.Type != EqlTokenTypes.EVENT_FILTER_IDENT) && (!ASTConstantHelper.canConvert(ast.Type, primitiveValue.Type)))
			{
				String message = getConversionErrorMsg(ast.Type, primitiveValue.Type, propertyName);
				throw new ASTFilterSpecValidationException(message);
			}
			FilterSpecParamRangeValue valueMax = getRangePoint(ast, primitiveValue);
			
			ast = ast.getNextSibling();
			bool highInclusive = ast.Type == EqlTokenTypes.RBRACK;
			
			FilterOperator _operator = FilterOperatorHelper.ParseRangeOperator(lowInclusive, highInclusive);
			
			return new FilterSpecParamRange(propertyName, _operator, valueMin, valueMax);
		}
		
		private static FilterSpecParamRangeValue getRangePoint(AST ast, PrimitiveValue primitiveValue)
		{
			if (ast.Type == EqlTokenTypes.EVENT_FILTER_IDENT)
			{
				String eventAsName = ast.getFirstChild().getText();
				AST propertyRoot = ast.getFirstChild().getNextSibling().getFirstChild();
				String eventProperty = getPropertyName(propertyRoot);
				return new RangeValueEventProp(eventAsName, eventProperty);
			}
			else
			{
				// Parse text node
				String value = ast.getText();
				primitiveValue.Parse(value);
				double dbl = Convert.ToDouble(primitiveValue.ValueObject);
				return new RangeValueDouble(dbl);
			}
		}
		
		private static String getConversionErrorMsg(int astConstantType, PrimitiveValueType primitiveValueType, String propertyName)
		{
			return 
                "Implicit conversion from datatype '" + ASTConstantHelper.getConstantTypeName(astConstantType) +
                "' to '" + primitiveValueType.ToString() + 
                "' for property '" + propertyName + 
                "' is not allowed";
		}
		
		private static Log log = LogFactory.GetLog(typeof(ASTFilterSpecHelper));
	}
}
