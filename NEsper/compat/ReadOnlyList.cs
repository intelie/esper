using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	public class ReadOnlyList<T> : IList<T>
	{
		private IList<T> m_parent;
		
		public ReadOnlyList( IList<T> parentList )
		{
			m_parent = parentList;
		}
		
		public T this[int index]
		{
			get { return m_parent[index] ; }
			set { throw new NotSupportedException() ; }
		}
		
		public int Count {
			get { return m_parent.Count; }
		}
		
		public bool IsReadOnly {
			get { return true ; }
		}
		
		public int IndexOf(T item)
		{
			return m_parent.IndexOf( item ) ;
		}
		
		public void Insert(int index, T item)
		{
			throw new NotSupportedException() ;
		}
		
		public void RemoveAt(int index)
		{
			throw new NotSupportedException() ;
		}
		
		public void Add(T item)
		{
			throw new NotSupportedException() ;
		}
		
		public void Clear()
		{
			throw new NotSupportedException() ;
		}
		
		public bool Contains(T item)
		{
			return m_parent.Contains( item ) ;
		}
		
		public void CopyTo(T[] array, int arrayIndex)
		{
			m_parent.CopyTo( array, arrayIndex ) ;
		}
		
		public bool Remove(T item)
		{
			throw new NotSupportedException() ;
		}
		
		public IEnumerator<T> GetEnumerator()
		{
			return m_parent.GetEnumerator() ;
		}
		
		System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
		{
			return m_parent.GetEnumerator() ;
		}
	}
}
