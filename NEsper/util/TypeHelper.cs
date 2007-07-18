using System;
using System.Collections.Generic;
using System.Reflection;

namespace net.esper.util
{
    /// <summary> Helper for questions about types.
    /// <para> what is the boxed type for a primitive type</para>
    /// <para> is this a numeric type.</para>
    /// </summary>
    public class TypeHelper
    {
        /// <summary>
        /// Returns the boxed class for the given class, or the class itself if already boxed or not a primitive type.
        /// For primitive unboxed types returns the boxed types, e.g. returns typeof(int?) for passing typeof(int).
        /// For any other class, returns the class passed.
        /// </summary>
        /// <param name="type">is the type to return the boxed type for</param>

        public static Type GetBoxedType(Type type)
        {
            if (type == typeof(bool))
            {
                return typeof(bool?);
            }
            if (type == typeof(char))
            {
                return typeof(char?);
            }
            if (type == typeof(double))
            {
                return typeof(double?);
            }
            if (type == typeof(float))
            {
                return typeof(float?);
            }
            if (type == typeof(sbyte))
            {
                return typeof(sbyte?);
            }
            if (type == typeof(byte))
            {
                return typeof(byte?);
            }
            if (type == typeof(short))
            {
                return typeof(short?);
            }
            if (type == typeof(ushort))
            {
                return typeof(ushort?);
            }
            if (type == typeof(int))
            {
                return typeof(int?);
            }
            if (type == typeof(uint))
            {
                return typeof(uint?);
            }
            if (type == typeof(long))
            {
                return typeof(long?);
            }
            if (type == typeof(ulong))
            {
                return typeof(ulong?);
            }
            return type;
        }

	    /// <summary>
	    /// Returns the un-boxed class for the given class, or the class itself if already un-boxed or not a primitive type.
	    /// For primitive boxed types returns the unboxed primitive type, e.g. returns typeof(int) for passing typeof(Integer).
	    /// For any other class, returns the class passed.
	    /// </summary>
	    /// <param name="type">
	    /// is the class to return the unboxed (or primitive) class for
	    /// </param>
	    /// <returns>primitive variant of the same class</returns>
	    public static Type GetPrimitiveType(Type type)
	    {
	        if (type == typeof(bool?))
	        {
	            return typeof(bool);
	        }
	        if (type == typeof(char?))
	        {
	            return typeof(char);
	        }
	        if (type == typeof(double?))
	        {
	            return typeof(double);
	        }
	        if (type == typeof(float?))
	        {
	            return typeof(float);
	        }
	        if (type == typeof(sbyte?))
	        {
	            return typeof(sbyte);
	        }
	        if (type == typeof(byte?))
	        {
	            return typeof(byte);
	        }
	        if (type == typeof(short?))
	        {
	            return typeof(short);
	        }
	        if (type == typeof(ushort?))
	        {
	            return typeof(ushort);
	        }
	        if (type == typeof(int?))
	        {
	            return typeof(int);
	        }
	        if (type == typeof(uint?))
	        {
	            return typeof(uint);
	        }
	        if (type == typeof(long?))
	        {
	            return typeof(long);
	        }
	        if (type == typeof(ulong?))
	        {
	            return typeof(ulong);
	        }
	        return type;
	    }

        /// <summary>
        /// Returns for the class name given the class name of the boxed (wrapped) type if
        /// the class name is one of the CLR primitive types.
        /// </summary>
        /// <param name="typeName">a type name, a CLR primitive type or other class</param>
        /// <returns>boxed type name if CLR primitive type, or just same class name passed in if not a primitive type</returns>

