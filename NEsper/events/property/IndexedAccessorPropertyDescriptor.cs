using System;
using System.ComponentModel;
using System.Reflection;

namespace net.esper.events.property
{
	/// <summary>
	/// Description of IndexedAccessorPropertyDescriptor.
	/// </summary>

    public class IndexedAccessorPropertyDescriptor : IndexedPropertyDescriptor
    {
        private MethodInfo accessorMethod;

        /// <summary>
        /// Indicates whether the value of this property should be
        /// persisted.
        /// </summary>
        /// <param name="component"></param>
        /// <returns></returns>

        public override bool ShouldSerializeValue(object component)
        {
            return false;
        }

        /// <summary>
        /// Indicates whether or not the descriptor is readonly
        /// </summary>

        public override bool IsReadOnly
        {
            get { return true; }
        }

        /// <summary>
        /// Gets the type of component this property is bound to
        /// </summary>

        public override Type ComponentType
        {
            get { return accessorMethod.DeclaringType; }
        }

        /// <summary>
        /// Gets the return type of the property
        /// </summary>

        public override Type PropertyType
        {
            get { return accessorMethod.ReturnType; }
        }

        /// <summary>
        /// Call the accessor method
        /// </summary>
        /// <param name="component">Object to be queries</param>
        /// <param name="index">Index of the query</param>
        /// <returns></returns>

        public override Object GetValue(object component, object index)
        {
        	try {
            	return accessorMethod.Invoke(component, new Object[] { index });
        	} catch( TargetException e ) {
        		throw new PropertyAccessException( e ) ;
        	}
        }

        /// <summary>
        /// Call the accessor method
        /// </summary>
        /// <param name="component"></param>
        /// <returns></returns>

        public override Object GetValue(object component)
        {
            return GetValue(component, null);
        }

        /// <summary>
        /// Sets the value of the property
        /// </summary>
        /// <param name="component"></param>
        /// <param name="value"></param>

        public override void SetValue(object component, object value)
        {
            throw new NotSupportedException();
        }

        /// <summary>
        /// Can not override values with the simple accessor model
        /// </summary>
        /// <param name="component"></param>
        /// <returns></returns>

        public override bool CanResetValue(object component)
        {
            return false;
        }

        /// <summary>
        /// Resets the value of the property
        /// </summary>
        /// <param name="component"></param>

        public override void ResetValue(object component)
        {
            throw new NotSupportedException();
        }

        /// <summary>
        /// Returns true if the objects are equal.
        /// </summary>
        /// <param name="obj"></param>
        /// <returns></returns>

        public override bool Equals(object obj)
        {
            IndexedAccessorPropertyDescriptor temp = obj as IndexedAccessorPropertyDescriptor;
            if (temp != null)
            {
                return
                    Object.Equals(this.Name, temp.Name) &&
                    Object.Equals(this.accessorMethod, temp.accessorMethod);
            }

            return false;
        }

        /// <summary>
        /// Returns a hahscode for the object.
        /// </summary>
        /// <returns></returns>

        public override int GetHashCode()
        {
            return Name.GetHashCode();
        }


        /// <summary>
        /// Constructor
        /// </summary>

        public IndexedAccessorPropertyDescriptor(String name, MethodInfo accessorMethod)
            : base(name)
        {
            this.accessorMethod = accessorMethod;
        }

        /// <summary>
        /// Constructor
        /// </summary>

        public IndexedAccessorPropertyDescriptor(MethodInfo accessorMethod)
            : base(accessorMethod.Name)
        {
            this.accessorMethod = accessorMethod;
        }
    }
}
