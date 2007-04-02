using System;

namespace net.esper.type
{
    /// <summary>
    /// Placeholder for a double value in an event expression.
    /// </summary>

    public class DoubleValue : PrimitiveValueBase
    {
        /// <summary>
        /// Returns the type of primitive value this instance represents.
        /// </summary>
        /// <value></value>
        /// <returns> enum type of primitive
        /// </returns>
        override public PrimitiveValueType Type
        {
            get
            {
                return PrimitiveValueType.DOUBLE;
            }
        }

        /// <summary>
        /// Returns a value object.
        /// </summary>
        /// <value></value>
        /// <returns> value object
        /// </returns>
        override public Object ValueObject
        {
            get
            {
                return doubleValue;
            }

        }

        private double? doubleValue;

        /// <summary> Constructor.</summary>
        public DoubleValue()
        {
        }

        /// <summary> Constructor setting the value.</summary>
        /// <param name="doubleValue">value to set.
        /// </param>
        public DoubleValue(Double doubleValue)
        {
            this.doubleValue = doubleValue;
        }

        /// <summary> Parse string value returning a double.</summary>
        /// <param name="value">to parse
        /// </param>
        /// <returns> parsed value
        /// </returns>
        public static double ParseString(String value)
        {
            // Double strings are terminated with the character 'd' in Java.  This
            // appears to have propogated itself to the grammar which also uses this
            // syntax.  Trim the 'd' so that it can be parsed.

            if (value.EndsWith("d"))
            {
                value = value.Substring(0, value.Length - 1);
            }

            return Double.Parse(value);
        }

        /// <summary>
        /// Parse the string literal value into the specific data type.
        /// </summary>
        /// <param name="value">is the textual value to parse</param>
        public override void Parse(String value)
        {
            doubleValue = ParseString(value);
        }

        /// <summary> Parse the string array returning a double array.</summary>
        /// <param name="values">string array
        /// </param>
        /// <returns> typed array
        /// </returns>
        public static double[] ParseString(String[] values)
        {
            double[] result = new double[values.Length];
            for (int i = 0; i < result.Length; i++)
            {
                result[i] = ParseString(values[i]);
            }
            return result;
        }

        /// <summary> Return the value as an unboxed.</summary>
        /// <returns> value
        /// </returns>

        public double GetDouble()
        {
            if (doubleValue == null)
            {
                throw new SystemException();
            }
            return doubleValue.Value;
        }

        /// <summary>
        /// Set a double value.
        /// </summary>
        /// <value></value>
        public override double _Double
        {
            set { this.doubleValue = value; }
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            if (doubleValue == null)
            {
                return "null";
            }
            return doubleValue.ToString();
        }
    }
}