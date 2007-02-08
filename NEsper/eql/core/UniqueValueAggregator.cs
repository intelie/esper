using System;

using net.esper.collection;

namespace net.esper.eql.core
{
	/// <summary> Aggregator for use on top of another aggregator that handles unique value aggregation (versus all-value aggregation)
	/// for the underlying aggregator.
	/// </summary>

    public class UniqueValueAggregator : Aggregator
	{
		virtual public Object Value
		{
			get
			{
				return inner.Value;
			}
			
		}
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
		
		public virtual void enter(Object value)
		{
			// if value not already encountered, enter into aggregate
			if (valueSet.Add(value))
			{
				inner.enter(value);
			}
		}
		
		public virtual void leave(Object value)
		{
			// if last reference to the value is removed, remove from aggregate
			if (valueSet.Remove(value))
			{
				inner.leave(value);
			}
		}
		
		public virtual Aggregator newAggregator()
		{
			return new UniqueValueAggregator(inner.newAggregator());
		}
	}
}
