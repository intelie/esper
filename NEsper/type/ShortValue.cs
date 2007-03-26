using System;

namespace net.esper.type
{
    /// <summary>
    ///  Placeholder for a short-typed value in an event expression.
    /// </summary>

    public sealed class ShortValue : PrimitiveValueBase
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
                return PrimitiveValueType.SHORT;
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
                return shortValue;
            }
        }

        /// <summary>
        /// Set a short value.
        /// </summary>
        /// <value></value>
        override public short _Short
        {
            set
            {
                this.shortValue = value;
            }
        }

        private Nullable<Int16> shortValue;

        /// <summary>
        /// Parse the string literal value into the specific data type.
        /// </summary>
        /// <param name="value">is the textual value to parse</param>
        public override void Parse(String value)
        {
            shortValue = short.Parse(value);
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
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