        public static String GetBoxedTypeName(String typeName)
        {
            if (typeName == typeof(char).FullName)
            {
                return typeof(char?).FullName;
            }
            if (typeName == typeof(sbyte).FullName)
            {
                return typeof(sbyte?).FullName;
            }
            if (typeName == typeof(byte).FullName)
            {
                return typeof(byte?).FullName;
            }
            if ((typeName == typeof(short).FullName) ||
                (typeName == "short"))
            {
                return typeof(short?).FullName;
            }
            if ((typeName == typeof(ushort).FullName) ||
                (typeName == "ushort"))
            {
                return typeof(ushort?).FullName;
            }
            if ((typeName == typeof(int).FullName) ||
                (typeName == "int"))
            {
                return typeof(int?).FullName;
            }
            if ((typeName == typeof(uint).FullName) ||
                (typeName == "uint"))
            {
                return typeof(uint?).FullName;
            }
            if ((typeName == typeof(long).FullName) ||
                (typeName == "long"))
            {
                return typeof(long?).FullName;
            }
            if ((typeName == typeof(ulong).FullName) ||
                (typeName == "ulong"))
            {
                return typeof(ulong?).FullName;
            }
            if ((typeName == typeof(float).FullName) ||
                (typeName == "float"))
            {
                return typeof(float?).FullName;
            }
            if ((typeName == typeof(double).FullName) ||
                (typeName == "double"))
            {
                return typeof(double?).FullName;
            }
            if ((typeName == typeof(bool).FullName) ||
                (typeName == "boolean") ||
                (typeName == "bool"))
            {
                return typeof(bool?).FullName;
            }
            if (String.Equals(typeName, "string", StringComparison.OrdinalIgnoreCase))
            {
                typeName = typeof(string).FullName;
            }
            return typeName;
        }

        /// <summary>
        /// Determines whether the specified type is bool.
        /// </summary>
        /// <param name="type">The type.</param>
        /// <returns>
        /// 	<c>true</c> if the specified type is bool; otherwise, <c>false</c>.
        /// </returns>
        public static bool IsBoolean(Type type)
        {
            return
                (type == typeof(bool)) ||
                (type == typeof(bool?))
                ;
        }

        /// <summary>
        /// Returns true if the type represents a character type.
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>

        public static bool IsCharacter(Type type)
        {
            return
                (type == typeof(char)) ||
                (type == typeof(char?))
                ;
        }

        /// <summary> Determines if the type passed in is one of the integral numeric types.</summary>
        /// <param name="type">to check</param>
        public static bool IsIntegral(Type type)
        {
            return
                (type == typeof(short?)) ||
                (type == typeof(ushort?)) ||
                (type == typeof(int?)) ||
                (type == typeof(uint?)) ||
                (type == typeof(long?)) ||
                (type == typeof(ulong?)) ||
                (type == typeof(sbyte?)) ||
                (type == typeof(byte?)) ||
                (type == typeof(short)) ||
                (type == typeof(ushort)) ||
                (type == typeof(int)) ||
                (type == typeof(uint)) ||
                (type == typeof(long)) ||
                (type == typeof(ulong)) ||
                (type == typeof(sbyte))
                ;
        }

        /// <summary> Determines if the type passed in is one of the integral numeric types.</summary>
        /// <param name="value">to check</param>
        public static bool IsIntegralNumber(Object value)
        {
        	return
        		(value != null) ?
        		(IsIntegral(value.GetType())):
        		(false);
        }

        /// <summary> Determines if the type passed in is one of the numeric types.</summary>
        /// <param name="type">to check</param>
        /// <returns> true if numeric, false if not
        /// </returns>
        public static bool IsNumeric(Type type)
        {
            return
                (type == typeof(double?)) ||
                (type == typeof(float?)) ||
                (type == typeof(short?)) ||
                (type == typeof(ushort?)) ||
                (type == typeof(int?)) ||
                (type == typeof(uint?)) ||
                (type == typeof(long?)) ||
                (type == typeof(ulong?)) ||
                (type == typeof(sbyte?)) ||
                (type == typeof(byte?)) ||
                (type == typeof(double)) ||
                (type == typeof(float)) ||
                (type == typeof(short)) ||
                (type == typeof(ushort)) ||
                (type == typeof(int)) ||
                (type == typeof(uint)) ||
                (type == typeof(long)) ||
                (type == typeof(ulong)) ||
                (type == typeof(sbyte))
                ;
        }

        /// <summary> Determines if the value passed in is one of the numeric types.</summary>
        /// <param name="value">to check</param>
        /// <returns> true if numeric, false if not</returns>

        public static bool IsNumericValue(Object value)
        {
        	return
        		(value != null) ?
        		(IsNumeric(value.GetType())):
        		(false);
        }

