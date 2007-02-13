using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.eql.parse
{	
	/// <summary>
	/// Represents a list of values in a set of numeric parameters.
	/// </summary>

	public class ListParameter : NumberSetParameter
	{
		private IList<NumberSetParameter> parameters;

		/// <summary>
		/// Initializes a new instance of the <see cref="T:ListParameter"/> class.
		/// </summary>

		public ListParameter()
		{
			this.parameters = new List<NumberSetParameter>();
		}

		/// <summary> Add to the list a further parameter.</summary>
		/// <param name="numberSetParameter">is the parameter to add
		/// </param>
		public virtual void Add( NumberSetParameter numberSetParameter )
		{
			parameters.Add( numberSetParameter );
		}

		/// <summary> Returns list of parameters.</summary>
		/// <returns> list of parameters
		/// </returns>
		public IList<NumberSetParameter> Parameters
		{
            get { return parameters; }
		}

		public virtual bool IsWildcard( int min, int max )
		{
			foreach ( NumberSetParameter param in parameters )
			{
				if ( param.IsWildcard( min, max ) )
				{
					return true;
				}
			}
			return false;
		}

		public ISet<Int32> GetValuesInRange( int min, int max )
		{
			ISet<Int32> result = new EHashSet<Int32>();

			foreach ( NumberSetParameter param in parameters )
			{
				result.AddAll( param.GetValuesInRange( min, max ) );
			}

			return result;
		}
	}
}
