using System;

namespace net.esper.type
{
	/// <summary>
	/// Placeholder for a float value in an event expression.
	/// </summary>
	
	public sealed class FloatValue:PrimitiveValueBase
	{
		override public PrimitiveValueType Type
		{
			get
			{
				return PrimitiveValueType.FLOAT;
			}
			
		}
		override public Object ValueObject
		{
			get
			{
				return floatValue;
			}
			
		}
		override public float _Float
		{
			set
			{
				this.floatValue = value;
			}
			
		}
		private float? floatValue;
		
		/// <summary> Parse string value returning a float.</summary>
		/// <param name="value">to parse
		/// </param>
		/// <returns> parsed value
		/// </returns>
		public static float parseString(String value)
		{
			return Single.Parse(value);
		}
		
		/// <summary> Parse the string array returning a float array.</summary>
		/// <param name="values">- string array
		/// </param>
		/// <returns> typed array
		/// </returns>
		public static float[] parseString(String[] values)
		{
			float[] result = new float[values.Length];
			for (int i = 0; i < result.Length; i++)
			{
				result[i] = parseString(values[i]);
			}
			return result;
		}
		
		public override void  parse(String value)
		{
			floatValue = parseString(value);
		}
		
		public override String ToString()
		{
			if (floatValue == null)
			{
				return "null";
			}

			return floatValue.ToString();
		}
	}
}
