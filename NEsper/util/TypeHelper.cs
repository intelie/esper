using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.util
{
    /// <summary> Helper for questions about Java classes such as
    /// <p> what is the boxed type for a primitive type
    /// <p> is this a numeric type.
    /// </summary>
    public class TypeHelper
    {
        public static bool IsBoolean(Type type)
        {
            return
                (type == typeof(bool));
        }

        /// <summary> Determines if the class passed in is one of the numeric classes.</summary>
        /// <param name="type">to check
        /// </param>
        /// <returns> true if numeric, false if not
        /// </returns>
        public static bool IsNumeric(Type type)
        {
            return
                (type == typeof(double)) ||
                (type == typeof(float)) ||
                (type == typeof(short)) ||
                (type == typeof(int)) ||
                (type == typeof(long)) ||
                (type == typeof(sbyte))
                ;
        }


        /// <summary>
        /// Coerce the given number to the given type. Allows coerce to lower resultion number.
        /// Doesn't coerce to primitive types.
        /// <param name="numToCoerce">numToCoerce is the number to coerce to the given type</param>
        /// <param name="resultType">the result type to return</result>
        /// </summary>

        public static ValueType CoerceNumber(ValueType numToCoerce, Type resultType)
        {
            if (numToCoerce.GetType() == resultType)
            {
                return numToCoerce;
            }

            return (ValueType) Convert.ChangeType(numToCoerce, resultType);
        }

        /// <summary>
        /// Returns the coercion type for the 2 numeric types for use in arithmatic.
        /// Note: byte and short types always result in integer.
        /// </summary>
        /// <param name="typeOne">
        /// </param>
        /// <param name="typeTwo">
        /// </param>
        /// <returns> coerced type
        /// </returns>
        /// <throws>  CoercionException if types don't allow coercion </throws>
        
        public static Type GetArithmaticCoercionType(Type typeOne, Type typeTwo)
        {
            Type boxedOne = typeOne;
            Type boxedTwo = typeTwo;

            if (!IsNumeric(boxedOne) || !IsNumeric(boxedTwo))
            {
                throw new CoercionException("Cannot coerce types " + typeOne.FullName + " and " + typeTwo.FullName);
            }

            if ((boxedOne == typeof(double)) || (boxedTwo == typeof(double)))
            {
                return typeof(double);
            }
            if ((boxedOne == typeof(float)) || (boxedTwo == typeof(float)))
            {
                return typeof(float);
            }
            if ((boxedOne == typeof(long)) || (boxedTwo == typeof(long)))
            {
                return typeof(long);
            }
            return typeof(int);
        }


        /// <summary>
        /// Returns true if the Number instance is a floating point number.</summary>
        /// <param name="number">to check
        /// </param>
        /// <returns> true if number is Float or double type 
        /// </returns>
        
        public static bool IsFloatingPointNumber(ValueType number)
        {
            return ((number is float) || (number is double)) ;
        }

        /// <summary> Returns true if the supplied type is a floating point number.</summary>
        /// <param name="clazz">to check
        /// </param>
        /// <returns> true if primitive or boxed float or double
        /// </returns>
        public static bool IsFloatingPointClass(Type clazz)
        {
            return ((clazz == typeof(float)) || (clazz == typeof(double)));
        }

        /// <summary> Returns for 2 classes to be compared via relational operator the Class type of
        /// common comparison. The output is always Long.class, double.class, String.class or bool.class
        /// depending on whether the passed types are numeric and floating-point.
        /// Accepts primitive as well as boxed types.
        /// </summary>
        /// <param name="typeOne">
        /// </param>
        /// <param name="typeTwo">
        /// </param>
        /// <returns> One of Long.class, double.class or String.class
        /// </returns>
        /// <throws>  ArgumentException if the types cannot be compared </throws>
        
        public static Type GetCompareToCoercionType(Type typeOne, Type typeTwo)
        {
            if ((typeOne == typeof(String)) &&
                (typeTwo == typeof(String)))
            {
                return typeof(String);
            }

            if ((typeOne == typeof(bool)) &&
                (typeTwo == typeof(bool)))
            {
                return typeof(bool);
            }

            if (!IsNumeric(typeOne) ||
                !IsNumeric(typeTwo))
            {
                throw new ArgumentException(
                    "Types cannot be compared: " + typeOne.FullName + " and " + typeTwo.FullName);
            }

            if (IsFloatingPointClass(typeOne) ||
                IsFloatingPointClass(typeTwo))
            {
                return typeof(double);
            }

            return typeof(long);
        }

        /// <summary>
        /// Returns true if the class passed in is a built-in data type (primitive or wrapper)
        /// including String.
        /// </summary>
        /// <param name="clazz">to check</param>
        /// <returns> true if built-in data type, or false if not
        /// </returns>
        public static bool IsBuiltinDataType(Type type)
        {
            if (IsNumeric(type) ||
        	    (type == typeof(bool)) ||
        	    (type == typeof(string)) ||
        	    (type == typeof(char)))
            {
                return true;
            }

            return false;
        }

        // null values are allowed and represent and unknown type

        /// <summary> Determines a common denominator type to which one or more types can be casted or coerced.
        /// For use in determining the result type in certain expressions (coalesce, case).
        /// <p>
        /// Null values are allowed as part of the input and indicate a 'null' constant value
        /// in an expression tree. Such as value doesn't have any type and can be ignored in
        /// determining a result type.
        /// <p>
        /// For numeric types, determines a coercion type that all types can be converted to
        /// via the method GetArithmaticCoercionType.
        /// <p>
        /// Indicates that there is no common denominator type by throwing {@link CoercionException}.
        /// </summary>
        /// <param name="types">is an array of one or more types, which can be Java built-in (primitive or wrapper)
        /// or user types
        /// </param>
        /// <returns> common denominator type if any can be found, for use in comparison
        /// </returns>
        /// <throws>  CoercionException </throws>
        
        public static Type GetCommonCoercionType(Type[] types)
        {
            if (types.Length < 1)
            {
                throw new ArgumentException("Unexpected zero length array");
            }

            if (types.Length == 1)
            {
                return types[0];
            }

            // Reduce to non-null types
            List<Type> nonNullTypes = new List<Type>();
            for (int i = 0; i < types.Length; i++)
            {
                if (types[i] != null)
                {
                    nonNullTypes.Add(types[i]);
                }
            }

            types = nonNullTypes.ToArray();
            if (types.Length == 0)
            {
                return null; // only null types, result is null
            }
            
            if (types.Length == 1)
            {
                return types[0];
            }

            // Check if all String
            if (types[0] == typeof(String))
            {
                for (int i = 0; i < types.Length; i++)
                {
                    if (types[i] != typeof(String))
                    {
                        throw new CoercionException("Cannot coerce to String type " + types[i].FullName);
                    }
                }
                
                return typeof(String);
            }

            // Check if all bool
            if (types[0] == typeof(bool))
            {
                for (int i = 0; i < types.Length; i++)
                {
                    if (types[i] != typeof(bool))
                    {
                        throw new CoercionException("Cannot coerce to bool type " + types[i].FullName);
                    }
                }
                
                return typeof(bool);
            }

            // Check if all char
            if (types[0] == typeof(char))
            {
                for (int i = 0; i < types.Length; i++)
                {
                    if (types[i] != typeof(char))
                    {
                        throw new CoercionException("Cannot coerce to bool type " + types[i].FullName);
                    }
                }
                
                return typeof(char);
            }

            // Check if all the same builtin type
            if (!IsBuiltinDataType(types[0]))
            {
                for (int i = 0; i < types.Length; i++)
                {
                    if (types[i] != types[0])
                    {
                        throw new CoercionException("Cannot coerce to type " + types[0].FullName);
                    }
                }
                return types[0];
            }

            // Test for numeric
            if (!IsNumeric(types[0]))
            {
                throw new CoercionException("Cannot coerce to numeric type " + types[0].FullName);
            }

            // Use arithmatic coercion type as the final authority, considering all types
            Type result = GetArithmaticCoercionType(types[0], types[1]);
            for( int ii = 2 ; ii < types.Length ; ii++ )
            {
                result = GetArithmaticCoercionType(result, types[ii]);
            }
            return result;
        }
    }
}
