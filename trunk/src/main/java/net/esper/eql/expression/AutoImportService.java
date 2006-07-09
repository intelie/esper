package net.esper.eql.expression;

public interface AutoImportService 
{

	/**
	 * Get the imports in the same order in which they are attempted.
	 * @return an array containing the imports in the order
	 * they will be attempted in resolving a class name
	 */
	public String[] getImports();

	/**
	 * Set the imports. The imports will be searched in the same order as given.
	 * @param imports - the package and class names that will be used to resolve partial class names
	 */
	public void setImports(String[] imports);

	/**
	 * Gets the Class object for the class name. If the class 
	 * name is incomplete, uses the imported class and package 
	 * names to attempt to resolve the name.
	 * @param className - the name of the class to resolve
	 * @return - the Class object for this class name
	 * @throws ClassNotFoundException
	 */
	public abstract Class resolveClass(String className)
			throws ClassNotFoundException;

}