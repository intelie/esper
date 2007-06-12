using System;

using antlr.collections;

using net.esper.type;
using net.esper.eql.generated;

namespace net.esper.eql.parse
{
	/// <summary>
	/// Parses constant strings and returns the constant Object.
	/// </summary>
	public class ASTConstantHelper : EqlTokenTypes
	{
		/// <summary> Parse the AST constant node and return Object value.</summary>
		/// <param name="node">parse node for which to parse the string value
		/// </param>
		/// <returns> value matching AST node type
		/// </returns>
		public static Object Parse(AST node)
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
		
	    private static Object ParseIntLong(String arg)
	    {
	        // try to parse as an int first, else try to parse as a long
	        try
	        {
	            return Int32.Parse(arg);
	        }
	        catch (FormatException)
	        {
                return Int64.Parse(arg);
	        }
	    }
	}
}