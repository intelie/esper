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

        public static MethodInfo resolveMethod(
            String className,
            String methodName,
            Type[] paramTypes,
            AutoImportService autoImportService )
        {
            log.Debug(String.Format(".resolve method className=={0}, methodName=={1}", className, methodName));

            // Get the declaring class
            Type declaringClass = autoImportService.ResolveClass(className);

            // Get the method with the specified signature
            MethodInfo method = declaringClass.GetMethod( methodName, paramTypes);
            if (method.IsPublic && method.IsStatic)
            {
                return method;
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
   }
}