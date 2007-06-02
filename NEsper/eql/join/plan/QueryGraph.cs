using System;
using System.IO;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;

namespace net.esper.eql.join.plan
{
	/// <summary> Model of relationships between streams based on properties in both streams that are
	/// specified as equal in a filter expression.
	/// </summary>

	public class QueryGraph
	{
		/// <summary> Returns the number of streams.</summary>
		/// <returns> number of streams
		/// </returns>

		virtual public int NumStreams
		{
			get { return numStreams; }
		}

		private readonly int numStreams;
		private readonly IDictionary<GraphKey, GraphValue> streamJoinMap;

		/// <summary> Ctor.</summary>
		/// <param name="numStreams">number of streams
		/// </param>
		public QueryGraph( int numStreams )
		{
			this.numStreams = numStreams;
			this.streamJoinMap = new Dictionary<GraphKey, GraphValue>();
		}

		/// <summary> Add properties for 2 streams that are equal.</summary>
		/// <param name="streamLeft">left hand stream
		/// </param>
		/// <param name="propertyLeft">left hand stream property
		/// </param>
		/// <param name="streamRight">right hand stream
		/// </param>
		/// <param name="propertyRight">right hand stream property
		/// </param>
		/// <returns> true if added and did not exist, false if already known
		/// </returns>

		public virtual bool Add( int streamLeft, String propertyLeft, int streamRight, String propertyRight )
		{
			if ( propertyLeft == null || propertyRight == null )
			{
				throw new ArgumentException( "Null property names supplied" );
			}

			if ( streamLeft == streamRight )
			{
				throw new ArgumentException( "Streams supplied are the same" );
			}

			GraphKey key = new GraphKey( streamLeft, streamRight );
            GraphValue _value = null;
            if ( ! streamJoinMap.TryGetValue(key, out _value ) )
			{
            	_value = new GraphValue() ;
				streamJoinMap[key] = _value;
			}

			if ( streamLeft > streamRight )
			{
				return _value.Add( propertyRight, propertyLeft );
			}
			else
			{
				return _value.Add( propertyLeft, propertyRight );
			}
		}

		/// <summary> Returns true if there is a relationship between streams via equal properties.</summary>
		/// <param name="streamFrom">from stream number
		/// </param>
		/// <param name="streamTo">to stream number
		/// </param>
		/// <returns> true if relationship exists, false if not
		/// </returns>

		public virtual bool IsNavigable( int streamFrom, int streamTo )
		{
			GraphKey key = new GraphKey( streamFrom, streamTo );
			return streamJoinMap.ContainsKey( key );
		}

		/// <summary> Returns set of streams that the given stream is navigable to.</summary>
		/// <param name="streamFrom">from stream number
		/// </param>
		/// <returns> set of streams related to this stream, or empty set if none
		/// </returns>

		public ISet<Int32> GetNavigableStreams( int streamFrom )
		{
			ISet<Int32> result = new EHashSet<Int32>();
			for ( int i = 0 ; i < numStreams ; i++ )
			{
				if ( IsNavigable( streamFrom, i ) )
				{
                    result.Add(i);
				}
			}
			return result;
		}

		/// <summary> Returns index properties.</summary>
		/// <param name="streamLookup">stream to serve as source for looking up events
		/// </param>
		/// <param name="streamIndexed">stream to look up in
		/// </param>
		/// <returns> index property names
		/// </returns>
		public virtual String[] GetIndexProperties( int streamLookup, int streamIndexed )
		{
			GraphKey key = new GraphKey( streamLookup, streamIndexed );
            GraphValue _value = null;
            if ( ! streamJoinMap.TryGetValue(key, out _value ) )
			{
				return null;
			}
            
            return
				( streamLookup > streamIndexed ) ?
            	( CollectionHelper.ToArray( _value.PropertiesLeft ) ) :
            	( CollectionHelper.ToArray( _value.PropertiesRight ) ) ;
		}

