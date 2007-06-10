using System;
using System.Collections.Generic;
using System.DirectoryServices.ActiveDirectory;

namespace net.esper.core
{
	public class ActiveDirectory : Directory
	{
		/// <summary>
		/// Lookup an object by name.
		/// </summary>
		/// <param name="name"></param>
		/// <returns></returns>
		
		public Object Lookup(string name)
		{
			throw new NotImplementedException() ;
		}

		/// <summary>
		/// Bind an object to a name.  Throws an exception if
		/// the name is already bound.
		/// </summary>
		/// <param name="name"></param>
		/// <param name="obj"></param>
		
		public void Bind(string name, Object obj)
		{
			throw new NotImplementedException() ;
		}
		
		/// <summary>
		/// Bind an object to a name.  If the object is already
		/// bound, rebind it.
		/// </summary>
		/// <param name="name"></param>
		/// <param name="obj"></param>
		
		public void Rebind(string name, Object obj)
		{
			throw new NotImplementedException() ;
		}
		
		/// <summary>
		/// Unbind the object at the given name.
		/// </summary>
		/// <param name="name"></param>
		
		public void Unbind(string name) 
		{
			throw new NotImplementedException() ;
		}

		/// <summary>
		/// Rename the object at oldName with newName.
		/// </summary>
		/// <param name="oldName"></param>
		/// <param name="newName"></param>
		
	    public void Rename(String oldName, String newName)
	    {
			throw new NotImplementedException() ;
	    }
	    
	    /// <summary>
	    /// Enumerates the names bound in the named context.
	    /// </summary>
	    /// <param name="name"></param>
	    /// <returns></returns>
	    
	    public IEnumerator<string> List(string name)
	    {
			throw new NotImplementedException() ;
	    }
		
		public void Dispose()
		{
		}
	}
}
