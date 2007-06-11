using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.core;

using org.apache.commons.logging;

namespace net.esper.util
{
    /// <summary>
    /// Used for retrieving static method objects. It provides two points of added
    /// functionality over the standard System.Reflection mechanism of retrieving methods.
    /// First, class names can be partial, and if the class name is partial
    /// then import service is searched for the class.
    /// </summary>
    
    public class StaticMethodResolver
    {
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

		/**
		 * Attempts to find the static method described by the parameters, 
		 * or a method of the same name that will accept the same type of
		 * parameters.
	      * @param declaringClass - the class to search for the method
		 * @param methodName - the name of the method
		 * @param paramTypes - the parameter types for the method
		 * @return - the Method object for this method
		 * @throws NoSuchMethodException if the method could not be found
		 */
		public static MethodInfo ResolveMethod(Type declaringClass, String methodName, Type[] paramTypes)
		{
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".resolve method className=" + declaringClass.FullName + ", methodName=" + methodName);
	        }

            // Get the method with the specified signature
            MethodInfo[] methods = declaringClass.GetMethods() ;

            MethodInfo bestMatch = null; 
			int bestConversionCount = -1;
		
			// Examine each method, checking if the signature is compatible 
			foreach(MethodInfo method in methods)
			{
				// Check the modifiers: we only want public and static methods
				if(!IsPublicAndStatic(method))
				{
					continue;
				}
			
				// Check the name
				if ( method.Name != methodName )
				{
					continue ;
				}
			
				// Check the parameter list
				int conversionCount = CompareParameterTypes(method, paramTypes);
			
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

			if (bestMatch != null)
			{
				return bestMatch;
			}

            StringBuilder message = new StringBuilder();
            message.AppendFormat( "Unknown method {0}.{1}", declaringClass.FullName, methodName ) ;
            message.Append( '(' ) ;

            if ((paramTypes != null) && (paramTypes.Length != 0))
            {
                String appendage = String.Empty ;
                
                foreach (Type param in paramTypes)
                {
                    message.Append( param.ToString() ) ;
                    message.Append( appendage ) ;
                    appendage = "," ;
                }
            }
            
            message.Append( ')' ) ;

            throw new MethodAccessException( message.ToString() ) ;
        }
        
        private static bool IsWideningConversion(Type declarationType, Type invocationType)
       	{
			if (wideningConversions.ContainsKey(declarationType))
			{
				return wideningConversions[declarationType].Contains(invocationType);
			}
			else
			{
				return false;
			}
		}
        
        private static bool IsPublicAndStatic(MethodInfo method)
		{
        	return method.IsPublic && method.IsStatic ;
        }

        /// <summary>
		/// Returns -1 if the invocation parameters aren't applicable
		/// to the method. Otherwise returns the number of parameters
		/// that have to be converted
        /// </summary>
        /// <param name="method"></param>
        /// <param name="invocationParameters"></param>

        private static int CompareParameterTypes(MethodInfo method, Type[] invocationParameters)
		{
        	ParameterInfo[] declarationParameters = method.GetParameters() ;
		
			if(invocationParameters == null)
			{
				return declarationParameters.Length == 0 ? 0 : -1;
			}
		
			if(declarationParameters.Length != invocationParameters.Length)
			{
				return -1;
			}

			int conversionCount = 0;
			int count = 0;
			foreach(ParameterInfo parameter in declarationParameters)
			{
				if(!IsIdentityConversion(parameter.ParameterType, invocationParameters[count]))
				{
					conversionCount++;
					if(!IsWideningConversion(parameter.ParameterType, invocationParameters[count]))
					{
						conversionCount = -1;
						break;
					}
				}
				count++;
			}
	
			return conversionCount;
		}

        /// <summary>
        /// Identity conversion means no conversion, wrapper conversion, 
		/// or conversion to a supertype
        /// </summary>
        /// <param name="declarationType"></param>
        /// <param name="invocationType"></param>
        /// <returns></returns>

        private static bool IsIdentityConversion(Type declarationType, Type invocationType)
		{
			if(wrappingConversions.ContainsKey(declarationType))
			{
				return wrappingConversions[declarationType].Contains(invocationType) || declarationType.IsAssignableFrom(invocationType);
			}
			else 
			{
				return declarationType.IsAssignableFrom(invocationType);
			}
		}
        
        private static readonly EDictionary<Type, Set<Type>> wideningConversions = new EHashDictionary<Type, Set<Type>>();
		private static readonly EDictionary<Type, Set<Type>> wrappingConversions = new EHashDictionary<Type, Set<Type>>();
	