		/// <summary> Returns key properties.</summary>
		/// <param name="streamLookup">stream to serve as source for looking up events
		/// </param>
		/// <param name="streamIndexed">stream to look up in
		/// </param>
		/// <returns> key property names
		/// </returns>
		public virtual String[] GetKeyProperties( int streamLookup, int streamIndexed )
		{
			GraphKey key = new GraphKey( streamLookup, streamIndexed );
			GraphValue _value = null ;
			
			if ( ! streamJoinMap.TryGetValue( key, out _value ) )
			{
				return null;
			}

			return
				( streamLookup > streamIndexed ) ?
				( CollectionHelper.ToArray<string>( _value.PropertiesRight ) ) :
				( CollectionHelper.ToArray<string>( _value.PropertiesLeft ) ) ;
		}

		/// <summary> Fill in equivalent key properties (navigation entries) on all streams.
		/// For example, if  a=b and b=c  then add a=c. The method adds new equalivalent key properties
		/// until no additional entries to be added are found, ie. several passes can be made.
		/// </summary>
		/// <param name="queryGraph">navigablity info between streamss
		/// </param>
		public static void FillEquivalentNav( QueryGraph queryGraph )
		{
			bool addedEquivalency = false;

			// Repeat until no more entries were added
			do
			{
				addedEquivalency = false;

				// For each stream-to-stream combination
				for ( int lookupStream = 0 ; lookupStream < queryGraph.numStreams ; lookupStream++ )
				{
					for ( int indexedStream = 0 ; indexedStream < queryGraph.numStreams ; indexedStream++ )
					{
						if ( lookupStream == indexedStream )
						{
							continue;
						}

						bool added = FillEquivalentNav( queryGraph, lookupStream, indexedStream );
						if ( added )
						{
							addedEquivalency = true;
						}
					}
				}
			}
			while ( addedEquivalency );
		}

		/// <summary>
		/// Looks at the key and index (aka. left and right) properties of the 2 streams and checks
		/// for each property if any equivalent index properties exist for other streams.
	    /// </summary>
		private static bool FillEquivalentNav( QueryGraph queryGraph, int lookupStream, int indexedStream )
		{
			bool addedEquivalency = false;
			String[] keyProps = queryGraph.GetKeyProperties( lookupStream, indexedStream );
			String[] indexProps = queryGraph.GetIndexProperties( lookupStream, indexedStream );

			if ( keyProps == null )
			{
				return false;
			}
			if ( keyProps.Length != indexProps.Length )
			{
				throw new SystemException( "Unexpected key and index property number mismatch" );
			}

			for ( int i = 0 ; i < keyProps.Length ; i++ )
			{
				bool added = FillEquivalentNav( queryGraph, lookupStream, keyProps[i], indexedStream, indexProps[i] );
				if ( added )
				{
					addedEquivalency = true;
				}
			}

			return addedEquivalency;
		}

		/// <summary>
        /// Looks at the key and index (aka. left and right) properties of the 2 streams and checks
		/// for each property if any equivalent index properties exist for other streams.
		///
		/// Example:  s0.p0 = s1.p1  and  s1.p1 = s2.p2  ==> therefore s0.p0 = s2.p2
		/// ==> look stream s0, property p0; indexed stream s1, property p1
		/// Is there any other lookup stream that has stream 1 and property p1 as index property? ==> this is stream s2, p2
		/// Add navigation entry between stream s0 and property p0 to stream s2, property p2
        /// </summary>

        private static bool FillEquivalentNav( QueryGraph queryGraph, int lookupStream, String keyProp, int indexedStream, String indexProp )
		{
			bool addedEquivalency = false;

			for ( int otherStream = 0 ; otherStream < queryGraph.numStreams ; otherStream++ )
			{
				if ( ( otherStream == lookupStream ) || ( otherStream == indexedStream ) )
				{
					continue;
				}

				String[] otherKeyProps = queryGraph.GetKeyProperties( otherStream, indexedStream );
				String[] otherIndexProps = queryGraph.GetIndexProperties( otherStream, indexedStream );
				int otherPropertyNum = -1;

				if ( otherIndexProps == null )
				{
					continue;
				}

				for ( int i = 0 ; i < otherIndexProps.Length ; i++ )
				{
					if ( otherIndexProps[i].Equals( indexProp ) )
					{
						otherPropertyNum = i;
						break;
					}
				}

				if ( otherPropertyNum != -1 )
				{
					bool added = queryGraph.Add( lookupStream, keyProp, otherStream, otherKeyProps[otherPropertyNum] );
					if ( added )
					{
						addedEquivalency = true;
					}
				}
			}

			return addedEquivalency;
		}

