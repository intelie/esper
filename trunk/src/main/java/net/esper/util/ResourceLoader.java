package net.esper.util;

import net.esper.client.EPException;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * Utility class for loading or resolving external resources via URL and class path.
 */
public class ResourceLoader
{
    public static URL resolveClassPathOrURLResource(String resourceName, String urlOrClasspathResource)
    {
        URL url = null;
        try
        {
            url = new URL(urlOrClasspathResource);
        }
        catch (MalformedURLException ex)
        {
            url = getClasspathResourceAsURL(resourceName, urlOrClasspathResource);
        }
        return url;
    }

    /**
     * Returns an URL from an application resource in the classpath.
     * <p>
     * The method first removes the '/' character from the resource name if
     * the first character is '/'.
     * <p>
     * The lookup order is as follows:
     * <p>
     * If a thread context class loader exists, use <tt>Thread.currentThread().getResourceAsStream</tt>
     * to obtain an InputStream.
     * <p>
     * If no input stream was returned, use the <tt>Configuration.class.getResourceAsStream</tt>.
     * to obtain an InputStream.
     * <p>
     * If no input stream was returned, use the <tt>Configuration.class.getClassLoader().getResourceAsStream</tt>.
     * to obtain an InputStream.
     * <p>
     * If no input stream was returned, throw an Exception.
     *
     * @param resource to get input stream for
     * @return input stream for resource
     */
    protected static URL getClasspathResourceAsURL(String resourceName, String resource)
    {
        String stripped = resource.startsWith("/") ?
                resource.substring(1) : resource;

        URL url = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            url = classLoader.getResource(stripped);
        }
        if (url == null) {
            ResourceLoader.class.getResource(resource);
        }
        if (url == null) {
            url = ResourceLoader.class.getClassLoader().getResource(stripped);
        }
        if (url == null ) {
            throw new EPException(resourceName + " resource '" + resource + "' not found");
        }
        return url;
    }



}
