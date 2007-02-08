using System;

namespace net.esper.type
{
	/// <summary>
	///  Placeholder for a short-typed value in an event expression.
	/// </summary>
	
	public sealed class ShortValue : PrimitiveValueBase
	{
		override public PrimitiveValueType Type
		{
			get
			{
				return PrimitiveValueType.SHORT;
			}
			
		}
		override public Object ValueObject
		{
			get
			{
				return shortValue;
			}
			
		}
		override public short _Short
		{
			set
			{
				this.shortValue = value;
			}
			
		}
		private Nullable<Int16> shortValue;
		
		public override void  parse(String value)
		{
			shortValue = short.Parse(value);
		}
		
		public override String ToString()
		{
			if (shortValue == null)
			{
				return "null";
			}
			return shortValue.ToString();
		}
	}
}
