using System;
using System.ComponentModel;

namespace net.esper.events.property
{
    /// <summary>
    /// A property descriptor that takes an index.
    /// </summary>

	abstract public class IndexedPropertyDescriptor : PropertyDescriptor
	{
		/// <summary>
		/// Constructor
		/// </summary>
		/// <param name="name"></param>
		
		protected IndexedPropertyDescriptor( String name ) :
			base( name, null )
		{
		}

        /// <summary>
        /// Call the accessor method
        /// </summary>
        /// <param name="component">The component.</param>
        /// <param name="index">The index.</param>
        /// <returns></returns>
		
		abstract public Object GetValue(object component, object index) ;
	}
}
