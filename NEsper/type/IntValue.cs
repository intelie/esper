using System;

namespace net.esper.type
{
	/// <summary>
	///  Placeholder for an integer value in an event expression.
	/// </summary>
	
	public sealed class IntValue:PrimitiveValueBase
	{
		override public PrimitiveValueType Type
		{
			get { return PrimitiveValueType.INTEGER; }
		}
		
		override public Object ValueObject
		{
			get { return intValue; }
		}
		
		override public int _Int
		{
			set { this.intValue = value; }
		}
		
		private int? intValue;
		
		/// <summary>
		/// Constructor.
		/// </summary>
		
		public IntValue()
		{
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="intValue">is the value to set to
		/// </param>

		public IntValue(int intValue)
		{
			this.intValue = intValue;
		}
		
		/// <summary> Parse the string array returning a int array.</summary>
		/// <param name="values">- string array
		/// </param>
		/// <returns> typed array
		/// </returns>
		
		public static int[] ParseString(String[] values)
		{
			int[] result = new int[values.Length];
			for (int i = 0; i < result.Length; i++)
			{
				result[i] = ParseString(values[i]);
			}
			
			return result;
		}
		
		/// <summary> Parse string value returning a int.</summary>
		/// <param name="value">to parse
		/// </param>
		/// <returns> parsed value
		/// </returns>
		
		public static int ParseString(String value)
		{
			return Int32.Parse(value);
		}
		
		public override void parse(String value)
		{
			intValue = Int32.Parse(value);
		}
		
		public override String ToString()
		{
			if (intValue == null)
			{
				return "null";
			}
			return intValue.ToString();
		}
	}
}
