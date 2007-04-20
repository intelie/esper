using System;

namespace net.esper.support.bean
{
	public class SupportBean
	{
		virtual public String Str
		{
            get { return m_stringValue; }
            set { this.m_stringValue = value; }
		}

        virtual public String str
        {
            get { return m_stringValue; }
            set { this.m_stringValue = value; }
        }

		#region "Primitives"

		virtual public bool BoolPrimitive
		{
            get { return m_boolPrimitive; }
            set { this.m_boolPrimitive = value; }
		}

		virtual public int IntPrimitive
		{
            get { return m_intPrimitive; }
            set { this.m_intPrimitive = value; }
		}

		virtual public long LongPrimitive
		{
            get { return m_longPrimitive; }
            set { this.m_longPrimitive = value; }
		}

		virtual public char CharPrimitive
		{
            get { return m_charPrimitive; }
            set { this.m_charPrimitive = value; }
		}

		virtual public short ShortPrimitive
		{
            get { return m_shortPrimitive; }
            set { this.m_shortPrimitive = value; }
		}

		virtual public sbyte BytePrimitive
		{
            get { return m_bytePrimitive; }
            set { this.m_bytePrimitive = value; }
		}

		virtual public float FloatPrimitive
		{
            get { return m_floatPrimitive; }
            set { this.m_floatPrimitive = value; }
		}

		virtual public double DoublePrimitive
		{
            get { return m_doublePrimitive; }
            set { this.m_doublePrimitive = value; }
		}

		#endregion

        #region "Primitives (LC)"

        virtual public bool boolPrimitive
        {
            get { return m_boolPrimitive; }
            set { this.m_boolPrimitive = value; }
        }

        virtual public int intPrimitive
        {
            get { return m_intPrimitive; }
            set { this.m_intPrimitive = value; }
        }

        virtual public long longPrimitive
        {
            get { return m_longPrimitive; }
            set { this.m_longPrimitive = value; }
        }

        virtual public char charPrimitive
        {
            get { return m_charPrimitive; }
            set { this.m_charPrimitive = value; }
        }

        virtual public short shortPrimitive
        {
            get { return m_shortPrimitive; }
            set { this.m_shortPrimitive = value; }
        }

        virtual public sbyte bytePrimitive
        {
            get { return m_bytePrimitive; }
            set { this.m_bytePrimitive = value; }
        }

        virtual public float floatPrimitive
        {
            get { return m_floatPrimitive; }
            set { this.m_floatPrimitive = value; }
        }

        virtual public double doublePrimitive
        {
            get { return m_doublePrimitive; }
            set { this.m_doublePrimitive = value; }
        }

        #endregion

		#region "Boxed"

		virtual public bool? BoolBoxed
		{
            get { return m_boolBoxed; }
            set { this.m_boolBoxed = value; }
		}

		virtual public int? IntBoxed
		{
            get { return m_intBoxed; }
            set { this.m_intBoxed = value; }
		}

		virtual public long? LongBoxed
		{
            get { return m_longBoxed; }
            set { this.m_longBoxed = value; }
		}

		virtual public char? CharBoxed
		{
            get { return m_charBoxed; }
            set { this.m_charBoxed = value; }
		}

		virtual public short? ShortBoxed
		{
            get { return m_shortBoxed; }
            set { this.m_shortBoxed = value; }
		}

		virtual public sbyte? ByteBoxed
		{
            get { return m_byteBoxed; }
            set { this.m_byteBoxed = value; }
		}

		virtual public float? FloatBoxed
		{
            get { return m_floatBoxed; }
            set { this.m_floatBoxed = value; }
		}

		virtual public double? DoubleBoxed
		{
            get { return m_doubleBoxed; }
            set { this.m_doubleBoxed = value; }
		}

		#endregion

        #region "Boxed (LC)"

        virtual public bool? boolBoxed
        {
            get { return m_boolBoxed; }
            set { this.m_boolBoxed = value; }
        }

        virtual public int? intBoxed
        {
            get { return m_intBoxed; }
            set { this.m_intBoxed = value; }
        }

        virtual public long? longBoxed
        {
            get { return m_longBoxed; }
            set { this.m_longBoxed = value; }
        }

        virtual public char? charBoxed
        {
            get { return m_charBoxed; }
            set { this.m_charBoxed = value; }
        }

        virtual public short? shortBoxed
        {
            get { return m_shortBoxed; }
            set { this.m_shortBoxed = value; }
        }

        virtual public sbyte? byteBoxed
        {
            get { return m_byteBoxed; }
            set { this.m_byteBoxed = value; }
        }

        virtual public float? floatBoxed
        {
            get { return m_floatBoxed; }
            set { this.m_floatBoxed = value; }
        }

        virtual public double? doubleBoxed
        {
            get { return m_doubleBoxed; }
            set { this.m_doubleBoxed = value; }
        }

        #endregion

		virtual public SupportEnum EnumValue
		{
            get { return m_enumValue; }
            set { this.m_enumValue = value; }
		}

        virtual public SupportEnum enumValue
        {
            get { return m_enumValue; }
        }

        private String m_stringValue;

        private bool m_boolPrimitive;
        private int m_intPrimitive;
        private long m_longPrimitive;
        private char m_charPrimitive;
        private short m_shortPrimitive;
        private sbyte m_bytePrimitive;
        private float m_floatPrimitive;
        private double m_doublePrimitive;

        private bool? m_boolBoxed;
        private int? m_intBoxed;
        private long? m_longBoxed;
        private char? m_charBoxed;
        private short? m_shortBoxed;
        private sbyte? m_byteBoxed;
        private float? m_floatBoxed;
		private double? m_doubleBoxed;

        private SupportEnum m_enumValue;

        virtual public string getString()
        {
            return m_stringValue;
        }

        #region "Primitive Accessors"

        virtual public bool getBoolPrimitive()
        {
            return m_boolPrimitive;
        }

        virtual public int getIntPrimitive()
        {
            return m_intPrimitive;
        }

        virtual public long getLongPrimitive()
        {
            return m_longPrimitive;
        }

        virtual public char getCharPrimitive()
        {
            return m_charPrimitive;
        }

        virtual public short getShortPrimitive()
        {
            return m_shortPrimitive;
        }

        virtual public sbyte getBytePrimitive()
        {
            return m_bytePrimitive;
        }

        virtual public float getFloatPrimitive()
        {
            return m_floatPrimitive;
        }

        virtual public double getDoublePrimitive()
        {
            return m_doublePrimitive;
        }

        #endregion

        #region "Boxed Accessors"

        virtual public bool? getBoolBoxed()
        {
            return m_boolBoxed;
        }

        virtual public int? getIntBoxed()
        {
            return m_intBoxed;
        }

        virtual public long? getLongBoxed()
        {
            return m_longBoxed;
        }

        virtual public char? getCharBoxed()
        {
            return m_charBoxed;
        }

        virtual public short? getShortBoxed()
        {
            return m_shortBoxed;
        }

        virtual public sbyte? getByteBoxed()
        {
            return m_byteBoxed;
        }

        virtual public float? getFloatBoxed()
        {
            return m_floatBoxed;
        }

        virtual public double? getDoubleBoxed()
        {
            return m_doubleBoxed;
        }

        #endregion

        public SupportBean()
		{
		}
		
		public SupportBean(String stringValue, int intPrimitive)
		{
            this.m_stringValue = stringValue;
            this.m_intPrimitive = intPrimitive;
		}
	}
}