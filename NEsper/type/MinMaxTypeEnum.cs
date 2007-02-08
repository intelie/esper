using System;

namespace net.esper.type
{
    /// <summary>
    /// Enumeration for the type of arithmatic to use.
    /// </summary>

    public class MinMaxTypeEnum
    {
        /// <summary> Max.</summary>
        public static readonly MinMaxTypeEnum MAX = new MinMaxTypeEnum("max");
        /// <summary> Min.</summary>
        public static readonly MinMaxTypeEnum MIN = new MinMaxTypeEnum("min");

        private String expressionText;

        private MinMaxTypeEnum(String expressionText)
        {
            this.expressionText = expressionText;
        }

        /// <summary> Returns textual representation of enum.</summary>
        /// <returns> text for enum
        /// </returns>

        public String ExpressionText
        {
            get { return expressionText; }
        }
    }
}
