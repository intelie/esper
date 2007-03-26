using System;

namespace net.esper.type
{
    /// <summary>
    /// Placeholder for a byte value in an event expression.
    /// </summary>

    public sealed class ByteValue : PrimitiveValueBase
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
                return PrimitiveValueType.BYTE;
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
                return byteValue;
            }
        }

        /// <summary>
        /// Set a byte value.
        /// </summary>
        /// <value></value>
        override public sbyte _Byte
        {
            set
            {
                this.byteValue = value;
            }
        }

        private SByte? byteValue;

        /// <summary>
        /// Parse the string literal value into the specific data type.
        /// </summary>
        /// <param name="value">is the textual value to parse</param>
        public override void Parse(String value)
        {
            byteValue = (sbyte)SByte.Parse(value);
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
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
