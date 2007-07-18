///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.support.bean
{
    [Serializable]
	public class SupportBean
	{
	    private string _string;

        public string String
        {
            get { return _string; }
        }

        private SupportEnum EnumValue
        {
            get { return enumValue; }
        }

        public bool BoolPrimitive
        {
            get { return boolPrimitive; }
        }

        public int IntPrimitive
        {
            get { return intPrimitive; }
        }

        public long LongPrimitive
        {
            get { return longPrimitive; }
        }

        public char CharPrimitive
        {
            get { return charPrimitive; }
        }

        public short ShortPrimitive
        {
            get { return shortPrimitive; }
        }

        public sbyte BytePrimitive
        {
            get { return bytePrimitive; }
        }

        public float FloatPrimitive
        {
            get { return floatPrimitive; }
        }

        public double DoublePrimitive
        {
            get { return doublePrimitive; }
        }

        public bool? BoolBoxed
        {
            get { return boolBoxed; }
        }

        public int? IntBoxed
        {
            get { return intBoxed; }
        }

        public long? LongBoxed
        {
            get { return longBoxed; }
        }

        public char? CharBoxed
        {
            get { return charBoxed; }
        }

        public short? ShortBoxed
        {
            get { return shortBoxed; }
        }

        public sbyte? ByteBoxed
        {
            get { return byteBoxed; }
        }

        public float? FloatBoxed
        {
            get { return floatBoxed; }
        }

        public double? DoubleBoxed
        {
            get { return doubleBoxed; }
        }

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

	    public SupportBean(String _string, int intPrimitive)
	    {
	        this._string = _string;
	        this.intPrimitive = intPrimitive;
	    }

	    public void SetString(String _string)
	    {
            this._string = _string;
	    }

	    public void SetBoolPrimitive(bool boolPrimitive)
	    {
	        this.boolPrimitive = boolPrimitive;
	    }

	    public void SetIntPrimitive(int intPrimitive)
	    {
	        this.intPrimitive = intPrimitive;
	    }

	    public void SetLongPrimitive(long longPrimitive)
	    {
	        this.longPrimitive = longPrimitive;
	    }

	    public void SetCharPrimitive(char charPrimitive)
	    {
	        this.charPrimitive = charPrimitive;
	    }

	    public void SetShortPrimitive(short shortPrimitive)
	    {
	        this.shortPrimitive = shortPrimitive;
	    }

	    public void SetBytePrimitive(sbyte bytePrimitive)
	    {
	        this.bytePrimitive = bytePrimitive;
	    }

	    public void SetFloatPrimitive(float floatPrimitive)
	    {
	        this.floatPrimitive = floatPrimitive;
	    }

	    public void SetDoublePrimitive(double doublePrimitive)
	    {
	        this.doublePrimitive = doublePrimitive;
	    }

	    public void SetBoolBoxed(bool? boolBoxed)
	    {
	        this.boolBoxed = boolBoxed;
	    }

	    public void SetIntBoxed(int? intBoxed)
	    {
	        this.intBoxed = intBoxed;
	    }

	    public void SetLongBoxed(long? longBoxed)
	    {
	        this.longBoxed = longBoxed;
	    }

	    public void SetCharBoxed(char? charBoxed)
	    {
	        this.charBoxed = charBoxed;
	    }

	    public void SetShortBoxed(short? shortBoxed)
	    {
	        this.shortBoxed = shortBoxed;
	    }

	    public void SetByteBoxed(sbyte? byteBoxed)
	    {
	        this.byteBoxed = byteBoxed;
	    }

	    public void SetFloatBoxed(float? floatBoxed)
	    {
	        this.floatBoxed = floatBoxed;
	    }

	    public void SetDoubleBoxed(double? doubleBoxed)
	    {
	        this.doubleBoxed = doubleBoxed;
	    }

	    public SupportEnum GetEnumValue()
	    {
	        return enumValue;
	    }

	    public void SetEnumValue(SupportEnum enumValue)
	    {
	        this.enumValue = enumValue;
	    }
	}
} // End of namespace
