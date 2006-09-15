package net.esper.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.esper.eql.core.AutoImportService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Used for retrieving static method objects. It
 * provides two points of added functionality over the standard 
 * java.lang.reflect mechanism of retrieving methods. First, 
 * class names can be partial, and if the class name is partial
 * then java.lang is searched for the class. Second, 
 * invocation parameter types don't have to match the declaration
 * parameter types exactly when the standard java conversion
 * mechanisms (currently autoboxing and widening conversions) 
 * will make the invocation valid. Preference is given to those 
 * methods that require the fewest widening conversions.
 */
public class StaticMethodResolver 
{
	private static final Log log = LogFactory.getLog(StaticMethodResolver.class);
	
	private static final Map<Class, Set<Class>> wideningConversions = new HashMap<Class, Set<Class>>();
	private static final Map<Class, Set<Class>> wrappingConversions = new HashMap<Class, Set<Class>>();
	
	static 
	{
		// Initialize the map of wrapper conversions
		Set<Class> booleanWrappers = new HashSet<Class>();
		booleanWrappers.add(boolean.class);
		booleanWrappers.add(Boolean.class);
		StaticMethodResolver.wrappingConversions.put(boolean.class, booleanWrappers);
		StaticMethodResolver.wrappingConversions.put(Boolean.class, booleanWrappers);
		
		Set<Class> charWrappers = new HashSet<Class>();
		charWrappers.add(char.class);
		charWrappers.add(Character.class);		
		StaticMethodResolver.wrappingConversions.put(char.class, charWrappers);
		StaticMethodResolver.wrappingConversions.put(Character.class, charWrappers);
		
		Set<Class> byteWrappers = new HashSet<Class>();
		byteWrappers.add(byte.class);
		byteWrappers.add(Byte.class);
		StaticMethodResolver.wrappingConversions.put(byte.class, byteWrappers);
		StaticMethodResolver.wrappingConversions.put(Byte.class, byteWrappers);
		
		Set<Class> shortWrappers = new HashSet<Class>();
		shortWrappers.add(short.class);
		shortWrappers.add(Short.class);
		StaticMethodResolver.wrappingConversions.put(short.class, shortWrappers);
		StaticMethodResolver.wrappingConversions.put(Short.class, shortWrappers);
		
		Set<Class> intWrappers = new HashSet<Class>();
		intWrappers.add(int.class);
		intWrappers.add(Integer.class);
		StaticMethodResolver.wrappingConversions.put(int.class, intWrappers);
		StaticMethodResolver.wrappingConversions.put(Integer.class, intWrappers);
		
		Set<Class> longWrappers = new HashSet<Class>();
		longWrappers.add(long.class);
		longWrappers.add(Long.class);
		StaticMethodResolver.wrappingConversions.put(long.class, longWrappers);
		StaticMethodResolver.wrappingConversions.put(Long.class, longWrappers);
		
		Set<Class> floatWrappers = new HashSet<Class>();
		floatWrappers.add(float.class);
		floatWrappers.add(Float.class);
		StaticMethodResolver.wrappingConversions.put(float.class, floatWrappers);
		StaticMethodResolver.wrappingConversions.put(Float.class, floatWrappers);
		
		Set<Class> doubleWrappers = new HashSet<Class>();
		doubleWrappers.add(double.class);
		doubleWrappers.add(Double.class);
		StaticMethodResolver.wrappingConversions.put(double.class, doubleWrappers);
		StaticMethodResolver.wrappingConversions.put(Double.class, doubleWrappers);

		// Initialize the map of widening conversions
		Set<Class> wideningConversions = new HashSet<Class>(byteWrappers);
		StaticMethodResolver.wideningConversions.put(short.class, new HashSet<Class>(wideningConversions));
		StaticMethodResolver.wideningConversions.put(Short.class, new HashSet<Class>(wideningConversions));
		
		wideningConversions.addAll(shortWrappers);
		wideningConversions.addAll(charWrappers);
		StaticMethodResolver.wideningConversions.put(int.class, new HashSet<Class>(wideningConversions));
		StaticMethodResolver.wideningConversions.put(Integer.class, new HashSet<Class>(wideningConversions));
		
		wideningConversions.addAll(intWrappers);
		StaticMethodResolver.wideningConversions.put(long.class, new HashSet<Class>(wideningConversions));
		StaticMethodResolver.wideningConversions.put(Long.class, new HashSet<Class>(wideningConversions));

		wideningConversions.addAll(longWrappers);
		StaticMethodResolver.wideningConversions.put(float.class, new HashSet<Class>(wideningConversions));
		StaticMethodResolver.wideningConversions.put(Float.class, new HashSet<Class>(wideningConversions));
	
		wideningConversions.addAll(floatWrappers);
		StaticMethodResolver.wideningConversions.put(double.class, new HashSet<Class>(wideningConversions));
		StaticMethodResolver.wideningConversions.put(Double.class, new HashSet<Class>(wideningConversions));
	}
	
