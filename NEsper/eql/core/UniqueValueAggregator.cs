using System;

using net.esper.collection;

namespace net.esper.eql.core
{
	/// <summary> Aggregator for use on top of another aggregator that handles unique value aggregation (versus all-value aggregation)
	/// for the underlying aggregator.
	/// </summary>

    public class UniqueValueAggregator : Aggregator
	{
        /// <summary>
        /// Returns the current value held.
        /// </summary>
        /// <value></value>
        /// <returns> current value
        /// </returns>
		virtual public Object Value
		{
			get
			{
				return inner.Value;
			}
			
		}
        /// <summary>
        /// Returns the type of the current value.
        /// </summary>
        /// <value></value>
        /// <returns> type of values held
        /// </returns>
		virtual public Type ValueType
		{
			get
			{
				return inner.ValueType;
			}
			
		}

		private Aggregator inner;
		private readonly RefCountedSet<Object> valueSet;
		
		/// <summary> Ctor.</summary>
		/// <param name="inner">is the aggregator function computing aggregation values 
		/// </param>
		
        public UniqueValueAggregator(Aggregator inner)
		{
			this.inner = inner;
			this.valueSet = new RefCountedSet<Object>();
		}

        /// <summary>
        /// Apply the value as entering aggregation (entering window).
        /// The value can be null since 'null' values may be counted as unique separate values.
        /// </summary>
        /// <param name="value">to add to aggregate</param>
		public virtual void Enter(Object value)
		{
			// if value not already encountered, enter into aggregate
			if (valueSet.Add(value))
			{
				inner.Enter(value);
			}
		}

        /// <summary>
        /// Apply the value as leaving aggregation (leaving window).
        /// The value can be null since 'null' values may be counted as unique separate values.
        /// </summary>
        /// <param name="value">to remove from aggregate</param>
		public virtual void Leave(Object value)
		{
			// if last reference to the value is removed, remove from aggregate
			if (valueSet.Remove(value))
			{
				inner.Leave(value);
			}
		}

        /// <summary>
        /// Make a new, initalized aggregation state.
        /// </summary>
        /// <returns>initialized copy of the aggregator</returns>
		public virtual Aggregator NewAggregator()
		{
			return new UniqueValueAggregator(inner.NewAggregator());
		}
	}
}
