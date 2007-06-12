///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Threading;

using net.esper.events;
using net.esper.util;

namespace net.esper.filter
{
	/// <summary>
	/// Each implementation of this abstract class represents an index of filter parameter constants supplied in filter
	/// parameters in filter specifications that feature the same event property and operator.
	/// <para>
	/// For example, a filter with a parameter of "count EQUALS 10" would be represented as index
	/// for a property named "count" and for a filter operator typed "EQUALS". The index
	/// would store a value of "10" in its internal structure.
	/// </para>
	/// <para>
	/// Implementations make sure that the type of the Object constant in get and put calls matches the event property type.
	/// </para>
	/// </summary>
	public abstract class FilterParamIndexPropBase : FilterParamIndexBase
	{
	    private readonly String propertyName;
	    private readonly EventPropertyGetter getter;
	    private readonly Type propertyBoxedType;

	    /// <summary>Constructor.</summary>
	    /// <param name="propertyName">
	    /// is the name of the event property the index goes against
	    /// </param>
	    /// <param name="filterOperator">is the type of comparison performed.</param>
	    /// <param name="eventType">is the event type the index will handle.</param>
	    public FilterParamIndexPropBase(String propertyName, FilterOperator filterOperator, EventType eventType)
	        : base(filterOperator)
	    {
	        this.propertyName = propertyName;
	        this.getter = eventType.GetGetter(propertyName);
	        this.propertyBoxedType = TypeHelper.GetBoxedType(eventType.GetPropertyType(propertyName));
	        if (getter == null)
	        {
	            throw new ArgumentException("Property named '" + propertyName + "' not valid for event type ");
	        }
	    }

	    /// <summary>
	    /// Returns the name of the property to get the value for to match against the values
	    /// contained in the index.
	    /// </summary>
	    /// <returns>event property name</returns>
	    public String PropertyName
	    {
            get { return propertyName; }
	    }

	    /// <summary>Returns getter for property.</summary>
	    /// <returns>property value getter</returns>
	    public EventPropertyGetter Getter
	    {
	        get { return getter; }
	    }

	    /// <summary>Returns boxed property type.</summary>
	    /// <returns>boxed property type</returns>
	    public Type PropertyBoxedType
	    {
            get { return propertyBoxedType; }
	    }

        /// <summary>
        /// Toes the string.
        /// </summary>
        /// <returns></returns>
	    public override String ToString()
	    {
	        return base.ToString() +
	               " propName=" + propertyName +
	               " propertyBoxedType=" + propertyBoxedType.Name;
	    }
	}
} // End of namespace
