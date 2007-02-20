using System;
namespace net.esper.type
{
	
	/// <summary> Placeholder for a String value in an event expression.</summary>
	public sealed class StringValue:PrimitiveValueBase
	{
		override public PrimitiveValueType Type
		{
			get
			{
				return PrimitiveValueType.STRING;
			}
			
		}
		override public Object ValueObject
		{
			get
			{
				return stringValue;
			}
			
		}
		override public String _String
		{
			set
			{
				this.stringValue = value;
			}
			
		}
		private String stringValue;
		
		/// <summary> Constructor.</summary>
		/// <param name="stringValue">sets initial value
		/// </param>
		public StringValue(String stringValue)
		{
			this.stringValue = stringValue;
		}
		
		/// <summary> Constructor.</summary>
		public StringValue()
		{
		}
		
		/// <summary> Parse the string array returning a string array.</summary>
		/// <param name="values">- string array
		/// </param>
		/// <returns> typed array
		/// </returns>
		public static String[] ParseString(String[] values)
		{
			String[] result = new String[values.Length];
			for (int i = 0; i < result.Length; i++)
			{
				result[i] = ParseString(values[i]);
			}
			return result;
		}
		
		public override void  parse(String value)
		{
			stringValue = ParseString(value);
		}
		
		public override String ToString()
		{
			if (stringValue == null)
			{
				return "null";
			}
			return stringValue;
		}
		
		/// <summary> Parse the string literal consisting of text between double-quotes or single-quotes.</summary>
		/// <param name="value">is the text wthin double or single quotes
		/// </param>
		/// <returns> parsed value
		/// </returns>
		public static String ParseString(String value)
		{
			if ((value.StartsWith("\"")) & (value.EndsWith("\"")) || (value.StartsWith("'")) & (value.EndsWith("'")))
			{
				if (value.Length > 1)
				{
                    String stringValue = value.Substring(1, value.Length - 2);
					return stringValue;
				}
			}
			
			throw new ArgumentException("String value of '" + value + "' cannot be parsed");
		}
	}
}