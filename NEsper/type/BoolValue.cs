using System;

namespace net.esper.type
{
    /// <summary>
    /// Placeholder for a boolean value in an event expression.
    /// </summary>

    public sealed class BoolValue : PrimitiveValueBase
    {
        override public PrimitiveValueType Type
        {
            get
            {
                return PrimitiveValueType.BOOL;
            }
        }

        override public Object ValueObject
        {
            get
            {
                return boolValue;
            }

        }

        override public bool _Boolean
        {
            set { this.boolValue = value; }
        }

        private bool? boolValue;

        /// <summary> Constructor.</summary>
        /// <param name="boolValue">sets the value.
        /// </param>
        public BoolValue(Boolean boolValue)
        {
            this.boolValue = boolValue;
        }

        /// <summary> Constructor.</summary>
        public BoolValue()
        {
        }

        /// <summary> Parse the boolean string.</summary>
        /// <param name="value">is a bool value
        /// </param>
        /// <returns> parsed boolean
        /// </returns>
        public static bool ParseString(String value)
        {
            bool rvalue;
            if (!Boolean.TryParse(value, out rvalue))
            {
                throw new ArgumentException("Boolean value '" + value + "' cannot be converted to boolean");
            }

            return rvalue;
        }

        /// <summary> Parse the string array returning a boolean array.</summary>
        /// <param name="values">- string array
        /// </param>
        /// <returns> typed array
        /// </returns>
        public static bool[] ParseString(String[] values)
        {
            bool[] result = new bool[values.Length];
            for (int i = 0; i < result.Length; i++)
            {
                result[i] = ParseString(values[i]);
            }
            return result;
        }

        public override void Parse(String value)
        {
            boolValue = ParseString(value);
        }

        public override String ToString()
        {
            if (boolValue == null)
            {
                return "null";
            }

            return boolValue.ToString();
        }
    }
}