	/**
	 * Attempts to find the static method described by the parameters, 
	 * or a method of the same name that will accept the same type of
	 * parameters.
	 * @param className - the name of the class that declared this method
	 * @param methodName - the name of the method
	 * @param paramTypes - the parameter types for the method
	 * @param autoImportService - for resolving the class name
	 * @return - the Method object for this method
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	public static Method resolveMethod(String className, String methodName, Class[] paramTypes, AutoImportService autoImportService)
	throws ClassNotFoundException, NoSuchMethodException
	{
		log.debug(".resolve method className==" + className + ", methodName==" + methodName);

		// Get the declaring class
		Class declaringClass = autoImportService.resolveClass(className);
		
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
			StringBuffer params = new StringBuffer();
			if(paramTypes != null && paramTypes.length != 0)
			{
				String appendString = "";
				for(Object param : paramTypes)
				{
					params.append(appendString);
					params.append(param.toString());
					appendString = ", ";
				}
			}
			throw new NoSuchMethodException("Unknown method " + className + "." + methodName + "(" + params + ")");
		}
	}
	
	private static boolean isWideningConversion(Class declarationType, Class invocationType)
	{
		if(wideningConversions.containsKey(declarationType))
		{
			return wideningConversions.get(declarationType).contains(invocationType);
		}
		else
		{
			return false;
		}
	}
	
	private static boolean isPublicAndStatic(Method method)
	{
		int modifiers = method.getModifiers();
		return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers);
	}
	
	// Returns -1 if the invocation parameters aren't applicable
	// to the method. Otherwise returns the number of parameters
	// that have to be converted
	private static int compareParameterTypes(Method method, Class[] invocationParameters)
	{

		Class[] declarationParameters = method.getParameterTypes();
		
		if(invocationParameters == null)
		{
			return declarationParameters.length == 0 ? 0 : -1;
		}
		
		if(declarationParameters.length != invocationParameters.length)
		{
			return -1;
		}

		int conversionCount = 0;
		int count = 0;
		for(Class parameter : declarationParameters)
		{
			if(!isIdentityConversion(parameter, invocationParameters[count]))
			{
				conversionCount++;
				if(!isWideningConversion(parameter, invocationParameters[count]))
				{
					conversionCount = -1;
					break;
				}
			}
			count++;
		}

		return conversionCount;
	}
	
	// Identity conversion means no conversion, wrapper conversion, 
	// or conversion to a supertype
	private static boolean isIdentityConversion(Class declarationType, Class invocationType)
	{
		if(wrappingConversions.containsKey(declarationType))
		{
			return wrappingConversions.get(declarationType).contains(invocationType) || declarationType.isAssignableFrom(invocationType);	
		}
		else 
		{
			return declarationType.isAssignableFrom(invocationType);
		}

	}
}
