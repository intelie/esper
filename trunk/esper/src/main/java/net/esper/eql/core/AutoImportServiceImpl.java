package net.esper.eql.core;

import net.esper.eql.core.AutoImportService;

import java.util.ArrayList;
import java.util.List;

/** 
 * A service that allows users to refer to classes by
 * partial names. Users import either package names
 * (e.g. "java.lang.*) or class names (e.g. "java.lang.String")
 * and afterwards the service uses those imports to resolve
 * partial names (e.g. "String").
 */
public class AutoImportServiceImpl implements AutoImportService
{
	private final List<String> imports = new ArrayList<String>();
	
	/**
	 * Ctor.
	 * @param imports - the package and class names that will be used to resolve partial class names
	 */
	public AutoImportServiceImpl(String[] imports)
	{
		if(imports == null)
		{
			throw new NullPointerException("Array of auto imports cannot be null");
		}

		for(String importName : imports)
		{
			addImport(importName);
		}
	}

    /**
     * Ctor.
     */
    public AutoImportServiceImpl()
    {
    }

	@SuppressWarnings({"StringContatenationInLoop"})
    public Class resolveClass(String className)
	throws ClassNotFoundException
	{
		// Attempt to retrieve the class with the name as-is
		try
		{
			return Class.forName(className);
		}
		catch(ClassNotFoundException e){}

		// Try all the imports
		for(String importName : imports)
		{
			boolean isClassName = isClassName(importName);

			// Import is a class name
			if(isClassName)
			{
				if(importName.endsWith(className))
				{
					return Class.forName(importName);
				}
			}
			else
			{
				// Import is a package name
				String prefixedClassName = getPackageName(importName) + '.' + className;
				try
				{
					return Class.forName(prefixedClassName);
				}
				catch(ClassNotFoundException e){}
			}
		}

		// No import worked, the class isn't resolved
		throw new ClassNotFoundException("Unknown class " + className);
	}

    /**
     * For testing, returns imports.
     * @return returns auto-import list as array
     */
    protected String[] getImports()
	{
		return imports.toArray(new String[0]);
	}

    /**
     * Add a package to the auto-import list, for testing.
     * @param importName to add
     */
    protected void addImport(String importName)
	{
		if(!isClassName(importName) && !isPackageName(importName))
		{
			throw new IllegalArgumentException("Invalid import name " + importName);
		}
		imports.add(importName);
	}
	
	private static boolean isClassName(String importName)
	{
		String classNameRegEx = "(\\w+\\.)*\\w+";
		return importName.matches(classNameRegEx);
	}
	
	private static boolean isPackageName(String importName)
	{
		String classNameRegEx = "(\\w+\\.)+\\*";
		return importName.matches(classNameRegEx);
	}
	
	// Strip off the final ".*"
	private static String getPackageName(String importName)
	{
		return importName.substring(0, importName.length() - 2);
	}
}
