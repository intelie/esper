using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;

namespace net.esper.type
{
    /// <summary>
    /// Enumeration for the type of arithmatic to use.
    /// </summary>

    public class MathArithTypeEnum
    {
        /// <summary>
        /// Plus.
        /// </summary>
        public static readonly MathArithTypeEnum ADD = new MathArithTypeEnum("+");

        /// <summary>
        /// Minus
        /// </summary>

        public static readonly MathArithTypeEnum SUBTRACT = new MathArithTypeEnum("-");

        /// <summary>
        /// Divide
        /// </summary>

        public static readonly MathArithTypeEnum DIVIDE = new MathArithTypeEnum("/");

        /// <summary>
        /// Multiply.
        /// </summary>

        public static readonly MathArithTypeEnum MULTIPLY = new MathArithTypeEnum("*");

        /// <summary>
        /// Modulo.
        /// </summary>

        public static readonly MathArithTypeEnum MODULO = new MathArithTypeEnum("%");

        /// <summary>
        /// Public enumeration of pseudo-enumerated type values.
        /// </summary>

        public static readonly MathArithTypeEnum[] Values = {
            ADD,
            SUBTRACT,
            DIVIDE,
            MULTIPLY,
            MODULO
        };


        private static EDictionary<MultiKey<Object>, Computer> computers;

