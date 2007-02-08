using System;

namespace net.esper.eql.core
{
	/// <summary>
    /// Interface for a service that resolves a class name to type instances.
	/// Implementations typically allow some sort of configuration on which 
    /// namespaces are automatically checked for presence of a type.
	/// </summary>
	
    public interface AutoImportService
	{
        /// <summary>
        /// Gets the Type object for the type name. If the type
        /// name is incomplete, uses the imported namespace names
        /// to attempt to resolve the name.
        /// </summary>
        /// <param name="className">the name of the type to resolve</param>
        /// <returns>the Type object for this class name</returns>
		
        Type ResolveClass(String className);
	}
}