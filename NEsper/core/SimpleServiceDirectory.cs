using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.core
{
    /// <summary>
    /// Simple implementation of the directory.
    /// </summary>

	public class SimpleServiceDirectory : Directory
	{
		private EDictionary<string,object> m_dataTable ;
        private MonitorLock m_dataLock;

        /// <summary>
        /// Initializes a new instance of the <see cref="SimpleServiceDirectory"/> class.
        /// </summary>
		public SimpleServiceDirectory()
		{
			m_dataTable = new HashDictionary<string,object>() ;
            m_dataLock = new MonitorLock();
		}

        /// <summary>
        /// Lookup an object by name.
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
		public object Lookup(string name)
		{
			using(m_dataLock.Acquire())
			{
				return m_dataTable.Fetch( name ) ;
			}
		}

        /// <summary>
        /// Bind an object to a name.  Throws an exception if
        /// the name is already bound.
        /// </summary>
        /// <param name="name"></param>
        /// <param name="obj"></param>
		public void Bind(string name, object obj)
		{
			using(m_dataLock.Acquire())
			{
				if ( m_dataTable.ContainsKey( name ) )
				{
					throw new DirectoryException( "Key '" + name + "' was already bound" ) ;
				}
				
				m_dataTable[name] = obj ;
			}
		}

        /// <summary>
        /// Bind an object to a name.  If the object is already
        /// bound, rebind it.
        /// </summary>
        /// <param name="name"></param>
        /// <param name="obj"></param>
		public void Rebind(string name, object obj)
		{
			using(m_dataLock.Acquire())
			{
				m_dataTable[name] = obj;
			}
		}

        /// <summary>
        /// Unbind the object at the given name.
        /// </summary>
        /// <param name="name"></param>
		public void Unbind(string name)
		{
			using(m_dataLock.Acquire())
			{
				m_dataTable.Remove( name ) ;
			}
		}

        /// <summary>
        /// Rename the object at oldName with newName.
        /// </summary>
        /// <param name="oldName"></param>
        /// <param name="newName"></param>
		public void Rename(string oldName, string newName)
		{
			using(m_dataLock.Acquire())
			{
				object tempObj = m_dataTable.Fetch( oldName );
				if ( tempObj == null )
				{
					throw new DirectoryException( "Key '" + oldName + "' was not found" ) ;
				}
				
				if ( m_dataTable.ContainsKey( newName ) )
				{
					throw new DirectoryException( "Key '" + newName + "' was already bound" ) ;
				}
				
				m_dataTable.Remove(oldName) ;
				m_dataTable[newName] = tempObj;				
			}
		}

        /// <summary>
        /// Enumerates the names bound in the named context.
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
		public IEnumerator<string> List(string name)
		{
		    return m_dataTable.Keys.GetEnumerator();
		}

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
		public void Dispose()
		{
			m_dataTable = null ;
		}
	}
}
