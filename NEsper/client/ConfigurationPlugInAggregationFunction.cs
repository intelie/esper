///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.client
{
    /// <summary>
    /// Configuration information for plugging in a custom aggregation function.
    /// </summary>
    public class ConfigurationPlugInAggregationFunction
    {
        private String name;
        private String functionClassName;

        /// <summary>Ctor.</summary>
        public ConfigurationPlugInAggregationFunction()
        {
        }

        /// <summary>Gets or sets the view name.</summary>
        public String Name
        {
            get { return name; }
			set { this.name = value; }
        }

        /// <summary>Gets or sets the aggregation function name.</summary>
        public String FunctionClassName
        {
            get { return functionClassName; }
            set { this.functionClassName = value; }
        }
    }
}
