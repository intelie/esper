using System;

namespace net.esper.util
{
	/// <summary>
    /// Factory for an instance of any type. Employs Class.newInstance to instantiate.
    /// </summary>
	
    public class GenericFactory<T>
	{
		private Type clazz;
		
		/// <summary> Ctor.</summary>
		/// <param name="clazz">Class of which instace must be created
		/// </param>
		
        public GenericFactory(Type clazz)
        {
            this.clazz = clazz;
        }
		
		/// <summary> Create instance of class.</summary>
		/// <returns> instance
		/// </returns>
		
        public virtual T Create()
		{
			return (T) Activator.CreateInstance( this.clazz );
		}
	}
}