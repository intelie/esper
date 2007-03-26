using System;
namespace net.esper.type
{
    /// <summary>
    /// Classes implementing this interface are responsible for parsing, setting and getting the value of
    /// the different basic Java data types that occur in an event expression.
    /// Placeholders represent all literal values in event expressions and set values in prepared event expressions.
    /// </summary>
    public interface PrimitiveValue
    {
        /// <summary> Returns a value object.</summary>
        /// <returns> value object
        /// </returns>
        Object ValueObject { get; }

        /// <summary> Returns the type of primitive value this instance represents.</summary>
        /// <returns> enum type of primitive
        /// </returns>
        PrimitiveValueType Type { get; }

        /// <summary> Set a boolean value.</summary>
        bool _Boolean { set; }

        /// <summary> Set a byte value.</summary>
        sbyte _Byte { set; }

        /// <summary> Set a float value.</summary>
        float _Float { set; }

        /// <summary> Set an int value.</summary>
        int _Int { set; }

        /// <summary> Set a short value.</summary>
        short _Short { set; }

        /// <summary> Set a string value.</summary>
        String _String { set; }

        /// <summary> Parse the string literal value into the specific data type.</summary>
        /// <param name="value">is the textual value to parse
        /// </param>
        void Parse(String value);

        /// <summary> Parse the string literal values supplied in the array into the specific data type.</summary>
        /// <param name="values">are the textual values to parse</param>
        void Parse(String[] values);

        /// <summary> Set a double value.</summary>
        Double _Double { set; }

        /// <summary> Set a long value.</summary>
        long _Long { set; }
    }
}