		/// <summary> Property lists stored as a value for each stream-to-stream relationship.</summary>
		public class GraphValue
		{
			private IList<String> propertiesLeft;
			private IList<String> propertiesRight;

			/// <summary> Ctor.</summary>
			public GraphValue()
			{
				propertiesLeft = new List<String>();
				propertiesRight = new List<String>();
			}

			/// <summary> Add key and index property.</summary>
			/// <param name="keyProperty">key property
			/// </param>
			/// <param name="indexProperty">index property
			/// </param>
			/// <returns> true if added and either property did not exist, false if either already existed
			/// </returns>
			public virtual bool Add( String keyProperty, String indexProperty )
			{
				if ( propertiesLeft.Contains( keyProperty ) )
				{
					return false;
				}
				if ( propertiesRight.Contains( indexProperty ) )
				{
					return false;
				}
				propertiesLeft.Add( keyProperty );
				propertiesRight.Add( indexProperty );
				return true;
			}

			/// <summary> Returns property names for left stream.</summary>
			/// <returns> property names
			/// </returns>
			public IList<String> PropertiesLeft
			{
				get { return propertiesLeft; }
			}

			/// <summary> Returns property names for right stream.</summary>
			/// <returns> property names
			/// </returns>
			public IList<String> PropertiesRight
			{
				get { return propertiesRight; }
			}

            /// <summary>
            /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
            /// </summary>
            /// <returns>
            /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
            /// </returns>
			public override String ToString()
			{
				return
					"GraphValue " +
					" propertiesLeft=" + CollectionHelper.Render( propertiesLeft ) + 
					" propertiesRight=" + CollectionHelper.Render( propertiesRight );
			}
		}

		/// <summary>
		/// Key consisting of 2 integer stream numbers.
		/// </summary>

		public class GraphKey
		{
			private UniformPair<Int32> streams;

			/// <summary> Ctor.</summary>
			/// <param name="streamOne">from stream
			/// </param>
			/// <param name="streamTwo">to stream
			/// </param>
			public GraphKey( int streamOne, int streamTwo )
			{
				if ( streamOne > streamTwo )
				{
					int temp = streamTwo;
					streamTwo = streamOne;
					streamOne = temp;
				}

				streams = new UniformPair<Int32>( streamOne, streamTwo );
			}

            /// <summary>
            /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
            /// </summary>
            /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
            /// <returns>
            /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
            /// </returns>
			public override bool Equals( Object obj )
			{
				if ( this == obj )
				{
					return true;
				}

				if ( !( obj is GraphKey ) )
				{
					return false;
				}

				GraphKey other = (GraphKey) obj;
				return other.streams.Equals( this.streams );
			}

            /// <summary>
            /// Serves as a hash function for a particular type.
            /// </summary>
            /// <returns>
            /// A hash code for the current <see cref="T:System.Object"></see>.
            /// </returns>
			public override int GetHashCode()
			{
				return streams.GetHashCode();
			}

            /// <summary>
            /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
            /// </summary>
            /// <returns>
            /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
            /// </returns>
			public override String ToString()
			{
				return "GraphKey " + streams.First + " and " + streams.Second;
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
			StringWriter writer = new StringWriter();

			int count = 0;
			foreach ( KeyValuePair<GraphKey, GraphValue> entry in streamJoinMap )
			{
				count++;
				writer.WriteLine( "Entry " + count + ": key=" + entry.Key );
				writer.WriteLine( "  value=" + entry.Value );
			}

			return writer.ToString();
		}
	}
}
