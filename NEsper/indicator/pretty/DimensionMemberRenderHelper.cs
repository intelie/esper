using System;

using net.esper.compat;
using net.esper.view.stat.olap;

namespace net.esper.indicator.pretty
{
	/// <summary>
	/// Utility methods for rendering dimension members as a string.
	/// </summary>
	
	public sealed class DimensionMemberRenderHelper
	{
		/// <summary> Renders dimension members as a String.</summary>
		/// <param name="dimensionMember">is the dimension dimensionMember to render
		/// </param>
		/// <returns> rendered dimensionMember
		/// </returns>
		public static String renderMember(DimensionMember dimensionMember)
		{
			Object[] values = dimensionMember.Values;
			
			if (values.Length == 0)
			{
				return renderPropertyNames(dimensionMember.getDimension().PropertyNames);
			}
			
			return renderObjects(dimensionMember.Values);
		}
		
		private static String renderPropertyNames(String[] propertyNames)
		{
			if (propertyNames.Length == 1)
			{
				return propertyNames[0];
			}
			
			return CollectionHelper.Render(propertyNames);
		}
		
		private static String renderObjects(Object[] values)
		{
			if (values.Length == 1)
			{
				return values[0].ToString();
			}
			return CollectionHelper.Render(values);
		}
	}
}
