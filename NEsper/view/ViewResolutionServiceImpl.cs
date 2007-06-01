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
	    private readonly IDictionary<String, IDictionary<String, Class>> nameToFactoryMap;

	    /// <summary>Ctor.</summary>
	    /// <param name="configurationPlugInViews">is the configured plug-in views</param>
	    /// <throws>ConfigurationException when plug-in views cannot be solved</throws>
	    public ViewResolutionServiceImpl(List<ConfigurationPlugInView> configurationPlugInViews)
	    {
	        nameToFactoryMap = new EHashDictionary<String, IDictionary<String, Class>>();

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

	            Class clazz;
	            try
	            {
	                clazz = Class.ForName(entry.FactoryClassName);
	            }
	            catch (ClassNotFoundException ex)
	            {
	                throw new ConfigurationException("View factory class " + entry.FactoryClassName + " could not be loaded");
	            }

	            IDictionary<String, Class> namespaceMap = nameToFactoryMap.Get(entry.Namespace);
	            if (namespaceMap == null)
	            {
	                namespaceMap = new EHashDictionary<String, Class>();
	                nameToFactoryMap.Put(entry.Namespace, namespaceMap);
	            }
	            namespaceMap.Put(entry.Name, clazz);
	        }
	    }

	    public ViewFactory Create(ViewSpec spec)
	    {
	        if (log.IsDebugEnabled())
	        {
	            log.Debug(".create Creating view factory, spec=" + spec.ToString());
	        }

	        Class viewFactoryClass = null;

	        // Pre-configured views override build-in views
	        IDictionary<String, Class> namespaceMap = nameToFactoryMap.Get(spec.ObjectNamespace);
	        if (namespaceMap != null)
	        {
	            viewFactoryClass = namespaceMap.Get(spec.ObjectName);
	        }

	        // if not found in the plugin views, try o resolve as a builtin view
	        if (viewFactoryClass == null)
	        {
	            // Determine view class
	            ViewEnum viewEnum = ViewEnum.ForName(spec.ObjectNamespace, spec.ObjectName);

	            if (viewEnum == null)
	            {
	                String message = "View name '" + spec.ObjectNamespace + ":" + spec.ObjectName + "' is not a known view name";
	                throw new ViewProcessingException(message);
	            }

	            viewFactoryClass = viewEnum.FactoryClass;
	        }

	        ViewFactory viewFactory = null;
	        try
	        {
	            viewFactory = (ViewFactory) viewFactoryClass.NewInstance();

	            if (log.IsDebugEnabled())
	            {
	                log.Debug(".create Successfully instantiated view");
	            }
	        }
	        catch (ClassCastException e)
	        {
	            String message = "Error casting view factory instance to " + typeof(ViewFactory) + " interface for view '" + spec.ObjectName + "'";
	            throw new ViewProcessingException(message, e);
	        }
	        catch (IllegalAccessException e)
	        {
	            String message = "Error invoking view factory constructor for view '" + spec.ObjectName;
	            message += "', no invocation access for Class.newInstance";
	            throw new ViewProcessingException(message, e);
	        }
	        catch (InstantiationException e)
	        {
	            String message = "Error invoking view factory constructor for view '" + spec.ObjectName;
	            message += "' using Class.newInstance";
	            throw new ViewProcessingException(message, e);
	        }

	        return viewFactory;
	    }
	}
} // End of namespace
