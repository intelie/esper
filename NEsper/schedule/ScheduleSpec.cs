using System;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;
using net.esper.util;

namespace net.esper.schedule
{
	/// <summary> Holds a schedule specification which consists of a set of integer values or a null
	/// value for each schedule unit to indicate a wildcard.
	/// There is always an element in the specification for each unit minutes, hours, day of month, month, and day of week.
	/// There is optionally an element in the specification for the unit seconds.
	/// </summary>
	public sealed class ScheduleSpec : MetaDefItem
	{
		// Per unit hold the set of valid integer values, or null if wildcarded.
		// The seconds unit is optional.
		private readonly EnumDictionary<ScheduleUnit, ETreeSet<Int32>> unitValues;

		/// <summary> Constructor - validates that all mandatory schedule.</summary>
		/// <param name="unitValues">are the values for each minute, hour, day, month etc.
		/// </param>
		/// <throws>  ArgumentException - if validation of value set per unit fails </throws>
		public ScheduleSpec( EnumDictionary<ScheduleUnit, ETreeSet<Int32>> unitValues )
		{
			Validate( unitValues );

			// Reduce to wildcards any unit's values set, if possible
			compress( unitValues );

			this.unitValues = unitValues;
		}

		/// <summary>
		/// Constructor - for unit testing, initialize to all wildcards but leave seconds empty.
		/// </summary>

		public ScheduleSpec()
		{
			unitValues = new EnumDictionary<ScheduleUnit, ETreeSet<Int32>>();
			unitValues[ScheduleUnit.MINUTES] = null;
			unitValues[ScheduleUnit.HOURS] = null;
			unitValues[ScheduleUnit.DAYS_OF_MONTH] = null;
			unitValues[ScheduleUnit.MONTHS] = null;
			unitValues[ScheduleUnit.DAYS_OF_WEEK] = null;
		}

		/// <summary> Return map of ordered set of valid schedule values for minute, hour, day, month etc. units</summary>
		/// <returns> map of 5 or 6 entries each with a set of integers
		/// </returns>

		public EnumDictionary<ScheduleUnit, ETreeSet<Int32>> UnitValues
		{
			get { return unitValues; }
		}

		/// <summary> For unit testing, add a single value, changing wildcards to value sets.</summary>
		/// <param name="element">to add
		/// </param>
		/// <param name="value">to add
		/// </param>

		public void AddValue( ScheduleUnit element, int value )
		{
            ETreeSet<Int32> _set = unitValues.Fetch(element, null);
            if ( _set == null )
			{
				_set = new ETreeSet<Int32>();
				unitValues[element] = _set;
			}
            
			_set.Add(value);
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			StringBuilder buffer = new StringBuilder();
			foreach ( ScheduleUnit element in ScheduleUnit.Values )
			{
				if ( !unitValues.ContainsKey( element ) )
				{
					continue;
				}

				ISet<Int32> valueSet = unitValues.Fetch( element, null ) ;
				buffer.Append( element + "={" );
				if ( valueSet == null )
				{
					buffer.Append( "null" );
				}
				else
				{
					String delimiter = "";
					foreach ( int i in valueSet )
					{
						buffer.Append( delimiter + i );
						delimiter = ",";
					}
				}
				buffer.Append( "} " );
			}
			return buffer.ToString();
		}

        /// <summary>
        /// Returns true if this object equals the other object.
        /// </summary>
        /// <param name="otherObject">The other object.</param>
        /// <returns></returns>
		public override bool Equals( Object otherObject )
		{
			if ( otherObject == this )
			{
				return true;
			}

			if ( otherObject == null )
			{
				return false;
			}

			if ( GetType() != otherObject.GetType() )
			{
				return false;
			}

			ScheduleSpec other = (ScheduleSpec) otherObject;
			if ( this.unitValues.Count != other.unitValues.Count )
			{
				return false;
			}

			foreach ( KeyValuePair<ScheduleUnit, ETreeSet<Int32>> entry in unitValues )
			{
				ISet<Int32> mySet = entry.Value;
				ISet<Int32> otherSet = other.unitValues.Fetch( entry.Key, null ) ;

				if ( ( otherSet == null ) && ( mySet != null ) )
				{
					return false;
				}
				if ( ( otherSet != null ) && ( mySet == null ) )
				{
					return false;
				}
				if ( ( otherSet == null ) && ( mySet == null ) )
				{
					continue;
				}
				if ( mySet.Count != otherSet.Count )
				{
					return false;
				}

				// Commpare value by value
				foreach ( int i in mySet )
				{
                    if (!(otherSet.Contains(i)))
					{
						return false;
					}
				}
			}

			return true;
		}

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override int GetHashCode()
		{
			int hashCode = 0;
			foreach ( KeyValuePair<ScheduleUnit, ETreeSet<Int32>> entry in unitValues )
			{
				if ( entry.Value != null )
				{
					Int32 firstValue = entry.Value.First;
					hashCode ^= firstValue;
				}
			}
			return hashCode;
		}

		/// <summary> Function to reduce value sets for unit that cover the whole range down to a wildcard.
		/// I.e. reduce 0,1,2,3,4,5,6 for week value to 'null' indicating the wildcard.
		/// </summary>
		/// <param name="unitValues">is the set of valid values per unit
		/// </param>
		internal static void compress( IDictionary<ScheduleUnit, ETreeSet<Int32>> unitValues )
		{
            List<ScheduleUnit> termList = new List<ScheduleUnit>();

			foreach ( KeyValuePair<ScheduleUnit, ETreeSet<Int32>> entry in unitValues )
			{
				int elementValueSetSize = entry.Key.Max() - entry.Key.Min() + 1;
				if ( entry.Value != null )
				{
					if ( entry.Value.Count == elementValueSetSize )
					{
                        termList.Add(entry.Key);
					}
				}
			}

            foreach (ScheduleUnit scheduleUnit in termList)
            {
                unitValues[scheduleUnit] = null;
            }
		}

		/// <summary> Validate units and their value sets.</summary>
		/// <param name="unitValues">is the set of valid values per unit
		/// </param>
		internal static void Validate( EDictionary<ScheduleUnit, ETreeSet<Int32>> unitValues )
		{
			if ( ( !unitValues.ContainsKey( ScheduleUnit.MONTHS ) ) ||
				( !unitValues.ContainsKey( ScheduleUnit.DAYS_OF_WEEK ) ) ||
				( !unitValues.ContainsKey( ScheduleUnit.HOURS ) ) ||
				( !unitValues.ContainsKey( ScheduleUnit.MINUTES ) ) ||
				( !unitValues.ContainsKey( ScheduleUnit.DAYS_OF_MONTH ) ) )
			{
				throw new ArgumentException( "Incomplete information for schedule specification, only the following keys are supplied=" + CollectionHelper.Render( unitValues.Keys ) );
			}

			foreach ( ScheduleUnit unit in ScheduleUnit.Values )
			{
				if ( ( unit == ScheduleUnit.SECONDS ) && ( !unitValues.ContainsKey( unit ) ) )
				// Seconds are optional
				{
					continue;
				}

				if ( unitValues.Fetch( unit, null ) == null )
				// Wildcard - no validation for unit
				{
					continue;
				}

				ETreeSet<Int32> values = unitValues.Fetch( unit, null );
                foreach (Int32 _value in values)
				{
					if ( ( _value < unit.Min() ) || ( _value > unit.Max() ) )
					{
						throw new ArgumentException(
							"Invalid value found for schedule unit, value of " + _value +
							" is not valid for unit " + unit );
					}
				}
			}
		}
	}
}
