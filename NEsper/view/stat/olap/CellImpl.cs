using System;

namespace net.esper.view.stat.olap
{
	/// <summary>
	/// Hold the measure information which consists of a double value.
	/// </summary>
	
	public sealed class CellImpl : Cell
	{
		public double Value
		{
			get { return value; }
		}

		private readonly double value;
		
		/// <summary> Constructor.</summary>
		/// <param name="value">is the measure value
		/// </param>
		
		public CellImpl(double value)
		{
			this.value = value;
		}
	}
}
