using System;
using System.ComponentModel;

namespace net.esper.events.property
{
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
		/// <param name="component"></param>
		/// <returns></returns>
		
		abstract public Object GetValue(object component, object index) ;
	}
}
