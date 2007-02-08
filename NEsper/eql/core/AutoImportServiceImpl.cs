using System;
using System.Collections.Generic;

namespace net.esper.eql.core
{
    /// <summary>
    /// A service that allows users to refer to classes by partial names.
    /// </summary>

    public class AutoImportServiceImpl : AutoImportService
    {
        /// <summary>
        /// For testing, returns enumeration of namespaces.
        /// </summary>
        
        virtual public IList<String> Namespaces
        {
        	get { return namespaces; }
        }

        private IList<String> namespaces = new List<String>();

        /// <summary> Ctor.</summary>
        /// <param name="imports">- the package and class names that will be used to resolve partial class names
        /// </param>

        public AutoImportServiceImpl(IEnumerable<String> namespaces)
        {
            if (namespaces == null)
            {
                throw new NullReferenceException("Array of auto imports cannot be null");
            }

            foreach (String nspace in namespaces)
            {
                AddNamespace( nspace );
            }
        }

        public virtual Type ResolveClass(String className)
        {
            // Attempt to retrieve the class with the name as-is
            try
            {
                return Type.GetType(className);
            }
            catch (Exception)
            {
            }

            // Try all the namespaces
            foreach (String nspace in namespaces )
            {
                String prefixedClassName = String.Format( "{0}.{1}", nspace, className ) ;
                try
                {
                    return Type.GetType(prefixedClassName);
                }
                catch (Exception)
                {
                }
            }

            // No import worked, the class isn't resolved
            throw new Exception("Unknown class " + className);
        }

        /// <summary>
        /// Add a namespace to the auto-import list, for testing.
        /// </summary>
        /// <param name="nspace">The nspace.</param>

        public virtual void AddNamespace(String nspace)
        {
            namespaces.Add(nspace);
        }
    }
}
