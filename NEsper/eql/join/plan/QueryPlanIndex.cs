using System;
using System.Text;

using net.esper.compat;

namespace net.esper.eql.join.plan
{
	/// <summary>
	/// Specifies an index to build as part of an overall query plan.
	/// </summary>

	public class QueryPlanIndex
	{
		/// <summary> Returns property names of all indexes.</summary>
		/// <returns> property names array
		/// </returns>
		virtual public String[][] IndexProps
		{
			get { return indexProps; }
		}

		private String[][] indexProps;

		/// <summary> Ctor.</summary>
		/// <param name="indexProps">- array of property names with the first dimension suplying the number of
		/// distinct indexes. The second dimension can be empty and indicates a full table scan.
		/// </param>
		
		public QueryPlanIndex( String[][] indexProps )
		{
			if ( ( indexProps == null ) || ( indexProps.Length == 0 ) )
			{
				throw new ArgumentException( "Null or empty index properites parameter is supplied, expecting at least one entry" );
			}
			this.indexProps = indexProps;
		}

		/// <summary> Find a matching index for the property names supplied.</summary>
		/// <param name="indexFields">- property names to search for
		/// </param>
		/// <returns> -1 if not found, or offset within indexes if found
		/// </returns>
		
		public virtual int GetIndexNum( String[] indexFields )
		{
			for ( int i = 0 ; i < indexProps.Length ; i++ )
			{
				if ( ArrayHelper.AreEqual( indexFields, indexProps[i] ) )
				{
					return i;
				}
			}
			return -1;
		}

		/// <summary> Add an index specification element.</summary>
		/// <param name="indexProperties">- list of property names to index
		/// </param>
		/// <returns> number indicating position of index that was added
		/// </returns>
		public virtual int AddIndex( String[] indexProperties )
		{
			int numElements = indexProps.Length;
			String[][] newProps = new String[numElements + 1][];
			for ( int i = 0 ; i < numElements ; i++ )
			{
				newProps[i] = indexProps[i];
			}
			newProps[numElements] = indexProperties;

			indexProps = newProps;

			return numElements;
		}

		public override String ToString()
		{
			if ( indexProps == null )
			{
				return "indexProperties=null";
			}

			StringBuilder buf = new StringBuilder();
			for ( int i = 0 ; i < indexProps.Length ; i++ )
			{
				buf.Append( "indexProperties(" + i + ")=" + CollectionHelper.Render( indexProps[i] ) + " " );
			}

			return buf.ToString();
		}

		/// <summary> Print index specifications in readable format.</summary>
		/// <param name="indexSpecs">- define indexes
		/// </param>
		/// <returns> readable format of index info
		/// </returns>
		public static String Print( QueryPlanIndex[] indexSpecs )
		{
			StringBuilder buffer = new StringBuilder();
			buffer.Append( "QueryPlanIndex[]\n" );

			for ( int i = 0 ; i < indexSpecs.Length ; i++ )
			{
				buffer.Append( "  index spec " + i + " : " + indexSpecs[i].ToString() + "\n" );
			}

			return buffer.ToString();
		}
	}
}