        static MathArithTypeEnum()
        {
            computers = new EHashDictionary<MultiKey<Object>, Computer>();
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), ADD }), AddDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(float?), ADD }), AddSingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), ADD }), AddInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(int?), ADD }), AddInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(ulong?), ADD }), AddUInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(uint?), ADD }), AddUInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), SUBTRACT }), SubtractDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(float?), SUBTRACT }), SubtractSingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), SUBTRACT }), SubtractInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(int?), SUBTRACT }), SubtractInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(ulong?), SUBTRACT }), SubtractUInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(uint?), SUBTRACT }), SubtractUInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), DIVIDE }), DivideDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(float?), DIVIDE }), DivideSingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), DIVIDE }), DivideInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(int?), DIVIDE }), DivideInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(ulong?), DIVIDE }), DivideUInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(uint?), DIVIDE }), DivideUInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), MULTIPLY }), MultiplyDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(float?), MULTIPLY }), MultiplySingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), MULTIPLY }), MultiplyInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(int?), MULTIPLY }), MultiplyInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(ulong?), MULTIPLY }), MultiplyUInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(uint?), MULTIPLY }), MultiplyUInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), MODULO }), ModuloDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(float?), MODULO }), ModuloSingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), MODULO }), ModuloInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(int?), MODULO }), ModuloInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(ulong?), MODULO }), ModuloUInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(uint?), MODULO }), ModuloUInt32);
        }

        /**
         * Interface for number cruncher.
         */
        public delegate Object Computer(Object d1, Object d2);

        private String expressionText;

        private MathArithTypeEnum(String expressionText)
        {
            this.expressionText = expressionText;
        }

        /// <summary>
        /// Returns number cruncher for the target coercion type.
        /// </summary>
        /// <param name="coercedType">target type</param>
        /// <returns>number cruncher</returns>

        public Computer getComputer(Type coercedType)
        {
            if ((coercedType != typeof(double?)) &&
                (coercedType != typeof(float?)) &&
                (coercedType != typeof(long?)) &&
                (coercedType != typeof(int?)))
            {
                throw new ArgumentException("Expected base numeric type for computation result but got type " + coercedType);
            }
        	
            MultiKey<Object> key = new MultiKey<Object>(new Object[] { coercedType, this });
            Computer computer = computers.Fetch(key);

            if (computer == null)
            {
                throw new ArgumentException("Could not determine process or type " + this + " type " + coercedType);
            }
            return computer;
        }

        /**
         * Computer for type-specific arith. operations.
         */
        public static Object AddDouble(Object d1, Object d2)
        {
            double? result = Convert.ToDouble(d1) + Convert.ToDouble(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object AddSingle(Object d1, Object d2)
        {
            float? result = Convert.ToSingle(d1) + Convert.ToSingle(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object AddInt64(Object d1, Object d2)
        {
            long? result = Convert.ToInt64(d1) + Convert.ToInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object AddInt32(Object d1, Object d2)
        {
            int? result = Convert.ToInt32(d1) + Convert.ToInt32(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
        */
        public static Object AddUInt64(Object d1, Object d2)
        {
            ulong? result = Convert.ToUInt64(d1) + Convert.ToUInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object AddUInt32(Object d1, Object d2)
        {
            uint? result = Convert.ToUInt32(d1) + Convert.ToUInt32(d2);
            return result;
        }


        /**
         * Computer for type-specific arith. operations.
         */
        public static Object SubtractDouble(Object d1, Object d2)
        {
            double? result = Convert.ToDouble(d1) - Convert.ToDouble(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object SubtractSingle(Object d1, Object d2)
        {
            float? result = Convert.ToSingle(d1) - Convert.ToSingle(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object SubtractInt64(Object d1, Object d2)
        {
            long? result = Convert.ToInt64(d1) - Convert.ToInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object SubtractInt32(Object d1, Object d2)
        {
            int? result = Convert.ToInt32(d1) - Convert.ToInt32(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object SubtractUInt64(Object d1, Object d2)
        {
            ulong? result = Convert.ToUInt64(d1) - Convert.ToUInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object SubtractUInt32(Object d1, Object d2)
        {
            uint? result = Convert.ToUInt32(d1) - Convert.ToUInt32(d2);
            return result;
        }

        /**
         * Computer for type-specific arith. operations.
         */
        public static Object DivideDouble(Object d1, Object d2)
        {
            double? result = Convert.ToDouble(d1) / Convert.ToDouble(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object DivideSingle(Object d1, Object d2)
        {
            float? result = Convert.ToSingle(d1) / Convert.ToSingle(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
		public static Object DivideInt64( Object d1, Object d2 )
        {
            long? result = Convert.ToInt64(d1) / Convert.ToInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object DivideInt32(Object d1, Object d2)
        {
            int? result = Convert.ToInt32(d1) / Convert.ToInt32(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object DivideUInt64(Object d1, Object d2)
        {
            ulong? result = Convert.ToUInt64(d1) / Convert.ToUInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object DivideUInt32(Object d1, Object d2)
        {
            uint? result = Convert.ToUInt32(d1) / Convert.ToUInt32(d2);
            return result;
        }

        /**
         * Computer for type-specific arith. operations.
         */
        public static Object MultiplyDouble(Object d1, Object d2)
        {
            double? result = Convert.ToDouble(d1) * Convert.ToDouble(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object MultiplySingle(Object d1, Object d2)
        {
            float? result = Convert.ToSingle(d1) * Convert.ToSingle(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
		public static Object MultiplyInt64( Object d1, Object d2 )
        {
            long? result = Convert.ToInt64(d1) * Convert.ToInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object MultiplyInt32(Object d1, Object d2)
        {
            int? result = Convert.ToInt32(d1) * Convert.ToInt32(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object MultiplyUInt64(Object d1, Object d2)
        {
            ulong? result = Convert.ToUInt64(d1) * Convert.ToUInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object MultiplyUInt32(Object d1, Object d2)
        {
            uint? result = Convert.ToUInt32(d1) * Convert.ToUInt32(d2);
            return result;
        }

        /**
         * Computer for type-specific arith. operations.
         */
        public static Object ModuloDouble(Object d1, Object d2)
        {
            double? result = Convert.ToDouble(d1) % Convert.ToDouble(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object ModuloSingle(Object d1, Object d2)
        {
            float result = Convert.ToSingle(d1) % Convert.ToSingle(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
		public static Object ModuloInt64( Object d1, Object d2 )
        {
            long result = Convert.ToInt64(d1) % Convert.ToInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object ModuloInt32(Object d1, Object d2)
        {
            int result = Convert.ToInt32(d1) % Convert.ToInt32(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object ModuloUInt64(Object d1, Object d2)
        {
            ulong result = Convert.ToUInt64(d1) % Convert.ToUInt64(d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static Object ModuloUInt32(Object d1, Object d2)
        {
            uint result = Convert.ToUInt32(d1) % Convert.ToUInt32(d2);
            return result;
        }

        /**
         * Returns string representation of enum.
         * @return text for enum
         */
        public String getExpressionText()
        {
            return expressionText;
        }
    }
}
