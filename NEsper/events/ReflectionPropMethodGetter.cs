using System;
using System.Reflection;

namespace net.esper.events
{
	/// <summary>
    /// Property getter for methods using Java's vanilla reflection.
    /// </summary>
	
    public sealed class ReflectionPropMethodGetter : EventPropertyGetter
	{
		private readonly MethodInfo method;
		
		/// <summary> Constructor.</summary>
		/// <param name="method">is the regular reflection method to use to obtain values for a field.
		/// </param>
		public ReflectionPropMethodGetter(MethodInfo method)
		{
			this.method = method;
		}

        public Object GetValue(EventBean obj)
        {
            Object underlying = obj.Underlying;

            try
            {
                return method.Invoke(underlying, (Object[])null);
            }
            catch (ArgumentException)
            {
                throw new PropertyAccessException("Mismatched getter instance to event bean type");
            }
            catch (UnauthorizedAccessException e)
            {
                throw new PropertyAccessException(e);
            }
            catch (TargetInvocationException e)
            {
                throw new PropertyAccessException(e);
            }
            catch (TargetException e)
            {
                throw new PropertyAccessException(e);
            }
            catch (Exception e)
            {
                throw new PropertyAccessException(e);
            }
        }
		
		public override String ToString()
		{
			return "ReflectionPropMethodGetter " + "method=" + method.ToString();
		}
	}
}
