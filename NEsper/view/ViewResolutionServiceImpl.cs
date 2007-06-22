///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.eql.spec;

using org.apache.commons.logging;

namespace net.esper.view
{
	/// <summary>
	/// Resolves view namespace and name to view factory class, using configuration.
	/// </summary>
	public class ViewResolutionServiceImpl : ViewResolutionService
	{
	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	    
	    private readonly EDictionary<String, EDictionary<String, Type>> nameToFactoryMap;

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="configurationPlugInViews">is the configured plug-in views</param>
        /// <throws>ConfigurationException when plug-in views cannot be solved</throws>
	    public ViewResolutionServiceImpl(IList<ConfigurationPlugInView> configurationPlugInViews)
	    {
	        nameToFactoryMap = new HashDictionary<String, EDictionary<String, Type>>();

	        if (configurationPlugInViews == null)
	        {
	            return;
	        }

	        foreach (ConfigurationPlugInView entry in configurationPlugInViews)
	        {
	            if (entry.FactoryClassName == null)
	            {
	                throw new ConfigurationException("Factory class name has not been supplied for object '" + entry.Name + "'");
	            }
	            if (entry.Namespace == null)
	            {
	                throw new ConfigurationException("Namespace name has not been supplied for object '" + entry.Name + "'");
	            }
	            if (entry.Name == null)
	            {
	                throw new ConfigurationException("Name has not been supplied for object in namespace '" + entry.Namespace + "'");
	            }

	            Type type;
	            try
	            {
	                type = Type.GetType(entry.FactoryClassName);
	            }
	            catch (TypeLoadException)
	            {
	                throw new ConfigurationException("View factory class " + entry.FactoryClassName + " could not be loaded");
	            }

	            EDictionary<String, Type> namespaceMap = nameToFactoryMap.Fetch(entry.Namespace);
	            if (namespaceMap == null)
	            {
	                namespaceMap = new HashDictionary<String, Type>();
	                nameToFactoryMap[entry.Namespace] = namespaceMap;
	            }
	            namespaceMap[entry.Name] = type;
	        }
	    }

        /// <summary>
        /// Instantiates a {@link ViewFactory} based on the view namespace and name stored in the view spec.
        /// <para>
        /// Does not actually use the view factory object created.
        /// </para>
        /// </summary>
        /// <param name="spec">contains view name and namespace</param>
        /// <returns>{@link ViewFactory} instance</returns>
        /// <throws>ViewProcessingException if the view namespace or name cannot resolve</throws>
	    public ViewFactory Create(ViewSpec spec)
	    {
	    	if (log.IsDebugEnabled)
	        {
	            log.Debug(".create Creating view factory, spec=" + spec.ToString());
	        }

	        Type viewFactoryType = null;

	        // Pre-configured views override build-in views
	        EDictionary<String, Type> namespaceMap = nameToFactoryMap.Fetch(spec.ObjectNamespace);
	        if (namespaceMap != null)
	        {
	            viewFactoryType = namespaceMap.Fetch(spec.ObjectName);
	        }

	        // if not found in the plugin views, try o resolve as a builtin view
            if (viewFactoryType == null)
	        {
	            // Determine view class
	            ViewEnum viewEnum = ViewEnum.ForName(spec.ObjectNamespace, spec.ObjectName);

	            if (viewEnum == null)
	            {
	                String message = "View name '" + spec.ObjectNamespace + ":" + spec.ObjectName + "' is not a known view name";
	                throw new ViewProcessingException(message);
	            }

	            viewFactoryType = viewEnum.FactoryType;
	        }

	        ViewFactory viewFactory = null;
	        try
	        {
                viewFactory = (ViewFactory) Activator.CreateInstance(viewFactoryType);

	            if (log.IsDebugEnabled)
	            {
	                log.Debug(".create Successfully instantiated view");
	            }
	        }
	        catch (InvalidCastException e)
	        {
	            String message = "Error casting view factory instance to " + typeof(ViewFactory) + " interface for view '" + spec.ObjectName + "'";
	            throw new ViewProcessingException(message, e);
	        }
	        catch (MethodAccessException e)
	        {
	            String message = "Error invoking view factory constructor for view '" + spec.ObjectName;
	            message += "', no invocation access for Class.newInstance";
	            throw new ViewProcessingException(message, e);
	        }

	        return viewFactory;
	    }
	}
} // End of namespace
