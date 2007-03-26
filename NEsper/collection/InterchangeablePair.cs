using System;
namespace net.esper.collection
{

    /// <summary> General-purpose pair of values of any type. The pair equals another pair if
    /// the objects that form the pair equal in any order, ie. first pair first object equals (.equals)
    /// the second pair first object or second object, and the first pair second object equals the second pair first object
    /// or second object.
    /// </summary>
    public sealed class InterchangeablePair<FirstT, SecondT>
    {
        /// <summary>
        /// Gets or sets the first value within the pair.
        /// </summary>

        public FirstT First
        {
            get { return first; }
            set { this.first = value; }
        }

        /// <summary>
        /// Gets or sets the second value within the pair.
        /// </summary>

        public SecondT Second
        {
            get { return second; }
            set { this.second = value; }
        }

        private FirstT first;
        private SecondT second;

        /// <summary> Construct pair of values.</summary>
        /// <param name="first">is the first value</param>
        /// <param name="second">is the second value</param>

        public InterchangeablePair(FirstT first, SecondT second)
        {
            this.first = first;
            this.second = second;
        }

        /// <summary>
        /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
        /// <returns>
        /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
        /// </returns>
        public override bool Equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }

            if (!(obj is InterchangeablePair<FirstT, SecondT>))
            {
                return false;
            }

            InterchangeablePair<FirstT, SecondT> other = (InterchangeablePair<FirstT, SecondT>)obj;

            if ((first == null) && (second == null))
            {
                return ((other.first == null) && (other.second == null));
            }

            if ((first == null) && (second != null))
            {
                if (other.second != null)
                {
                    return (other.first == null) && second.Equals(other.second);
                }
                else
                {
                    return second.Equals(other.first);
                }
            }

            if ((first != null) && (second == null))
            {
                if (other.first != null)
                {
                    return first.Equals(other.first) && (other.second == null);
                }
                else
                {
                    return first.Equals(other.second);
                }
            }

            return (
                (first.Equals(other.first) && second.Equals(other.second)) ||
                (first.Equals(other.second) && second.Equals(other.first))
                );
        }

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override int GetHashCode()
        {
            return
                (first == null ? 0 : first.GetHashCode()) ^
                (second == null ? 0 : second.GetHashCode());
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return "Pair [" + first + ':' + second + ']';
        }
    }
}