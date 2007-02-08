using System;

namespace net.esper.filter
{
	/// <summary>
	/// Filter parameter value defining the event property to filter, the
	/// filter operator, and the filter value.
	/// </summary>
	
	public class FilterValueSetParamImpl : FilterValueSetParam
	{
		private readonly String propertyName;
		private readonly FilterOperator filterOperator;
		private readonly Object filterValue;
		
		virtual public String PropertyName
		{
			get
			{
				return propertyName;
			}
			
		}
		virtual public FilterOperator FilterOperator
		{
			get
			{
				return filterOperator;
			}
			
		}
		virtual public Object FilterForValue
		{
			get
			{
				return filterValue;
			}
			
		}

		/// <summary> Ctor.</summary>
		/// <param name="propertyName">- property to interrogate
		/// </param>
		/// <param name="filterOperator">- operator to apply
		/// </param>
		/// <param name="filterValue">- value to look for
		/// </param>
		public FilterValueSetParamImpl(String propertyName, FilterOperator filterOperator, Object filterValue)
		{
			this.propertyName = propertyName;
			this.filterOperator = filterOperator;
			this.filterValue = filterValue;
		}
	}
}
