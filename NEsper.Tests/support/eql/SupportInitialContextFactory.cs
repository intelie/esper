using System;
using System.DirectoryServices;

namespace net.esper.support.eql
{
	public class SupportInitialContextFactory : InitialContextFactory
	{
		private static Map < String, Object > contextEntries = new HashMap < String, Object >();
		
		public static void addContextEntry(String name, System.Object value_Renamed)
		{
			contextEntries.Put(name, value_Renamed);
		}
		
		public System.DirectoryServices.DirectoryEntry getInitialContext(Hashtable < ?, ? > environment)
		{
			return new SupportContext(contextEntries);
		}
		
		public class SupportContext : DirectoryEntry
    {
        private Map<String, Object> contextEntries;

        public SupportContext(Map<String, Object> contextEntries)
        {
            this.contextEntries = contextEntries;
        }

        public Object lookup(String name)
        {
            if (!contextEntries.containsKey(name))
            {
                throw new NamingException("Name '" + name + "' not found");
            }
            return contextEntries.get(name);
        }
    }
	}
}
