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

	    /**
	     * Returns property names of all indexes.
	     * @return property names array
	     */
	    virtual public Type[][] CoercionTypesPerIndex
	    {
	        get { return optCoercionTypes; }
	    }
		
		private String[][] indexProps;
		private Type[][] optCoercionTypes;

	    /**
	     * Ctor.
	     * @param indexProps - array of property names with the first dimension suplying the number of
	     * distinct indexes. The second dimension can be empty and indicates a full table scan.
	     * @param optCoercionTypes - array of coercion types for each index, or null entry for no coercion required
	     */
	    public QueryPlanIndex(String[][] indexProps, Type[][] optCoercionTypes)
	    {
	        if ((indexProps == null) || (indexProps.Length == 0) || (optCoercionTypes == null))
	        {
	            throw new ArgumentException("Null or empty index properites or coercion types-per-index parameter is supplied, expecting at least one entry");
	        }
	        this.indexProps = indexProps;
	        this.optCoercionTypes = optCoercionTypes;
	    }

		/// <summary> Find a matching index for the property names supplied.</summary>
		/// <param name="indexFields">property names to search for
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

	    /**
	     * Add an index specification element.
	     * @param indexProperties - list of property names to index
	     * @param coercionTypes - list of coercion types if required, or null if no coercion required
	     * @return number indicating position of index that was added
	     */
	    public int AddIndex(String[] indexProperties, Type[] coercionTypes)
	    {
	        int numElements = indexProps.Length;
	        String[][] newProps = new String[numElements + 1][];
	        Array.Copy(indexProps, 0, newProps, 0, numElements);
	        newProps[numElements] = indexProperties;
	        indexProps = newProps;

	        Type[][] newCoercionTypes = new Type[numElements + 1][];
	        Array.Copy(optCoercionTypes, 0, newCoercionTypes, 0, numElements);
	        newCoercionTypes[numElements] = coercionTypes;
	        optCoercionTypes = newCoercionTypes;

	        return numElements;
	    }

	    /**
	     * Returns a list of coercion types for a given index.
	     * @param indexProperties is the index field names
	     * @return coercion types, or null if no coercion is required
	     */
	    public Type[] GetCoercionTypes(String[] indexProperties)
	    {
	        for (int i = 0; i < indexProps.Length; i++)
	        {
	            if (Arrays.DeepEquals(indexProps[i], indexProperties))
	            {
	                return this.optCoercionTypes[i];
	            }
	        }
	        throw new ArgumentException("Index properties not found");
	    }

	    /**
	     * Sets the coercion types for a given index.
	     * @param indexProperties is the index property names
	     * @param coercionTypes is the coercion types
	     */
	    public void setCoercionTypes(String[] indexProperties, Type[] coercionTypes)
	    {
	        bool found = false;
	        for (int i = 0; i < indexProps.Length; i++)
	        {
	            if (Arrays.DeepEquals(indexProps[i], indexProperties))
	            {
	                this.optCoercionTypes[i] = coercionTypes;
	                found = true;
	            }
	        }
	        if (!found)
	        {
	            throw new ArgumentException("Index properties not found");
	        }
	    }
	
        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			if ( indexProps == null )
			{
				return "indexProperties=null";
			}

			StringBuilder buf = new StringBuilder();
			for ( int i = 0 ; i < indexProps.Length ; i++ )
			{
				buf.Append( "indexProperties(" );
				buf.Append( i ) ;
				buf.Append( ")=" ) ;
				buf.Append( CollectionHelper.Render( indexProps[i] ) ) ;
				buf.Append( " " );
			}

			return buf.ToString();
		}

		/// <summary> Print index specifications in readable format.</summary>
		/// <param name="indexSpecs">define indexes
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
