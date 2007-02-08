using System;
namespace net.esper.type
{
	/// <summary>
	/// Placeholder for a byte value in an event expression.
	/// </summary>
	
	public sealed class ByteValue:PrimitiveValueBase
	{
		override public PrimitiveValueType Type
		{
			get
			{
				return PrimitiveValueType.BYTE;
			}
			
		}
		override public Object ValueObject
		{
			get
			{
				return byteValue;
			}
			
		}
		override public sbyte _Byte
		{
			set
			{
				this.byteValue = value;
			}
			
		}
		private SByte? byteValue;
		
		public override void  parse(String value)
		{
			byteValue = (sbyte) SByte.Parse(value);
		}
		
		public override String ToString()
		{
			if (byteValue == null)
			{
				return "null";
			}
			return byteValue.ToString();
		}
	}
}
