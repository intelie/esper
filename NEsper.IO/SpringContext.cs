namespace net.esper.adapter
{
	/// <summary>
	/// Supplies properties for use in configuration files to configure Spring application context. 
	/// </summary>
	public class SpringContext
	{
	    /// <summary>
	    /// Use to configure a classpath context.
	    /// </summary>
	    public const string CLASSPATH_CONTEXT = "classpath-app-context";

	    /// <summary>
	    /// Use to configure a file context.
	    /// </summary>
	    public const string FILE_APP_CONTEXT = "file-app-context";
	}
}