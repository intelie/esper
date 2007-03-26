using System;
using System.Collections.Generic;
using System.IO;

namespace net.esper.compat
{
    /// <summary>
    /// Manages access to named resources
    /// </summary>

    public sealed class ResourceManager
    {
        private static List<string> m_searchPath;

        /// <summary>
        /// Gets or sets the search path.
        /// </summary>
        /// <value>The search path.</value>
        public static IEnumerable<string> SearchPath
        {
            get { return m_searchPath; }
            set { m_searchPath = new List<string>(value); }
        }

        /// <summary>
        /// Adds to the search path
        /// </summary>
        /// <param name="searchPathElement"></param>

        public static void AddSearchPathElement(String searchPathElement)
        {
            if (!m_searchPath.Contains(searchPathElement))
            {
                m_searchPath.Add(searchPathElement);
            }
        }

        /// <summary>
        /// Resolves a resource and returns the file info.
        /// </summary>
        /// <param name="name">The name.</param>
        /// <param name="searchPath">The search path.</param>
        /// <returns></returns>

        public static FileInfo ResolveResourceFile(string name, string searchPath)
        {
            string filename = String.Format(@"{0}\{1}", searchPath, name);
            if (File.Exists(filename))
            {
                return new FileInfo(filename);
            }
            else
            {
                return null;
            }
        }

        /// <summary>
        /// Resolves a resource and returns the file info.
        /// </summary>
        /// <param name="name">The name.</param>

        public static FileInfo ResolveResourceFile(string name)
        {
            foreach (String pathElement in SearchPath)
            {
                FileInfo fileInfo = ResolveResourceFile(name, pathElement);
                if ( fileInfo != null )
                {
                    return fileInfo;
                }
            }

            return null;
        }

        /// <summary>
        /// Resolves a resource and the URL for the resource
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>

        public static Uri ResolveResourceURL(string name)
        {
            FileInfo fileInfo = ResolveResourceFile(name);
            if (fileInfo != null)
            {
                UriBuilder builder = new UriBuilder();
                builder.Scheme = Uri.UriSchemeFile;
                builder.Host = String.Empty;
                builder.Path = fileInfo.FullName;
                return builder.Uri;
            }

            return null;
        }

        /// <summary>
        /// Attempts to retrieve the resource identified by the specified
        /// name as a stream.  If the stream can not be retrieved, this
        /// method returns null.
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>

        public static Stream GetResourceAsStream(string name)
        {
            // Currently using file-based search and lookup.  This needs to be expanded
            // to cover a broader search-lookup strategy that includes true web-based
            // pathing and internal stream lookups like those in the manifest.
            
            FileInfo fileInfo = ResolveResourceFile(name);
            if (fileInfo != null)
            {
                Stream stream = new FileStream(fileInfo.FullName, FileMode.Open, FileAccess.Read);
                return stream;
            }

            return null;
        }

        /// <summary>
        /// Initializes the class
        /// </summary>

        static ResourceManager()
        {
            m_searchPath = new List<string>();
            m_searchPath.Add(Environment.CurrentDirectory);
            m_searchPath.Add(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData)) ;
            m_searchPath.Add(Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData)) ;
        }
    }
}
