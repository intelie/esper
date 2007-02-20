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
    /// 
    /// Unlike the Java version, widening and narrowing conversions do not need to occur.
    /// As a result, exact matches are required.
    /// </summary>
    
    public class StaticMethodResolver
    {
        private static readonly Log log = LogFactory.GetLog(typeof(StaticMethodResolver));

        /// <summary>
        /// Attempts to find the static method described by the parameters, 
        /// or a method of the same name that will accept the same type of
        /// parameters.
        /// </summary>

        public static MethodInfo ResolveMethod(
            String className,
            String methodName,
            Type[] paramTypes,
            AutoImportService autoImportService )
        {
            log.Debug(String.Format(".resolve method className=={0}, methodName=={1}", className, methodName));

            // Get the declaring class
            Type declaringClass = autoImportService.ResolveClass(className);

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

			if(bestMatch != null)
			{
				return bestMatch;
			}

            StringBuilder message = new StringBuilder();
            message.AppendFormat( "Unknown method {0}.{1}", className, methodName ) ;
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
        
        private static readonly EDictionary<Type, ISet<Type>> wideningConversions = new EHashDictionary<Type, ISet<Type>>();
		private static readonly EDictionary<Type, ISet<Type>> wrappingConversions = new EHashDictionary<Type, ISet<Type>>();
	
		static StaticMethodResolver()
		{
			// Initialize the map of wrapper conversions
			ISet<Type> booleanWrappers = new EHashSet<Type>();
			booleanWrappers.Add(typeof(bool));
			booleanWrappers.Add(typeof(bool?));
			StaticMethodResolver.wrappingConversions.Put(typeof(bool), booleanWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(bool?), booleanWrappers);
			
			ISet<Type> charWrappers = new EHashSet<Type>();
			charWrappers.Add(typeof(char));
			charWrappers.Add(typeof(char?));		
			StaticMethodResolver.wrappingConversions.Put(typeof(char), charWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(char?), charWrappers);
			
			ISet<Type> byteWrappers = new EHashSet<Type>();
			byteWrappers.Add(typeof(sbyte));
			byteWrappers.Add(typeof(sbyte?));
			StaticMethodResolver.wrappingConversions.Put(typeof(sbyte), byteWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(sbyte?), byteWrappers);
			
			ISet<Type> shortWrappers = new EHashSet<Type>();
			shortWrappers.Add(typeof(short));
			shortWrappers.Add(typeof(short?));
			StaticMethodResolver.wrappingConversions.Put(typeof(short), shortWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(short?), shortWrappers);
			
			ISet<Type> intWrappers = new EHashSet<Type>();
			intWrappers.Add(typeof(int));
			intWrappers.Add(typeof(int?));
			StaticMethodResolver.wrappingConversions.Put(typeof(int), intWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(int?), intWrappers);
			
			ISet<Type> longWrappers = new EHashSet<Type>();
			longWrappers.Add(typeof(long));
			longWrappers.Add(typeof(long?));
			StaticMethodResolver.wrappingConversions.Put(typeof(long), longWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(long?), longWrappers);
			
			ISet<Type> floatWrappers = new EHashSet<Type>();
			floatWrappers.Add(typeof(float));
			floatWrappers.Add(typeof(float?));
			StaticMethodResolver.wrappingConversions.Put(typeof(float), floatWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(float?), floatWrappers);
			
			ISet<Type> doubleWrappers = new EHashSet<Type>();
			doubleWrappers.Add(typeof(double));
			doubleWrappers.Add(typeof(double?));
			StaticMethodResolver.wrappingConversions.Put(typeof(double), doubleWrappers);
			StaticMethodResolver.wrappingConversions.Put(typeof(double?), doubleWrappers);
	
			// Initialize the map of widening conversions
			ISet<Type> wideningConversions = new EHashSet<Type>(byteWrappers);
			StaticMethodResolver.wideningConversions.Put(typeof(short), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(short?), new EHashSet<Type>(wideningConversions));
			
			wideningConversions.AddAll(shortWrappers);
			wideningConversions.AddAll(charWrappers);
			StaticMethodResolver.wideningConversions.Put(typeof(int), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(int?), new EHashSet<Type>(wideningConversions));
			
			wideningConversions.AddAll(intWrappers);
			StaticMethodResolver.wideningConversions.Put(typeof(long), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(long?), new EHashSet<Type>(wideningConversions));
	
			wideningConversions.AddAll(longWrappers);
			StaticMethodResolver.wideningConversions.Put(typeof(float), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(float?), new EHashSet<Type>(wideningConversions));
		
			wideningConversions.AddAll(floatWrappers);
			StaticMethodResolver.wideningConversions.Put(typeof(double), new EHashSet<Type>(wideningConversions));
			StaticMethodResolver.wideningConversions.Put(typeof(double?), new EHashSet<Type>(wideningConversions));
		}
    }
}
