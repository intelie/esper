///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Text;

using net.esper.util;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Encapsulates the information required to specify an object identification and construction.
	/// <para>
	/// Abstract class for use with any object, such as views, pattern guards or pattern observers.
	/// </para>
	/// <para>
	/// A object construction specification can be equal to another specification. This information can be
	/// important to determine reuse of any object.
	/// </para>
	/// </summary>
	public abstract class ObjectSpec : MetaDefItem
	{
	    private readonly String objectNamespace;
	    private readonly String objectName;
	    private readonly IList<Object> objectParameters;

	    /// <summary>Constructor.</summary>
	    /// <param name="_namespace">if the namespace the object is in</param>
	    /// <param name="objectName">is the name of the object</param>
	    /// <param name="objectParameters">
	    /// is a list of values representing the object parameters
	    /// </param>
	    public ObjectSpec(String _namespace, String objectName, IList<Object> objectParameters)
	    {
	        this.objectNamespace = _namespace;
	        this.objectName = objectName;
	        this.objectParameters = objectParameters;
	    }

	    /// <summary>Returns namespace for view object.</summary>
	    /// <returns>namespace</returns>
	    public String ObjectNamespace
	    {
	        get { return objectNamespace; }
	    }

	    /// <summary>Returns the object name.</summary>
	    /// <returns>object name</returns>
	    public String ObjectName
	    {
	        get { return objectName; }
	    }

	    /// <summary>Returns the list of object parameters.</summary>
	    /// <returns>list of values representing object parameters</returns>
	    public IList<Object> ObjectParameters
	    {
	        get { return objectParameters; }
	    }

	    public override bool Equals(Object otherObject)
	    {
	        if (otherObject == this)
	        {
	            return true;
	        }

	        if (otherObject == null)
	        {
	            return false;
	        }

	        if (GetType() != otherObject.GetType())
	        {
	            return false;
	        }

	        ObjectSpec other = (ObjectSpec) otherObject;
	        if (this.objectName != other.objectName)
	        {
	            return false;
	        }

	        if (objectParameters.Count != other.objectParameters.Count)
	        {
	            return false;
	        }

	        // Compare object parameter by object parameter
	        int index = 0;
	        foreach (Object thisParam in objectParameters)
	        {
	            Object otherParam = other.objectParameters[index];
	            index++;

	            if (!(thisParam.Equals(otherParam)))
	            {
	                return false;
	            }
	        }

	        return true;
	    }

        /// <summary>
        /// Serves as a hash function for a particular type. <see cref="M:System.Object.GetHashCode"></see> is suitable for use in hashing algorithms and data structures like a hash table.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override int GetHashCode()
		{
			return
				(this.objectName.GetHashCode() * 31) +
				(this.objectNamespace.GetHashCode()) ;
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
	    public override String ToString()
	    {
	        StringBuilder buffer = new StringBuilder();
	        buffer.Append("objectName=" + objectName + "  objectParameters=(");
	        char delimiter = ' ';

	        if (objectParameters != null)
	        {
	            foreach (Object param in objectParameters)
	            {
	                buffer.Append(delimiter + param.ToString());
	                delimiter = ',';
	            }
	        }

	        buffer.Append(')');

	        return buffer.ToString();
	    }
	}
} // End of namespace
