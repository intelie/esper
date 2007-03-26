using System;
using System.Collections.Generic;

using net.esper.util;

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

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="namespaces">the namespaces that will be used to resolve partial class names</param>

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

        /// <summary>
        /// Gets the Type object for the type name. If the type
        /// name is incomplete, uses the imported namespace names
        /// to attempt to resolve the name.
        /// </summary>
        /// <param name="className">the name of the type to resolve</param>
        /// <returns>the Type object for this class name</returns>
        public virtual Type ResolveClass(String className)
        {
            // Attempt to retrieve the class with the name as-is
            try
            {
                return TypeHelper.ResolveType(className);
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
                    return TypeHelper.ResolveType(prefixedClassName);
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
