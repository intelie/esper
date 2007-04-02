using System;

namespace net.esper.type
{
	/// <summary>
    /// Factory class for PrimitiveValue for all fundamental Java types.
    /// </summary>
	
    public sealed class PrimitiveValueFactory
	{
		/// <summary> Create a placeholder instance for the primitive Java type passed in.
		/// Returns null if the type passed in is not a primitive type.
		/// </summary>
		/// <param name="type">is a fundamental Java
		/// </param>
		/// <returns> instance of placeholder representing the type, or null if not a primitive type
		/// </returns>
	
        public static PrimitiveValue Create(Type type)
		{
			if ((type == typeof(bool)) || type == typeof(bool?))
			{
				return new BoolValue();
			}
			if ((type == typeof(sbyte)) || (type == typeof(sbyte?)))
			{
				return new ByteValue();
			}
			if ((type == typeof(double)) || (type == typeof(double?)))
			{
				return new DoubleValue();
			}
			if ((type == typeof(float)) || (type == typeof(float?)))
			{
				return new FloatValue();
			}
			if ((type == typeof(int)) || (type == typeof(int?)))
			{
				return new IntValue();
			}
			if ((type == typeof(long)) || (type == typeof(long?)))
			{
				return new LongValue();
			}
			if ((type == typeof(short)) || (type == typeof(short?)))
			{
				return new ShortValue();
			}
			if (type == typeof(String))
			{
				return new StringValue();
			}
			
			return null;
		}
	}
}