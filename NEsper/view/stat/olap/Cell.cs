using System;
namespace net.esper.view.stat.olap
{
	
	/// <summary> Cell is the analytic values or variable tracked by a cube.</summary>
	public interface Cell
	{
		/// <summary> Returns the value.</summary>
		/// <returns> double value
		/// </returns>
		double Value
		{
			get;
			
		}
	}
}