using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.compat
{
    /// <summary>
    /// Linked list.
    /// </summary>
    /// <typeparam name="T"></typeparam>

    public sealed class ELinkedList<T> : C5.LinkedList<T>
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="ELinkedList&lt;T&gt;"/> class.
        /// </summary>
        public ELinkedList() {}
        /// <summary>
        /// Initializes a new instance of the <see cref="ELinkedList&lt;T&gt;"/> class.
        /// </summary>
        /// <param name="source">The source.</param>
        public ELinkedList(IEnumerable<T> source)
        {
            AddAll(source);
        }

        /// <summary>
        /// Adds the item at the front of the list.
        /// </summary>
        /// <param name="item">The item.</param>
        public void AddFirst( T item )
        {
        	Push( item ) ;
        }
    }
}
