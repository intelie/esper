///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using Properties = net.esper.compat.EDataDictionary;

namespace net.esper.client
{
    /// <summary>
    /// Holds configuration for an input/output adapter loader.
    /// </summary>

    public class ConfigurationAdapterLoader
    {
        private String loaderName;
        private String className;
        private Properties configProperties;

        /// <summary>Ctor.</summary>
        public ConfigurationAdapterLoader()
        {
        }

        /// <summary>Gets or sets the loader class name.</summary>
        public String TypeName
        {
            get { return className; }
            set { this.className = value; }
        }

        /// <summary>Gets or sets loader configuration properties.</summary>
        public Properties ConfigProperties
        {
            get { return configProperties; }
			set { this.configProperties = value; }
        }

        /// <summary>Gets or sets the loader name.</summary>
        public String LoaderName
        {
            get { return loaderName; }
			set { this.loaderName = value; }
        }
    }
}