        /// <summary>
        /// Coerce the given number to the given type. Allows coerce to lower resultion number.
        /// Doesn't coerce to primitive types.
        /// <param name="numToCoerce">numToCoerce is the number to coerce to the given type</param>
        /// <param name="resultBoxedType">the result type to return</param>
		/// <returns>the numToCoerce as a value in the given result type</returns>
        /// </summary>

        public static Object CoerceBoxed(Object numToCoerce, Type resultBoxedType)
        {
            if (GetBoxedType(numToCoerce.GetType()) == resultBoxedType)
            {
                return numToCoerce;
            }

            if (resultBoxedType == typeof(double?))
            {
                double? value = Convert.ToDouble(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(ulong?))
            {
                ulong? value = Convert.ToUInt64(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(long?))
            {
                long? value = Convert.ToInt64(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(float?))
            {
                float? value = Convert.ToSingle(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(uint?))
            {
                uint? value = Convert.ToUInt32(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(int?))
            {
                int? value = Convert.ToInt32(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(ushort?))
            {
                ushort? value = Convert.ToUInt16(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(short?))
            {
                short? value = Convert.ToInt16(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(byte?))
            {
                byte? value = Convert.ToByte(numToCoerce);
                return value;
            }
            if (resultBoxedType == typeof(sbyte?))
            {
                sbyte? value = Convert.ToSByte(numToCoerce);
                return value;
            }

            throw new ArgumentException("Cannot coerce to number subtype " + resultBoxedType.FullName);
        }

        /// <summary>
        /// Returns the coercion type for the 2 numeric types for use in arithmatic.
        /// Note: byte and short types always result in integer.
        /// </summary>
        /// <param name="typeOne">The first type.</param>
        /// <param name="typeTwo">The second type.</param>
        /// <returns>coerced type</returns>
        /// <throws>  CoercionException if types don't allow coercion </throws>

        public static Type GetArithmaticCoercionType(Type typeOne, Type typeTwo)
        {
            Type boxedOne = GetBoxedType(typeOne);
            Type boxedTwo = GetBoxedType(typeTwo);

            if (!IsNumeric(boxedOne) ||
                !IsNumeric(boxedTwo))
            {
                throw new CoercionException("Cannot coerce types " + typeOne.FullName + " and " + typeTwo.FullName);
            }

            if ((boxedOne == typeof(double?)) ||
                (boxedTwo == typeof(double?)))
            {
                return typeof(double?);
            }
            if ((boxedOne == typeof(float?)) ||
                (boxedTwo == typeof(float?)))
            {
                return typeof(float?);
            }
            if ((boxedOne == typeof(ulong?)) ||
                (boxedTwo == typeof(ulong?)))
            {
                return typeof(ulong?);
            }
            if ((boxedOne == typeof(long?)) ||
                (boxedTwo == typeof(long?)))
            {
                return typeof(long?);
            }
            if ((boxedOne == typeof(uint?)) ||
                (boxedTwo == typeof(uint?)))
            {
                return typeof(uint?);
            }
            return typeof(int?);
        }


        /// <summary>
        /// Returns true if the Number instance is a floating point number.
        /// </summary>
        /// <param name="number">to check</param>
        /// <returns>true if number is Float or double type</returns>

        public static bool IsFloatingPointNumber(Object number)
        {
            return
                (number is float) ||
                (number is double);
        }

        /// <summary>
        /// Returns true if the supplied type is a floating point number.
        /// </summary>
        /// <param name="type">to check</param>
        /// <returns>
        /// true if primitive or boxed float or double
        /// </returns>
        public static bool IsFloatingPointClass(Type type)
        {
            return
                (type == typeof(float?)) ||
                (type == typeof(float)) ||
                (type == typeof(double?)) ||
                (type == typeof(double))
            ;
        }

        /// <summary>
        /// Returns for 2 classes to be compared via relational operator the Class type of
        /// common comparison. The output is always typeof(long?), typeof(double), typeof(String) or typeof(bool)
        /// depending on whether the passed types are numeric and floating-point.
        /// Accepts primitive as well as boxed types.
        /// </summary>
        /// <param name="typeOne">The first type.</param>
        /// <param name="typeTwo">The second type.</param>
        /// <returns>
        /// One of typeof(long?), typeof(double) or typeof(String)
        /// </returns>
        /// <throws>  ArgumentException if the types cannot be compared </throws>

        public static Type GetCompareToCoercionType(Type typeOne, Type typeTwo)
        {
            if ((typeOne == typeof(String)) &&
                (typeTwo == typeof(String)))
            {
                return typeof(String);
            }

            if (IsBoolean(typeOne) &&
                IsBoolean(typeTwo))
            {
                return typeof(bool?);
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
                return typeof(double?);
            }

            return typeof(long?);
        }

		/// <summary>
		/// Determines if a number can be coerced upwards to another number class without loss.
		/// <para>
		/// Clients must pass in two classes that are numeric types.
        /// </para>
		/// <para>
		/// Any number class can be coerced to double, while only double cannot be coerced to float.
		/// Any non-floating point number can be coerced to long.
		/// Integer can be coerced to Byte and Short even though loss is possible, for convenience.
        /// </para>
		/// </summary>
		/// <param name="numberClassToBeCoerced">the number class to be coerced</param>
		/// <param name="numberClassToCoerceTo">the number class to coerce to</param>
		/// <returns>true if numbers can be coerced without loss, false if not</returns>
	    public static bool CanCoerce(Type numberClassToBeCoerced, Type numberClassToCoerceTo)
	    {
	        Type boxedFrom = GetBoxedType(numberClassToBeCoerced);
	        Type boxedTo = GetBoxedType(numberClassToCoerceTo);

	        if (!IsNumeric(numberClassToBeCoerced))
	        {
	            throw new ArgumentException("Type '" + numberClassToBeCoerced + "' is not a numeric type'");
	        }

	        if (boxedTo == typeof(float?))
	        {
	            return ((boxedFrom == typeof(byte?)) ||
						(boxedFrom == typeof(sbyte?)) ||
	                    (boxedFrom == typeof(short?)) ||
	                    (boxedFrom == typeof(ushort?)) ||
	                    (boxedFrom == typeof(int?)) ||
	                    (boxedFrom == typeof(uint?)) ||
	                    (boxedFrom == typeof(long?)) ||
	                    (boxedFrom == typeof(ulong?)) ||
	                    (boxedFrom == typeof(float?)));
	        }
	        else if (boxedTo == typeof(double?))
	        {
	            return ((boxedFrom == typeof(byte?)) ||
	                    (boxedFrom == typeof(sbyte?)) ||
	                    (boxedFrom == typeof(short?)) ||
	                    (boxedFrom == typeof(ushort?)) ||
	                    (boxedFrom == typeof(int?)) ||
	                    (boxedFrom == typeof(uint?)) ||
	                    (boxedFrom == typeof(long?)) ||
	                    (boxedFrom == typeof(ulong?)) ||
	                    (boxedFrom == typeof(float?)) ||
	                    (boxedFrom == typeof(double?)));
	        }
	        else if (boxedTo == typeof(long?))
	        {
	            return ((boxedFrom == typeof(byte?)) ||
	                    (boxedFrom == typeof(sbyte?)) ||
	                    (boxedFrom == typeof(short?)) ||
	                    (boxedFrom == typeof(ushort?)) ||
	                    (boxedFrom == typeof(int?)) ||
	                    (boxedFrom == typeof(uint?)) ||
	                    (boxedFrom == typeof(long?)));
	        }
	        else if ((boxedTo == typeof(int?)) ||
	                 (boxedTo == typeof(short?)) ||
	                 (boxedTo == typeof(ushort?)) ||
	                 (boxedTo == typeof(sbyte?)))
	        {
	            return ((boxedFrom == typeof(byte?)) ||
	                    (boxedFrom == typeof(sbyte?)) ||
	                    (boxedFrom == typeof(short?)) ||
	                    (boxedFrom == typeof(ushort?)) ||
	                    (boxedFrom == typeof(int?)));
	        }
	        else
	        {
	            throw new ArgumentException("Type '" + numberClassToCoerceTo + "' is not a numeric type'");
	        }
	    }
        /// <summary>
        /// Returns true if the class passed in is a built-in data type (primitive or wrapper)
        /// including String.
        /// </summary>
        /// <param name="type">The type.</param>
        /// <returns>
        /// true if built-in data type, or false if not
        /// </returns>
        public static bool IsBuiltinDataType(Type type)
        {
            Type typeBoxed = GetBoxedType(type);

            if (IsNumeric(typeBoxed) ||
                IsBoolean(typeBoxed) ||
                IsCharacter(typeBoxed) ||
                (type == typeof(string)))
            {
                return true;
            }

            return false;
        }

        /// <summary>
        /// Returns true if 2 classes are assignment compatible.
        /// </summary>
        /// <param name="parameterType">type to assign from</param>
        /// <param name="parameterization">type to assign to</param>
        /// <returns>
        /// true if assignment compatible, false if not
        /// </returns>

        public static bool IsAssignmentCompatible(Type parameterType, Type parameterization)
        {
            if (parameterType.IsAssignableFrom(parameterization))
            {
                return true;
            }

            if (parameterType.IsValueType)
            {
                Type parameterWrapperType = GetBoxedType(parameterType);
                if (parameterWrapperType != null)
                {
                    return parameterWrapperType.Equals(parameterization);
                }
            }

            return false;
        }

        /// <summary> Determines a common denominator type to which one or more types can be casted or coerced.
        /// For use in determining the result type in certain expressions (coalesce, case).
        /// <para>
        /// Null values are allowed as part of the input and indicate a 'null' constant value
        /// in an expression tree. Such as value doesn't have any type and can be ignored in
        /// determining a result type.
        /// </para>
        /// <para>
        /// For numeric types, determines a coercion type that all types can be converted to
        /// via the method GetArithmaticCoercionType.
        /// </para>
        /// <para>
        /// Indicates that there is no common denominator type by throwing <seealso cref="CoercionException"/>.
        /// </para>
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
                return GetBoxedType(types[0]);
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
                return GetBoxedType(types[0]);
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

            // Convert to boxed types
            for (int i = 0; i < types.Length; i++)
            {
                types[i] = GetBoxedType(types[i]);
            }

            // Check if all bool
            if (types[0] == typeof(bool?))
            {
                for (int i = 0; i < types.Length; i++)
                {
                    if (types[i] != typeof(bool?))
                    {
                        throw new CoercionException("Cannot coerce to bool type " + types[i].FullName);
                    }
                }

                return typeof(bool?);
            }

            // Check if all char
            if (types[0] == typeof(char?))
            {
                for (int i = 0; i < types.Length; i++)
                {
                    if (types[i] != typeof(char?))
                    {
                        throw new CoercionException("Cannot coerce to bool type " + types[i].FullName);
                    }
                }

                return typeof(char?);
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
            for (int ii = 2; ii < types.Length; ii++)
            {
                result = GetArithmaticCoercionType(result, types[ii]);
            }
            return result;
        }

        /// <summary>
        /// Resolves a type using the assembly qualified type name.  If the type
        /// can not be resolved using a simple Type.GetType() [which many can not],
        /// then the method will check all assemblies in the assembly search path.
        /// </summary>
        /// <param name="assemblyQualifiedTypeName">Name of the assembly qualified type.</param>
        /// <param name="assemblySearchPath">The assembly search path.</param>
        /// <returns></returns>

        public static Type ResolveType(String assemblyQualifiedTypeName, IEnumerable<Assembly> assemblySearchPath)
        {
            Exception coreException = null;

            // Attempt to find the type by using the Type object to resolve
            // the type.  If its fully qualified this will work, if its not,
            // then this will likely fail.

            try
            {
                return Type.GetType(assemblyQualifiedTypeName, true, false);
            }
            catch (Exception e)
            {
                coreException = e;
            }

            // Search the assembly path to resolve the type

            foreach (Assembly assembly in assemblySearchPath)
            {
                Type type = assembly.GetType(assemblyQualifiedTypeName, false, false);
                if (type != null)
                {
                    return type;
                }
            }

            // Type was not found in any of our search points

            throw coreException;
        }

        /// <summary>
        /// Resolves a type using the assembly qualified type name.  If the type
        /// can not be resolved using a simple Type.GetType() [which many can not],
        /// then the method will check all assemblies currently loaded into the
        /// AppDomain.
        /// </summary>
        /// <param name="assemblyQualifiedTypeName">Name of the assembly qualified type.</param>
        /// <returns></returns>

        public static Type ResolveType(String assemblyQualifiedTypeName)
        {
            return ResolveType(assemblyQualifiedTypeName, AppDomain.CurrentDomain.GetAssemblies());
        }
    }
}
