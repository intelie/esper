using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.view
{
	/// <summary>
    /// Encapsulates the information required to specify a object construction.
	/// A object construction specification can be equal to another specification.
    /// This information is important to determine reuse of any object.
	/// </summary>

    public sealed class ViewSpec
    {
        private readonly String objectNamespace;
        private readonly String objectName;
        private readonly IList<Object> objectParameters;

        /// <summary>
        /// Initializes a new instance of the <see cref="ViewSpec"/> class.
        /// </summary>
        /// <param name="nspace">The nspace.</param>
        /// <param name="objectName">is the name of the object</param>
        /// <param name="objectParameters">is a list of PrimitiveValue instances representing the object parameters</param>

        public ViewSpec(String nspace, String objectName, IList<Object> objectParameters)
        {
            this.objectNamespace = nspace;
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
        /// <returns>list of PrimitiveValue representing object parameters</returns>

        public IList<Object> ObjectParameters
        {
            get { return objectParameters; }
        }

        /// <summary>
        /// Returns true if the objects are equal.
        /// </summary>
        /// <param name="otherObject">The other object.</param>
        /// <returns></returns>
        
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

            if ( GetType() != otherObject.GetType() )
            {
                return false;
            }

            ViewSpec other = (ViewSpec)otherObject;
            if (!(this.objectName).Equals(other.objectName))
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

            buffer.Append(")");

            return buffer.ToString();
        }

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
    }
}
