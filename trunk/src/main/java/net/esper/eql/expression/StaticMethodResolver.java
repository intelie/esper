package net.esper.eql.expression;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A helper class used for retrieving static methods objects. It
 * provides two points of added functionality over the standard 
 * java.lang.reflect mechanism of retrieving methods. First, 
 * class names can be partial, and a user-specified list of 
 * package names can be used to search for the class. Second, 
 * invocation parameter types don't have to match the declaration
 * parameter types exactly when the standard java conversion
 * mechanisms (autoboxing, widening conversions etc.) will make 
 * the invocation valid. Preference is given to those methods that
 * require the fewest parameter conversions.
 */
public class StaticMethodResolver 
{
	private final List<String> classPath = new ArrayList<String>();
	private static final Map<Class, Set<Class>> validTypeConversions = new HashMap<Class, Set<Class>>();
	
	// Initialize the map of valid parameter type conversions
	static 
	{
		Set<Class> booleanTypes = new HashSet<Class>();
		booleanTypes.add(boolean.class);
		booleanTypes.add(Boolean.class);
		StaticMethodResolver.validTypeConversions.put(boolean.class, booleanTypes);
		StaticMethodResolver.validTypeConversions.put(Boolean.class, booleanTypes);
		
		Set<Class> charTypes = new HashSet<Class>();
		charTypes.add(char.class);
		charTypes.add(Character.class);		
		StaticMethodResolver.validTypeConversions.put(char.class, charTypes);
		StaticMethodResolver.validTypeConversions.put(Character.class, charTypes);
		
		Set<Class> byteTypes = new HashSet<Class>();
		byteTypes.add(byte.class);
		byteTypes.add(Byte.class);
		StaticMethodResolver.validTypeConversions.put(byte.class, byteTypes);
		StaticMethodResolver.validTypeConversions.put(Byte.class, byteTypes);
		
		Set<Class> shortTypes = new HashSet<Class>();
		shortTypes.add(short.class);
		shortTypes.add(Short.class);
		shortTypes.addAll(byteTypes);
		StaticMethodResolver.validTypeConversions.put(short.class, shortTypes);
		StaticMethodResolver.validTypeConversions.put(Short.class, shortTypes);
	
		Set<Class> intTypes = new HashSet<Class>();
		intTypes.add(int.class);
		intTypes.add(Integer.class);
		intTypes.addAll(shortTypes);
		StaticMethodResolver.validTypeConversions.put(int.class, intTypes);
		StaticMethodResolver.validTypeConversions.put(Integer.class, intTypes);
		
		Set<Class> longTypes = new HashSet<Class>();
		longTypes.add(long.class);
		longTypes.add(Long.class);
		longTypes.addAll(intTypes);
		StaticMethodResolver.validTypeConversions.put(long.class, longTypes);
		StaticMethodResolver.validTypeConversions.put(Long.class, longTypes);
		
		Set<Class> floatTypes = new HashSet<Class>();
		floatTypes.add(float.class);
		floatTypes.add(Float.class);
		floatTypes.addAll(longTypes);
		StaticMethodResolver.validTypeConversions.put(float.class, floatTypes);
		StaticMethodResolver.validTypeConversions.put(Float.class, floatTypes);
		
		Set<Class> doubleTypes = new HashSet<Class>();
		doubleTypes.add(double.class);
		doubleTypes.add(Double.class);
		doubleTypes.addAll(floatTypes);
		StaticMethodResolver.validTypeConversions.put(double.class, doubleTypes);
		StaticMethodResolver.validTypeConversions.put(Double.class, doubleTypes);
	}

	/**
	 * Ctor.
	 * @param classPath - the names of the library packages that 
	 * will be searched to try to resolve an incomplete class name
	 */
	public StaticMethodResolver(String[] classPath)
	{
		if(classPath == null)
		{
			throw new NullPointerException();
		}
		this.classPath.addAll(Arrays.asList(classPath));
	}
	
	/**
	 * Ctor.
	 * The classpath for resolving partial class names 
	 * will be initialized with "java.lang"
	 */
	public StaticMethodResolver()
	{
		this.classPath.add("java.lang");
	}
	
	/**
	 * @return the names of the library packages that will be
	 * searched to try to resolve an incomplete class name
	 */
	public String[] getClassPath() 
	{
		return classPath.toArray(new String[0]);
	}
	
