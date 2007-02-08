using System;
using EventPropertyGetter = net.esper.events.EventPropertyGetter;
using EventBean = net.esper.events.EventBean;
using PropertyAccessException = net.esper.events.PropertyAccessException;
namespace net.esper.events.property
{
	
	/// <summary> Getter for an array property backed by a field, identified by a given index, using vanilla reflection.</summary>
	public class ArrayFieldPropertyGetter : EventPropertyGetter
	{
		private readonly System.Reflection.FieldInfo field;
		private readonly int index;
		
		/// <summary> Constructor.</summary>
		/// <param name="field">is the field to use to retrieve a value from the object
		/// </param>
		/// <param name="index">is tge index within the array to get the property from
		/// </param>
		public ArrayFieldPropertyGetter(System.Reflection.FieldInfo field, int index)
		{
			this.index = index;
			this.field = field;
			
			if (index < 0)
			{
				throw new ArgumentException("Invalid negative index value");
			}
		}
		
		public Object GetValue(EventBean obj)
		{
			Object underlying = obj.Underlying;
			
			try
			{
				Object value = field.GetValue(underlying);
				if (((Array) value).Length <= index)
				{
					return null;
				}
				return ((Array) value).GetValue(index);
			}
			catch (InvalidCastException)
			{
				throw new PropertyAccessException("Mismatched getter instance to event bean type");
			}
			catch (UnauthorizedAccessException e)
			{
				throw new PropertyAccessException(e);
			}
			catch (ArgumentException e)
			{
				throw new PropertyAccessException(e);
			}
		}
		
		public override String ToString()
		{
			return "ArrayFieldPropertyGetter " + " field=" + field.ToString() + " index=" + index;
		}
	}
}