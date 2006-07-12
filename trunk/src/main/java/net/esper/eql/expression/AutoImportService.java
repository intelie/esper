package net.esper.eql.expression;

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