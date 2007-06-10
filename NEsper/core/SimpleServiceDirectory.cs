using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.core
{
	public class SimpleServiceDirectory : Directory
	{
		private EDictionary<string,object> m_dataTable ;
		
		public SimpleServiceDirectory()
		{
			m_dataTable = new EHashDictionary<string,object>() ;
		}
		
		public object Lookup(string name)
		{
			lock( m_dataTable )
			{
				return m_dataTable.Fetch( name ) ;
			}
		}
		
		public void Bind(string name, object obj)
		{
			lock( m_dataTable )
			{
				if ( m_dataTable.ContainsKey( name ) )
				{
					throw new DirectoryException( "Key '" + name + "' was already bound" ) ;
				}
				
				m_dataTable[name] = obj ;
			}
		}
		
		public void Rebind(string name, object obj)
		{
			lock( m_dataTable )
			{
				m_dataTable[name] = obj;
			}
		}
		
		public void Unbind(string name)
		{
			lock( m_dataTable )
			{
				m_dataTable.Remove( name ) ;
			}
		}
		
		public void Rename(string oldName, string newName)
		{
			lock( m_dataTable )
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
		
		public IEnumerator<string> List(string name)
		{
			return m_dataTable.Keys ;
		}
		
		public void Dispose()
		{
			m_dataTable = null ;
		}
	}
}
