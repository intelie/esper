using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.compat
{
    public sealed class ELinkedList<T> : C5.LinkedList<T>
    {
        public ELinkedList() {}
        public ELinkedList(IEnumerable<T> source)
        {
            AddAll(source);
        }
        
        public void AddFirst( T item )
        {
        	Push( item ) ;
        }

		/// <summary>
		/// Converts the list to an array
		/// </summary>
		/// <returns></returns>
		
//		public T[] ToArray()
//		{
//			T[] array = new T[this.Count] ;
//			CopyTo( array, 0 );
//			return array;
//		}
    }
}
