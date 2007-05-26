using System;
namespace net.esper.filter
{
    /// <summary>
    /// Holds a range of double values with a minimum (Start) value and a maximum (end) value.
    /// </summary>

    public sealed class DoubleRange
    {
        /// <summary> Returns low endpoint.</summary>
        /// <returns> low endpoint
        /// </returns>

        public double? Min
        {
            get { return min; }
        }

        /// <summary> Returns high endpoint.</summary>
        /// <returns> high endpoint
        /// </returns>

        public double? Max
        {
            get { return max; }
        }

        private double? min;
        private double? max;
        private int hashCode;

        /// <summary> Constructor - takes range endpoints.</summary>
        /// <param name="min">is the low endpoint
        /// </param>
        /// <param name="max">is the high endpoint
        /// </param>
        public DoubleRange(double? min, double? max)
        {
            this.min = min;
            this.max = max;

	        if ((min != null) && (max != null))
	        {
	            if (min > max)
	            {
	                this.max = min;
	                this.min = max;
	            }
	        }

	        hashCode = 7;
	        if (min != null)
	        {
	            hashCode = hashCode ^ min.GetHashCode();
	        }
	        if (max != null)
	        {
	            hashCode = hashCode ^ max.GetHashCode();
	        }
        }

        /// <summary>
        /// Returns true if the objects are equal.
        /// </summary>
        /// <param name="other">The other object.</param>
        /// <returns></returns>
        public override bool Equals(Object other)
        {
            if (other == this)
            {
                return true;
            }
            if (!(other is DoubleRange))
            {
                return false;
            }

            DoubleRange otherRange = (DoubleRange)other;
            return ((otherRange.max == this.max) && (otherRange.min == this.min));
        }

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override int GetHashCode()
        {
            return hashCode;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return "DoubleRange" + " min=" + min + " max=" + max;
        }
    }
}