///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

namespace net.esper.client
{
	/// <summary>
    /// Configuration information for plugging in a custom view.
    /// </summary>
	
    public class ConfigurationPlugInView
	{
	    private String _namespace;
	    private String name;
	    private String factoryClassName;

	    /// <summary>Ctor.</summary>
	    public ConfigurationPlugInView()
	    {
	    }

        /// <summary>Gets or sets  the namespace</summary>
	    /// <returns>namespace</returns>
	    public String Namespace
	    {
	        get { return _namespace; }
            set { _namespace = value ; }
	    }

        /// <summary>Gets or sets  the view name.</summary>
	    /// <returns>view name</returns>
	    public String Name
	    {
	        get { return name; }
            set { name = value; }
	    }

	    /// <summary>Gets or sets the view factory class name.</summary>
	    /// <returns>factory class name</returns>
	    public String FactoryClassName
	    {
	        get { return factoryClassName; }
            set { factoryClassName = value ; }
	    }
	}
} // End of namespace
