using System;

using antlr.collections;

using net.esper.type;
using net.esper.eql.generated;

namespace net.esper.eql.parse
{
	/// <summary> Parses constant strings and returns the constant Object.</summary>
	public class ASTConstantHelper : EqlTokenTypes
	{
		/// <summary> Answers the question of whether a given AST constant node can be converted via parsing into a primitive value
		/// of a given type.
		/// </summary>
		/// <param name="astTypeConstant">is the AST constant node's type
		/// </param>
		/// <param name="targetType">is the primitve value's type which will be used to parse the string
		/// </param>
		/// <returns> true if the primitive value can be used to parse the AST node, false if not
		/// </returns>
		public static bool canConvert(int astTypeConstant, PrimitiveValueType targetType)
		{
			switch (astTypeConstant)
			{
				
				case EqlTokenTypes.STRING_TYPE: 
					return (targetType == PrimitiveValueType.STRING);
				
				case EqlTokenTypes.BOOL_TYPE: 
					return (targetType == PrimitiveValueType.BOOL);
				
				case EqlTokenTypes.FLOAT_TYPE: 
					return ((targetType == PrimitiveValueType.FLOAT) || (targetType == PrimitiveValueType.DOUBLE));
				
				case EqlTokenTypes.DOUBLE_TYPE: 
					return (targetType == PrimitiveValueType.DOUBLE);
				
				case EqlTokenTypes.LONG_TYPE: 
					return ((targetType == PrimitiveValueType.FLOAT) || (targetType == PrimitiveValueType.DOUBLE) || (targetType == PrimitiveValueType.LONG));
				
				case EqlTokenTypes.NUM_INT: 
				case EqlTokenTypes.INT_TYPE: 
					return ((targetType == PrimitiveValueType.BYTE) || (targetType == PrimitiveValueType.SHORT) || (targetType == PrimitiveValueType.INTEGER) || (targetType == PrimitiveValueType.LONG) || (targetType == PrimitiveValueType.DOUBLE) || (targetType == PrimitiveValueType.FLOAT));
				
				default: 
					throw new ArgumentException("Unexpected constant of type " + astTypeConstant + " encountered");
				
			}
		}
		
		/// <summary> Returns the type name for the given AST constant type.</summary>
		/// <param name="astTypeConstant">is the type of the AST constant node.
		/// </param>
		/// <returns> name of the type
		/// </returns>
		public static String getConstantTypeName(int astTypeConstant)
		{
			switch (astTypeConstant)
			{
				case EqlEvalTokenTypes.NUM_INT:
                    return PrimitiveValueType.INTEGER.ToString();
				case EqlEvalTokenTypes.INT_TYPE:
                    return PrimitiveValueType.INTEGER.ToString();
				case EqlEvalTokenTypes.LONG_TYPE:
                    return PrimitiveValueType.LONG.ToString();
				case EqlEvalTokenTypes.FLOAT_TYPE:
                    return PrimitiveValueType.FLOAT.ToString();
				case EqlEvalTokenTypes.DOUBLE_TYPE:
                    return PrimitiveValueType.DOUBLE.ToString();
				case EqlEvalTokenTypes.STRING_TYPE:
                    return PrimitiveValueType.STRING.ToString();
				case EqlEvalTokenTypes.BOOL_TYPE:
                    return PrimitiveValueType.BOOL.ToString();
				case EqlEvalTokenTypes.NULL_TYPE:
                    return "null/unknwown type";
				default: 
					throw new ArgumentException("Unexpected constant of type " + astTypeConstant + " encountered");
				
			}
		}
		
		/// <summary> Parse the AST constant node and return Object value.</summary>
		/// <param name="node">parse node for which to parse the string value
		/// </param>
		/// <returns> value matching AST node type
		/// </returns>
		public static Object parse(AST node)
		{
			switch (node.Type)
			{
				case EqlEvalTokenTypes.NUM_INT:
                    return IntValue.ParseString(node.getText());
				case EqlEvalTokenTypes.INT_TYPE:
                    return IntValue.ParseString(node.getText());
				case EqlEvalTokenTypes.LONG_TYPE:
                    return LongValue.ParseString(node.getText());
				case EqlEvalTokenTypes.BOOL_TYPE:
                    return BoolValue.ParseString(node.getText());
				case EqlEvalTokenTypes.FLOAT_TYPE:
                    return FloatValue.ParseString(node.getText());
				case EqlEvalTokenTypes.DOUBLE_TYPE:
                    return DoubleValue.ParseString(node.getText());
				case EqlEvalTokenTypes.STRING_TYPE:
                    return StringValue.ParseString(node.getText());
				case EqlEvalTokenTypes.NULL_TYPE:
                    return null;
				default: 
					throw new ArgumentException("Unexpected constant of type " + node.Type + " encountered, this class supports only primitgive types");
			}
		}
	}
}