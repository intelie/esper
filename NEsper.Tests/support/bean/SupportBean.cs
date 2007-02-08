using System;

namespace net.esper.support.bean
{
	public class SupportBean
	{
		virtual public String StringValue
		{
			get { return stringValue; }
			set { this.stringValue = value; }
		}

		#region "Primitives"

		virtual public bool BoolPrimitive
		{
			get { return boolPrimitive; }
			set { this.boolPrimitive = value; }
		}

		virtual public int IntPrimitive
		{
			get { return intPrimitive; }
			set { this.intPrimitive = value; }
		}

		virtual public long LongPrimitive
		{
			get { return longPrimitive; }
			set { this.longPrimitive = value; }
		}

		virtual public char CharPrimitive
		{
			get { return charPrimitive; }
			set { this.charPrimitive = value; }
		}

		virtual public short ShortPrimitive
		{
			get { return shortPrimitive; }
			set { this.shortPrimitive = value; }
		}

		virtual public sbyte BytePrimitive
		{
			get { return bytePrimitive; }
			set { this.bytePrimitive = value; }
		}

		virtual public float FloatPrimitive
		{
			get { return floatPrimitive; }
			set { this.floatPrimitive = value; }
		}

		virtual public double DoublePrimitive
		{
			get { return doublePrimitive; }
			set { this.doublePrimitive = value; }
		}

		#endregion

		#region "Boxed"

		virtual public bool? BoolBoxed
		{
			get { return boolBoxed; }
			set { this.boolBoxed = value; }
		}

		virtual public int? IntBoxed
		{
			get { return intBoxed; }
			set { this.intBoxed = value; }
		}

		virtual public long? LongBoxed
		{
			get { return longBoxed; }
			set { this.longBoxed = value; }
		}

		virtual public char? CharBoxed
		{
			get { return charBoxed; }
			set { this.charBoxed = value; }
		}

		virtual public short? ShortBoxed
		{
			get { return shortBoxed; }
			set { this.shortBoxed = value; }
		}

		virtual public sbyte? ByteBoxed
		{
			get { return byteBoxed; }
			set { this.byteBoxed = value; }
		}

		virtual public float? FloatBoxed
		{
			get { return floatBoxed; }
			set { this.floatBoxed = value; }
		}

		virtual public double? DoubleBoxed
		{
			get { return doubleBoxed; }
			set { this.doubleBoxed = value; }
		}

		#endregion

		virtual public SupportEnum EnumValue
		{
			get { return enumValue; }
			set { this.enumValue = value; }
		}

		private String stringValue;
		
		private bool boolPrimitive;
		private int intPrimitive;
		private long longPrimitive;
		private char charPrimitive;
		private short shortPrimitive;
		private sbyte bytePrimitive;
		private float floatPrimitive;
		private double doublePrimitive;

		private bool? boolBoxed;
		private int? intBoxed;
		private long? longBoxed;
		private char? charBoxed;
		private short? shortBoxed;
		private sbyte? byteBoxed;
		private float? floatBoxed;
		private double? doubleBoxed;

		private SupportEnum enumValue;
		
		public SupportBean()
		{
		}
		
		public SupportBean(String stringValue, int intPrimitive)
		{
			this.stringValue = stringValue;
			this.IntPrimitive = intPrimitive;
		}
	}
}
