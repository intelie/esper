using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.view
{
	
	/// <summary> Encapsulates the information required to specify a object construction.
	/// A object construction specification can be equal to another specification. This information is
	/// important to determine reuse of any object.
	/// </summary>

    public sealed class ViewSpec
    {
        private readonly String objectNamespace;
        private readonly String objectName;
        private readonly IList<Object> objectParameters;

        /**
         * Constructor.
         * @param namespace if the namespace the object is in
         * @param objectName is the name of the object
         * @param objectParameters is a list of PrimitiveValue instances representing the object parameters
         */
        public ViewSpec(String nspace, String objectName, IList<Object> objectParameters)
        {
            this.objectNamespace = nspace;
            this.objectName = objectName;
            this.objectParameters = objectParameters;
        }

        /**
         * Returns namespace for view object.
         * @return namespace
         */
        public String ObjectNamespace
        {
            get { return objectNamespace; }
        }

        /**
         * Returns the object name.
         * @return object name
         */
        public String ObjectName
        {
            get { return objectName; }
        }

        /**
         * Returns the list of object parameters.
         * @return list of PrimitiveValue representing object parameters
         */
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
        
		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
    }
}
