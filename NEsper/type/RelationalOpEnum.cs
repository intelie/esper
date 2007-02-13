using System;
using System.Collections.Generic;

using net.esper.collection;

namespace net.esper.type
{
    /// <summary>
    /// Enum representing relational types of operation.
    /// </summary>

    public class RelationalOpEnum
    {
        /// <summary>Greater then.</summary>
        public static readonly RelationalOpEnum GT = new RelationalOpEnum(">");

        /// <summary>Greater equals.</summary>
        public static readonly RelationalOpEnum GE = new RelationalOpEnum(">=");

        /// <summary>Lesser then.</summary>
        public static readonly RelationalOpEnum LT = new RelationalOpEnum("<");

        /// <summary>Lesser equals.</summary>
        public static readonly RelationalOpEnum LE = new RelationalOpEnum("<=");

        private static IDictionary<MultiKey<Object>, RelationalOpEnum.Computer> computers;

        public static readonly RelationalOpEnum[] Values = new RelationalOpEnum[]
		{
			GT,
			GE,
			LT,
			LE
		};

        private String expressionText;

        private RelationalOpEnum(String expressionText)
        {
            this.expressionText = expressionText;
        }

        static RelationalOpEnum()
        {
            computers = new Dictionary<MultiKey<Object>, RelationalOpEnum.Computer>();
            computers.Add(new MultiKey<Object>(new Object[] { typeof(String), GT }), GTStringComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(String), GE }), GEStringComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(String), LT }), LTStringComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(String), LE }), LEStringComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), GT }), GTLongComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), GE }), GELongComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), LT }), LTLongComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(long?), LE }), LELongComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), GT }), GTDoubleComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), GE }), GEDoubleComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), LT }), LTDoubleComputer);
            computers.Add(new MultiKey<Object>(new Object[] { typeof(double?), LE }), LEDoubleComputer);
        }

        /**
         * Returns the computer to use for the relational operation based on the coercion type.
         * @param coercedType is the object type
         * @return computer for performing the relational op
         */
        public RelationalOpEnum.Computer getComputer(Type coercedType)
        {
            if ((coercedType != typeof(double)) &&
                 (coercedType != typeof(long)) &&
                 (coercedType != typeof(String)))
            {
                throw new ArgumentException("Unsupported type for relational op compare, type " + coercedType);
            }

            MultiKey<Object> key = new MultiKey<Object>(new Object[] { coercedType, this });
            RelationalOpEnum.Computer computer;
            return
                (computers.TryGetValue(key, out computer)) ?
                (computer) :
                (null);
        }

        public delegate Boolean Computer(Object objOne, Object objTwo);

        /**
         * Computer for relational op compare.
         */
        public static bool GTStringComputer(Object objOne, Object objTwo)
        {
            String s1 = (String)objOne;
            String s2 = (String)objTwo;
            int result = s1.CompareTo(s2);
            return result > 0;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool GEStringComputer(Object objOne, Object objTwo)
        {
            String s1 = (String)objOne;
            String s2 = (String)objTwo;
            return s1.CompareTo(s2) >= 0;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool LEStringComputer(Object objOne, Object objTwo)
        {
            String s1 = (String)objOne;
            String s2 = (String)objTwo;
            return s1.CompareTo(s2) <= 0;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool LTStringComputer(Object objOne, Object objTwo)
        {
            String s1 = (String)objOne;
            String s2 = (String)objTwo;
            return s1.CompareTo(s2) < 0;
        }

        /**
         * Computer for relational op compare.
         */
        public static bool GTLongComputer(Object objOne, Object objTwo)
        {
            Int64 s1 = Convert.ToInt64(objOne);
            Int64 s2 = Convert.ToInt64(objTwo);
            return s1 > s2;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool GELongComputer(Object objOne, Object objTwo)
        {
            Int64 s1 = Convert.ToInt64(objOne);
            Int64 s2 = Convert.ToInt64(objTwo);
            return s1 >= s2;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool LTLongComputer(Object objOne, Object objTwo)
        {
            Int64 s1 = Convert.ToInt64(objOne);
            Int64 s2 = Convert.ToInt64(objTwo);
            return s1 < s2;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool LELongComputer(Object objOne, Object objTwo)
        {
            Int64 s1 = Convert.ToInt64(objOne);
            Int64 s2 = Convert.ToInt64(objTwo);
            return s1 <= s2;
        }

        /**
         * Computer for relational op compare.
         */
        public static bool GTDoubleComputer(Object objOne, Object objTwo)
        {
            double s1 = Convert.ToDouble(objOne);
            double s2 = Convert.ToDouble(objTwo);
            return s1 > s2;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool GEDoubleComputer(Object objOne, Object objTwo)
        {
            double s1 = Convert.ToDouble(objOne);
            double s2 = Convert.ToDouble(objTwo);
            return s1 >= s2;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool LTDoubleComputer(Object objOne, Object objTwo)
        {
            double s1 = Convert.ToDouble(objOne);
            double s2 = Convert.ToDouble(objTwo);
            return s1 < s2;
        }
        /**
         * Computer for relational op compare.
         */
        public static bool LEDoubleComputer(Object objOne, Object objTwo)
        {
            double s1 = Convert.ToDouble(objOne);
            double s2 = Convert.ToDouble(objTwo);
            return s1 <= s2;
        }

        /**
         * Returns string rendering of enum.
         * @return relational op string
         */
        public String getExpressionText()
        {
            return expressionText;
        }
    }
}