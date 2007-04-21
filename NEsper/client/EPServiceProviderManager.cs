// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.Collections.Generic;

using net.esper.core;

namespace net.esper.client
{
    /// <summary>
    /// Factory for instances of <seealso cref="EPServiceProvider" />.
    /// </summary>

    public sealed class EPServiceProviderManager
    {
        private static IDictionary<String, EPServiceProvider> runtimes = new Dictionary<String, EPServiceProvider>();
        private static EPServiceProvider defaultServiceProvider;

        /// <summary> Returns the default EPServiceProvider.</summary>
        /// <returns> default instance of the service.
        /// </returns>

        public static EPServiceProvider GetDefaultProvider()
        {
            return GetProvider(null, new Configuration());
        }

        /// <summary> Returns the default EPServiceProvider.</summary>
        /// <param name="configuration">is the configuration for the service
        /// </param>
        /// <returns> default instance of the service.
        /// </returns>
        /// <throws>  ConfigurationException to indicate a configuration problem </throws>

        public static EPServiceProvider GetDefaultProvider(Configuration configuration)
        {
            return GetProvider(null, configuration);
        }

        /// <summary> Returns an EPServiceProvider for a given registration URI.</summary>
        /// <param name="uri">the registration URI
        /// </param>
        /// <returns> EPServiceProvider for the given registration URI.
        /// </returns>

        public static EPServiceProvider GetProvider(String uri)
        {
            return GetProvider(uri, new Configuration());
        }

        /// <summary> Returns an EPServiceProvider for a given registration URI.</summary>
        /// <param name="uri">the registration URI
        /// </param>
        /// <param name="configuration">is the configuration for the service
        /// </param>
        /// <returns> EPServiceProvider for the given registration URI.
        /// </returns>
        /// <throws>  ConfigurationException to indicate a configuration problem </throws>

        public static EPServiceProvider GetProvider(String uri, Configuration configuration)
        {
            lock (runtimes)
            {
                if (String.IsNullOrEmpty(uri))
                {
                    if (defaultServiceProvider == null)
                    {
                        defaultServiceProvider = new EPServiceProviderImpl(configuration);
                    }

                    return defaultServiceProvider;
                }

                if (runtimes.ContainsKey(uri))
                {
                    return runtimes[uri];
                }

                // New runtime
                EPServiceProvider runtime = new EPServiceProviderImpl(configuration);
                runtimes[uri] = runtime;

                return runtime;
            }
        }

        /// <summary>
        /// Clears references to the provider.
        /// </summary>
        /// <param name="uri"></param>

        public static void PurgeProvider( String uri )
        {
            lock (runtimes)
            {
                if (String.IsNullOrEmpty(uri))
                {
                    defaultServiceProvider = null;
                    return ;
                }

                runtimes.Remove(uri);
            }
        }
    }
}
