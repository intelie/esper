using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.events.property
{
	/// <summary>
    /// Interface for an introspector that generates a list of event property descriptors
	/// given a clazz.
	/// </summary>
	
    public interface PropertyListBuilder
	{
		/// <summary>
		/// Introspect the type and deterime exposed event properties.
		/// </summary>
		/// <param name="type">type to introspect</param>
		/// <returns>list of event property descriptors</returns>

        IList<EventPropertyDescriptor> AssessProperties(Type type);
	}
}
