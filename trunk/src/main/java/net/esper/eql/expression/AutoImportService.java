package net.esper.eql.expression;

/**
 * Interface for a service that resolves a class name to Java Class instances.
 * <p>Implementations typically allow some sort of configuration on which Java packages
 * are automatically checked for presence of a class. 
 */
public interface AutoImportService 
{

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