	/**
	 * @param classPath - the names of the library packages that 
	 * will be searched to try to resolve an incomplete class name
	 */
	public void setClassPath(String[] classPath)
	{
		if(classPath == null)
		{
			throw new NullPointerException();
		}
		this.classPath.clear();
		this.classPath.addAll(Arrays.asList(classPath));
	}
	
	/**
	 * Attempts to find the static method described by the parameters, 
	 * or a method of the same name that will accept the same type of
	 * parameters.
	 * @param className - the name of the class that declared this method
	 * @param methodName - the name of the method
	 * @param paramTypes - the parameter types for the method
	 * @return - the Method object for this method
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	public Method resolveMethod(String className, String methodName, Class[] paramTypes)
	throws ClassNotFoundException, NoSuchMethodException
	{
		// Get the declaring class
		Class declaringClass = resolveClass(className);
		
		// Get all the methods for this class
		Method[] methods = declaringClass.getMethods();
		
		Method bestMatch = null; 
		int bestConversionCount = -1;
		
		// Examine each method, checking if the signature is compatible 
		for(Method method : methods)
		{
			// Check the modifiers: we only want public and static
			// methods
			if(!isPublicAndStatic(method))
			{
				continue;
			}
			
			// Check the name
			if(!method.getName().equals(methodName))
			{
				continue;
			}
			
			// Check the parameter list
			int conversionCount = compareParameterTypes(method, paramTypes);
			
			// Parameters don't match
			if(conversionCount == -1)
			{
				continue;
			}
			
			// Parameters match exactly
			if(conversionCount == 0)
			{
				bestMatch = method;
				break;
			}
			
			// No previous match
			if(bestMatch == null)
			{
				bestMatch = method;
				bestConversionCount = conversionCount;
			}
			else
			{
				// Current match is better
				if(conversionCount < bestConversionCount)
				{
					bestMatch = method;
					bestConversionCount = conversionCount;
				}
			}
				
		}
		
		if(bestMatch != null)
		{
			return bestMatch;
		}
		else
		{
			throw new NoSuchMethodException("Unknown method " + className + "." + methodName);
		}
	}
	
	/**
	 * Gets the Class object for the class name. If the class 
	 * name is incomplete, uses the values in the classpath
	 * to search specific packages for this class.
	 * @param className - the name of the class to resolve
	 * @return - the Class object for this class
	 * @throws ClassNotFoundException
	 */
	protected Class resolveClass(String className) 
	throws ClassNotFoundException
	{
		Class result = null;
		
		// Attempt to resolve the class without adding a 
		// package prefix
		try
		{
			result = Class.forName(className);
			return result;
		}
		catch(ClassNotFoundException e){}
		
		// Try all the package prefixes in the classpath
		for(String prefix : classPath)
		{
			String prefixedClassName = prefix + "." + className;
			try
			{
				result = Class.forName(prefixedClassName);
				return result;
			}
			catch(ClassNotFoundException e){}
		}
		
		// No prefix worked, the class isn't resolved
		throw new ClassNotFoundException("Unknown class " + className);
	}

	/**
	 * Resolves whether a parameter type in a method invocation
	 * is valid, possibly after autoboxing and widening conversions.
	 * @param declarationType - the type in the method declaration
	 * @param invocationType - the type in the method invocation
	 * @return true if an object of type invocationType can 
	 * be used in place of a object of type declarationType
	 */
	protected static boolean isValidTypeConversion(Class declarationType, Class invocationType)
	{
		if(validTypeConversions.containsKey(declarationType))
		{
			return validTypeConversions.get(declarationType).contains(invocationType);
		}
		else
		{
			return declarationType.isAssignableFrom(invocationType);
		}
	}
	
	private boolean isPublicAndStatic(Method method)
	{
		int modifiers = method.getModifiers();
		return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers);
	}
	
	// Returns -1 if the invocation parameters aren't applicable
	// to the method. Otherwise returns the number of parameters
	// that have to be adapted in some way (autoboxing, widening
	// conversion etc.).
	private int compareParameterTypes(Method method, Class[] invocationParameters)
	{
		Class[] declarationParameters = method.getParameterTypes();

		if(declarationParameters.length != invocationParameters.length)
		{
			return -1;
		}
		
		int conversionCount = 0;
		int count = 0;
		for(Class parameter : declarationParameters)
		{
			if(!parameter.equals(invocationParameters[count]))
			{
				conversionCount++;
				if(!StaticMethodResolver.isValidTypeConversion(parameter, invocationParameters[count]))
				{
					conversionCount = -1;
					break;
				}
			}
			count++;
		}
		return conversionCount;
	}
}
