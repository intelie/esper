using System;
using System.Reflection;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.util
{
	/// <summary>
	/// Helper class to find and invoke a class constructors that matches the types
	/// of arguments supplied.
	/// </summary>
	
	public class ConstructorHelper
	{
		/// <summary>
        /// Find and invoke constructor matching the argument number and types returning
        /// an instance of given class.
		/// </summary>
		/// <param name="clazz">is the class of instance to construct</param>
		/// <param name="arguments">is the arguments for the constructor to match in number and type
		/// </param>

        public static Object invokeConstructor(Type clazz, Object[] arguments)
        {
            return Activator.CreateInstance(clazz, arguments, null);
        }
		
		private static readonly Log log = LogFactory.GetLog(typeof(ConstructorHelper));
	}
}