		static StaticMethodResolver()
		{
			// Initialize the map of wrapper conversions
			Set<Type> booleanWrappers = new EHashSet<Type>();
			booleanWrappers.Add(typeof(bool));
			booleanWrappers.Add(typeof(bool?));
			StaticMethodResolver.wrappingConversions.Put(typeof(bool), booleanWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(bool?), booleanWrappers);
			
			Set<Type> charWrappers = new EHashSet<Type>();
			charWrappers.Add(typeof(char));
			charWrappers.Add(typeof(char?));		
			StaticMethodResolver.wrappingConversions.Put(typeof(char), charWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(char?), charWrappers);
			
			Set<Type> sbyteWrappers = new EHashSet<Type>();
			sbyteWrappers.Add(typeof(sbyte));
			sbyteWrappers.Add(typeof(sbyte?));
			StaticMethodResolver.wrappingConversions.Put(typeof(sbyte), sbyteWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(sbyte?), sbyteWrappers);

            Set<Type> byteWrappers = new EHashSet<Type>();
            byteWrappers.Add(typeof(byte));
            byteWrappers.Add(typeof(byte?));
            StaticMethodResolver.wrappingConversions.Put(typeof(byte), byteWrappers);
            StaticMethodResolver.wrappingConversions.Put(typeof(byte?), byteWrappers);

			Set<Type> shortWrappers = new EHashSet<Type>();
			shortWrappers.Add(typeof(short));
			shortWrappers.Add(typeof(short?));
			StaticMethodResolver.wrappingConversions.Put(typeof(short), shortWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(short?), shortWrappers);

            Set<Type> ushortWrappers = new EHashSet<Type>();
            ushortWrappers.Add(typeof(ushort));
            ushortWrappers.Add(typeof(ushort?));
            StaticMethodResolver.wrappingConversions.Put(typeof(ushort), ushortWrappers);
            StaticMethodResolver.wrappingConversions.Put(typeof(ushort?), ushortWrappers);

			Set<Type> intWrappers = new EHashSet<Type>();
			intWrappers.Add(typeof(int));
			intWrappers.Add(typeof(int?));
			StaticMethodResolver.wrappingConversions.Put(typeof(int), intWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(int?), intWrappers);

            Set<Type> uintWrappers = new EHashSet<Type>();
            uintWrappers.Add(typeof(uint));
            uintWrappers.Add(typeof(uint?));
            StaticMethodResolver.wrappingConversions.Put(typeof(uint), uintWrappers);
            StaticMethodResolver.wrappingConversions.Put(typeof(uint?), uintWrappers);

			Set<Type> longWrappers = new EHashSet<Type>();
			longWrappers.Add(typeof(long));
			longWrappers.Add(typeof(long?));
			StaticMethodResolver.wrappingConversions.Put(typeof(long), longWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(long?), longWrappers);

            Set<Type> ulongWrappers = new EHashSet<Type>();
            ulongWrappers.Add(typeof(ulong));
            ulongWrappers.Add(typeof(ulong?));
            StaticMethodResolver.wrappingConversions.Put(typeof(ulong), ulongWrappers);
            StaticMethodResolver.wrappingConversions.Put(typeof(ulong?), ulongWrappers);

            
            Set<Type> floatWrappers = new EHashSet<Type>();
			floatWrappers.Add(typeof(float));
			floatWrappers.Add(typeof(float?));
			StaticMethodResolver.wrappingConversions.Put(typeof(float), floatWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(float?), floatWrappers);
			
			Set<Type> doubleWrappers = new EHashSet<Type>();
			doubleWrappers.Add(typeof(double));
			doubleWrappers.Add(typeof(double?));
			StaticMethodResolver.wrappingConversions.Put(typeof(double), doubleWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(double?), doubleWrappers);
	
			// Initialize the map of widening conversions
			Set<Type> wideningConversions = new EHashSet<Type>();

            wideningConversions.AddAll(byteWrappers);
            wideningConversions.AddAll(sbyteWrappers);

			StaticMethodResolver.wideningConversions.Put(typeof(short), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(short?), new EHashSet<Type>(wideningConversions));
			wideningConversions.AddAll(shortWrappers);
			wideningConversions.AddAll(charWrappers);

            StaticMethodResolver.wideningConversions.Put(typeof(ushort), new EHashSet<Type>(wideningConversions));
            StaticMethodResolver.wideningConversions.Put(typeof(ushort?), new EHashSet<Type>(wideningConversions));
            wideningConversions.AddAll(ushortWrappers);
            
            StaticMethodResolver.wideningConversions.Put(typeof(int), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(int?), new EHashSet<Type>(wideningConversions));
			wideningConversions.AddAll(intWrappers);

			StaticMethodResolver.wideningConversions.Put(typeof(long), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(long?), new EHashSet<Type>(wideningConversions));
            wideningConversions.AddAll(longWrappers);

            StaticMethodResolver.wideningConversions.Put(typeof(ulong), new EHashSet<Type>(wideningConversions));
            StaticMethodResolver.wideningConversions.Put(typeof(ulong?), new EHashSet<Type>(wideningConversions));
			wideningConversions.AddAll(ulongWrappers);

			StaticMethodResolver.wideningConversions.Put(typeof(float), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(float?), new EHashSet<Type>(wideningConversions));
			wideningConversions.AddAll(floatWrappers);

			StaticMethodResolver.wideningConversions.Put(typeof(double), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(double?), new EHashSet<Type>(wideningConversions));
		}
    }
}
