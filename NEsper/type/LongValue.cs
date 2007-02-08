using System;

namespace net.esper.type
{
    /// <summary>
    /// Placeholder for a long-typed value in an event expression.
    /// </summary>

    public sealed class LongValue : PrimitiveValueBase
    {
        override public PrimitiveValueType Type
        {
            get
            {
                return PrimitiveValueType.LONG;
            }
        }

        override public Object ValueObject
        {
            get
            {
                return longValue;
            }
        }

        private long? longValue;

        public override void parse(String value)
        {
            longValue = parseString(value);
        }

        /// <summary> Parse the string containing a long value.</summary>
        /// <param name="value">is the textual long value
        /// </param>
        /// <returns> long value
        /// </returns>
        public static long parseString(String value)
        {
            if ((value.EndsWith("L")) || ((value.EndsWith("l"))))
            {
                value = value.Substring(0, (value.Length - 1) - (0));
            }
            if (value.StartsWith("+"))
            {
                value = value.Substring(1);
            }
            return long.Parse(value);
        }

        /// <summary> Parse the string array returning a long array.</summary>
        /// <param name="values">- string array
        /// </param>
        /// <returns> typed array
        /// </returns>
        public static long[] parseString(String[] values)
        {
            long[] result = new long[values.Length];
            for (int i = 0; i < result.Length; i++)
            {
                result[i] = parseString(values[i]);
            }
            return result;
        }

        public override long _Long
        {
            set { this.longValue = value; }
        }

        /// <summary> Returns the long value.</summary>
        /// <returns> long value
        /// </returns>
        public long getLong()
        {
            if (longValue == null)
            {
                throw new SystemException();
            }
            return longValue.Value;
        }

        public override String ToString()
        {
            if (longValue == null)
            {
                return "null";
            }
            return longValue.ToString();
        }
    }
}