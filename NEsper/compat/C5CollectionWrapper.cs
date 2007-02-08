using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	public class C5CollectionWrapper<T> : ICollection<T>
	{
		private C5.ICollectionValue<T> m_subCollection;
		
		public C5CollectionWrapper( C5.ICollectionValue<T> subCollection )
		{
			m_subCollection = subCollection ;
		}
		
		public int Count {
			get { return m_subCollection.Count ; }
		}
		
		public bool IsReadOnly {
			get {
				return
					( m_subCollection is C5.ICollection<T> ) ?
					( m_subCollection as C5.ICollection<T> ).IsReadOnly :
					( true ) ;
			}
		}
		
		public void Add(T item)
		{
			if ( m_subCollection is C5.ICollection<T> ) {
				( m_subCollection as C5.ICollection<T> ).Add( item ) ;
			} else {
				throw new NotSupportedException() ;
			}
		}
		
		public void Clear()
		{
			if ( m_subCollection is C5.ICollection<T> ) {
				( m_subCollection as C5.ICollection<T> ).Clear() ;
			} else {
				throw new NotSupportedException() ;
			}
		}
		
		public bool Contains(T item)
		{
			if ( m_subCollection is C5.ICollection<T> ) {
				return ( m_subCollection as C5.ICollection<T> ).Contains( item ) ;
			} else {
				throw new NotSupportedException() ;
			}
		}
		
		public void CopyTo(T[] array, int arrayIndex)
		{
			m_subCollection.CopyTo( array, arrayIndex ) ;
		}
		
		public bool Remove(T item)
		{
			if ( m_subCollection is C5.ICollection<T> ) {
				return ( m_subCollection as C5.ICollection<T> ).Remove( item ) ;
			} else {
				throw new NotSupportedException() ;
			}
		}
		
		public IEnumerator<T> GetEnumerator()
		{
			return m_subCollection.GetEnumerator() ;
		}
		
		System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
		{
			return m_subCollection.GetEnumerator() ;
		}
	}
}
