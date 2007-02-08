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
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Double), ADD }), AddDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Single), ADD }), AddSingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int64), ADD }), AddInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int32), ADD }), AddInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Double), SUBTRACT }), SubtractDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Single), SUBTRACT }), SubtractSingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int64), SUBTRACT }), SubtractInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int32), SUBTRACT }), SubtractInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Double), DIVIDE }), DivideDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Single), DIVIDE }), DivideSingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int64), DIVIDE }), DivideInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int32), DIVIDE }), DivideInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Double), MULTIPLY }), MultiplyDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Single), MULTIPLY }), MultiplySingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int64), MULTIPLY }), MultiplyInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int32), MULTIPLY }), MultiplyInt32);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Double), MODULO }), ModuloDouble);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Single), MODULO }), ModuloSingle);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int64), MODULO }), ModuloInt64);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(Int32), MODULO }), ModuloInt32);
        }

        /**
         * Interface for number cruncher.
         */
        public delegate ValueType Computer(ValueType d1, ValueType d2);

        private String expressionText;

        private MathArithTypeEnum(String expressionText)
        {
            this.expressionText = expressionText;
        }

        /**
         * Returns number cruncher for the target coercion type.
         * @param coercedType - target type
         * @return number cruncher
         */
        public Computer getComputer(Type coercedType)
        {
            if ((coercedType != typeof(double)) &&
                (coercedType != typeof(float)) &&
                (coercedType != typeof(long)) &&
                (coercedType != typeof(int)))
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
        public static ValueType AddDouble(ValueType d1, ValueType d2)
        {
            double result = ((double) d1) + ((double) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType AddSingle(ValueType d1, ValueType d2)
        {
            float result = ((float) d1) + ((float) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType AddInt64(ValueType d1, ValueType d2)
        {
            long result = ((long) d1) + ((long) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType AddInt32(ValueType d1, ValueType d2)
        {
            int result = ((int) d1) + ((int) d2);
            return result;
        }

        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType SubtractDouble(ValueType d1, ValueType d2)
        {
            double result = ((double) d1) - ((double) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType SubtractSingle(ValueType d1, ValueType d2)
        {
            float result = ((float) d1) - ((float) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType SubtractInt64(ValueType d1, ValueType d2)
        {
            long result = ((long) d1) - ((long) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType SubtractInt32(ValueType d1, ValueType d2)
        {
            int result = ((int) d1) - ((int) d2);
            return result;
        }

        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType DivideDouble(ValueType d1, ValueType d2)
        {
            double result = ((double) d1) / ((double) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType DivideSingle(ValueType d1, ValueType d2)
        {
            float result = ((float) d1) / ((float) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
		public static ValueType DivideInt64( ValueType d1, ValueType d2 )
        {
            long result = ((long) d1) / ((long) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType DivideInt32(ValueType d1, ValueType d2)
        {
            int result = ((int) d1) / ((int) d2);
            return result;
        }

        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType MultiplyDouble(ValueType d1, ValueType d2)
        {
            double result = ((double) d1) * ((double) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType MultiplySingle(ValueType d1, ValueType d2)
        {
            float result = ((float) d1) * ((float) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
		public static ValueType MultiplyInt64( ValueType d1, ValueType d2 )
        {
            long result = ((long) d1) * ((long) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType MultiplyInt32(ValueType d1, ValueType d2)
        {
            int result = ((int) d1) * ((int) d2);
            return result;
        }

        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType ModuloDouble(ValueType d1, ValueType d2)
        {
            double result = ((double) d1) % ((double) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType ModuloSingle(ValueType d1, ValueType d2)
        {
            float result = ((float) d1) % ((float) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
		public static ValueType ModuloInt64( ValueType d1, ValueType d2 )
        {
            long result = ((long) d1) % ((long) d2);
            return result;
        }
        /**
         * Computer for type-specific arith. operations.
         */
        public static ValueType ModuloInt32(ValueType d1, ValueType d2)
        {
            int result = ((int) d1) % ((int) d2);
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
