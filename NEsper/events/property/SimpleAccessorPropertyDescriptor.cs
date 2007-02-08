using System;
using System.ComponentModel;
using System.Reflection;

namespace net.esper.events.property
{
	public class SimpleAccessorPropertyDescriptor : PropertyDescriptor
	{
		private MethodInfo accessorMethod ;

		/// <summary>
		/// Indicates whether the value of this property should be
		/// persisted.
		/// </summary>
		/// <param name="component"></param>
		/// <returns></returns>
		
		public override bool ShouldSerializeValue(object component)
		{
			return false ;
		}
		
		/// <summary>
		/// Indicates whether or not the descriptor is readonly
		/// </summary>

		public override bool IsReadOnly
		{
			get { return true ; }
		}
		
		/// <summary>
		/// Gets the type of component this property is bound to
		/// </summary>
		
		public override Type ComponentType
		{
			get { return accessorMethod.DeclaringType ; }
		}
		
		/// <summary>
		/// Gets the return type of the property
		/// </summary>
		
		public override Type PropertyType 
		{
			get { return accessorMethod.ReturnType ; }
		}
		
		/// <summary>
		/// Call the accessor method
		/// </summary>
		/// <param name="component"></param>
		/// <returns></returns>
		
		public override Object GetValue(object component)
		{
			return accessorMethod.Invoke( component, null ) ;
		}
		
		/// <summary>
		/// Sets the value of the property
		/// </summary>
		/// <param name="component"></param>
		/// <param name="value"></param>
		
		public override void SetValue(object component, object value)
		{
			throw new NotSupportedException() ;
		}
		
		/// <summary>
		/// Can not override values with the simple accessor model
		/// </summary>
		/// <param name="component"></param>
		/// <returns></returns>
		
		public override bool CanResetValue(object component)
		{
			return false ;
		}

		/// <summary>
		/// Resets the value of the property
		/// </summary>
		/// <param name="component"></param>
		
		public override void ResetValue(object component)
		{
			throw new NotSupportedException() ;
		}
		
		/// <summary>
		/// Constructor
		/// </summary>
		
		public SimpleAccessorPropertyDescriptor( String name, MethodInfo accessorMethod ) :
			base( name, null )
		{
			this.accessorMethod = accessorMethod;
		}

        public SimpleAccessorPropertyDescriptor(MethodInfo accessorMethod)
            : base(accessorMethod.Name, null)
        {
            this.accessorMethod = accessorMethod;
        }
	}
}
