using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace net.esper.compat
{
    /// <summary>
    /// Provides additional functions that are useful when operating on
    /// collections.
    /// </summary>

	public class CollectionHelper
	{
        /// <summary>
        /// Compares two collections of objects.  The objects must share the same generic
        /// parameter, but can be of different collections.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="baseObj"></param>
        /// <param name="compObj"></param>

        public static bool AreEqual<T>(ICollection<T> baseObj, ICollection<T> compObj)
        {
            if (baseObj.Count != compObj.Count)
            {
                return false;
            }

            IEnumerator<T> baseEnum = baseObj.GetEnumerator();
            IEnumerator<T> compEnum = compObj.GetEnumerator();

            return AreEqual(baseEnum, compEnum);
        }

        /// <summary>
        /// Compares two collections of objects.  The objects must share the same generic
        /// parameter, but can be of different collections.
        /// </summary>
        /// <param name="baseEnum">The base enumerator.</param>
        /// <param name="compEnum">The comp enumerator.</param>
        /// <returns></returns>
        /// <typeparam name="T"></typeparam>

        public static bool AreEqual<T>(IEnumerator<T> baseEnum, IEnumerator<T> compEnum)
        {
            while (true)
            {
                bool baseTest = baseEnum.MoveNext();
                bool compTest = compEnum.MoveNext();

                if (baseTest && compTest)
                {
                    if (!Object.Equals(baseEnum.Current, compEnum.Current))
                    {
                        return false;
                    }
                }
                else if (!baseTest && !compTest)
                {
                    return true;
                }
                else
                {
                    // Both baseTest and compTest should both be returning
                    // false at this point.  Failure to do so indicates that
                    // one enumerator is returning more results than the
                    // other.

                    return false;
                }
            }
        }

        /// <summary>
        /// Primitive reversal of a collection
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="collection"></param>

        public static void Reverse<T>(ICollection<T> collection)
        {
            List<T> tempList = collection as List<T>;
            if (tempList != null)
            {
                tempList.Reverse();
                return;
            }

            tempList = new List<T>();
            tempList.AddRange(collection);
            tempList.Reverse();

            collection.Clear();

            foreach (T item in tempList)
            {
                collection.Add(item);
            }
        }

		/// <summary>
		/// Tries to ensure that we are using a list that supports O(1)
		/// random access lookups.  In short, the requestor wants to
		/// ensure that they do not pay a penalty for requesting data
		/// anywhere in the list.  This can be a problem if the list is
		/// represented as a linkedlist or other such structure where
		/// lookup time can be proportional to length.
		/// </summary>
		/// <param name="baseList"></param>
		/// <returns></returns>
		
		public static IList<T> RandomAccessList<T>( IList<T> baseList )
		{
			if ( baseList is ELinkedList<T> )
			{
				return new List<T>( baseList ) ;
			}
			
			// We don't know about all of the other types of lists
			// out there.  Rather than trying to assume we can create
			// a restrictive list, I've opted to let anything pass
			// that is not transformed above.  Any performance issues
			// related to a poor list choice will quickly be exposed
			// and can be rectified here.
			
			return baseList ;
		}
		
		/// <summary>
		/// Renders an enumerable source
		/// </summary>
		/// <param name="source"></param>
		/// <returns></returns>
		
		public static String Render( IEnumerable source )
		{
			string fieldDelimiter = String.Empty ;
			
			StringBuilder builder = new StringBuilder() ;
			builder.Append( '[' ) ;
			
			IEnumerator sourceEnum = source.GetEnumerator() ;
			while( sourceEnum.MoveNext() )
			{
				builder.Append( fieldDelimiter ) ;
				builder.Append( Convert.ToString( sourceEnum.Current ) ) ;
				fieldDelimiter = ", " ;
			}
			
			builder.Append( ']' ) ;
			return builder.ToString() ;
		}

		/// <summary>
		/// Converts all of the items in source to an array.
		/// </summary>
		/// <param name="source"></param>
		/// <returns></returns>
		
		public static T[] ToArray<T>( ICollection<T> source )
		{
			if ( source is List<T> ) {
				return ((List<T>) source).ToArray() ;
			}
			
			T[] array = new T[source.Count] ;
			source.CopyTo( array, 0 ) ;
			return array ;
		}
		
		/// <summary>
		/// Add all of one enumerable set into a collection.
		/// </summary>
		/// <param name="targetCollection"></param>
		/// <param name="sourceEnum"></param>
		
		public static void AddAll<T>( ICollection<T> targetCollection, IEnumerable<T> sourceEnum )
		{
			foreach( T item in sourceEnum )
			{
				targetCollection.Add( item );
			}
		}

        /// <summary>
        /// Returns true if all items in the itemEnum are contained in referenceCollection
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="referenceCollection"></param>
        /// <param name="itemEnum"></param>
        /// <returns></returns>

        public static bool ContainsAll<T>(ICollection<T> referenceCollection, IEnumerable<T> itemEnum)
        {
            foreach (T item in itemEnum)
            {
                if (!referenceCollection.Contains(item))
                {
                    return false;
                }
            }

            return true;
        }

        /// <summary>
        /// Removes all items in itemEnum from the targetCollection
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="targetCollection"></param>
        /// <param name="itemEnum"></param>

        public static void RemoveAll<T>(ICollection<T> targetCollection, IEnumerable<T> itemEnum)
        {
            foreach (T item in itemEnum)
            {
                while (targetCollection.Remove(item))
                    ;
            }
        }

        /// <summary>
        /// Returns the first item returned by the enumerator of the
        /// collection.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="sourceCollection"></param>
        /// <returns></returns>

		public static T First<T>( IEnumerable<T> sourceCollection )
		{
			IEnumerator<T> enumObj = sourceCollection.GetEnumerator();
			enumObj.MoveNext();
			return enumObj.Current;
		}

        /// <summary>
        /// Advances the enumerator and returns the next item.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="enumObj"></param>
        /// <returns></returns>

        public static T Next<T>(IEnumerator<T> enumObj)
        {
            enumObj.MoveNext();
            return enumObj.Current;
        }
        
        /// <summary>
        /// Shuffles the list.
        /// </summary>
        /// <param name="list"></param>
        
        public static void Shuffle<T>( IList<T> list )
        {
        	Shuffle( list, list.Count * 2, new Random() ) ;
        }
        
        /// <summary>
        /// Shuffles the list.  User supplies the randomizer.
        /// </summary>
        /// <param name="list"></param>
        /// <param name="random"></param>
        
        public static void Shuffle<T>( IList<T> list, Random random )
        {
        	Shuffle( list, list.Count * 2, random ) ;
        }
        
        /// <summary>
        /// Shuffles the list.  User supplies the randomizer.  Performs
        /// at least iteration swaps.
        /// </summary>
        /// <param name="list"></param>
        /// <param name="iterations"></param>
        /// <param name="random"></param>
        
        public static void Shuffle<T>( IList<T> list, int iterations, Random random )
        {
        	int count = list.Count ;
        	
        	for( int ii = iterations ; ii <= 0 ; ii-- )
        	{
        		int index1 = random.Next( count ) ;
        		int index2 = random.Next( count ) ;
        		T temp = list[index1] ;
        		list[index1] = list[index2] ;
        		list[index2] = temp ;
        	}        	
        }
    }
}
