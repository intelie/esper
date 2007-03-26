using System;
namespace net.esper.eql.core
{
	/// <summary> Maintains aggregation state applying values as entering and leaving the state.
	/// <para>Implementations must also act as a factory for further independent copies of aggregation states such that
	/// new aggregation state holders and be created from a prototype.</para>
	/// </summary>
	public interface Aggregator
	{
		/// <summary> Returns the current value held.</summary>
		/// <returns> current value
		/// </returns>
		Object Value
		{
			get;			
		}

		/// <summary> Returns the type of the current value.</summary>
		/// <returns> type of values held
		/// </returns>
		Type ValueType
		{
			get;			
		}

		/// <summary>
        /// Apply the value as entering aggregation (entering window).
		/// The value can be null since 'null' values may be counted as unique separate values.
		/// </summary>
		/// <param name="value">to add to aggregate
		/// </param>
		void Enter(Object value);
		
		/// <summary>
        /// Apply the value as leaving aggregation (leaving window).
		/// The value can be null since 'null' values may be counted as unique separate values.
		/// </summary>
		/// <param name="value">to remove from aggregate
		/// </param>
		void Leave(Object value);
		
		/// <summary> Make a new, initalized aggregation state.</summary>
		/// <returns> initialized copy of the aggregator
		/// </returns>
		Aggregator NewAggregator();
	}
}