using System;
using System.Reflection;

using net.esper.compat;

using LogFactory = org.apache.commons.logging.LogFactory;
using Log = org.apache.commons.logging.Log;

namespace net.esper.view
{
	/// <summary>
    /// Static factory for creating view instances based on a view specification and a given parent view.
    /// </summary>
	
    public sealed class ViewFactory
	{
		/// <summary> Instantiates a view based on view name and parameters stored in the view spec, and attempts to
		/// hook it up with a parent view.
		/// </summary>
		/// <param name="parentView">is the parent view to hook the new view into
		/// </param>
		/// <param name="spec">contains view name and parameters
		/// </param>
		/// <returns> instantiated and hooked-up view
		/// </returns>
		/// <throws>  ViewProcessingException if the view name is wrong, parameters don't match view constructors, or </throws>
		/// <summary> the view refuses to hook up with its parent
		/// </summary>
		public static View Create(Viewable parentView, ViewSpec spec)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(
					".create Creating view, parentView.class=" + parentView.GetType().FullName + 
					"  spec=" + spec.ToString());
			}
			
			// Determine view class
			ViewEnum viewEnum = ViewEnum.forName(spec.ObjectNamespace, spec.ObjectName);
			
			if (viewEnum == null)
			{
				String message = "View name '" + spec.ObjectName + "' is not a known view name";
				log.Fatal(".create " + message);
				throw new ViewProcessingException(message);
			}
			
			Object[] arguments = CollectionHelper.ToArray( spec.ObjectParameters ) ;
			
			// If the view requires parameters, the empty argument list would be a problem
			// since all views are also Java beans
			if ((arguments.Length == 0) && (viewEnum.IsRequiresParameters))
			{
				String message = "No parameters have been supplied for view " + spec.ObjectName;
				throw new ViewProcessingException(message);
			}
			
			View view = null;
			try
			{
                view = (View) Activator.CreateInstance( viewEnum.Clazz, arguments ) ; 
				//view = (View) ConstructorUtils.invokeConstructor(viewEnum.Clazz, arguments);
				
				if (log.IsDebugEnabled)
				{
					log.Debug(".create Successfully instantiated view");
				}
			}
			catch (MissingMethodException e)
			{
				String message =
					"Error invoking constructor for view '" + spec.ObjectName +
					"', the view parameter list is not valid for the view";
				log.Fatal(".create " + message);
				throw new ViewProcessingException(message, e);
			}
			catch (UnauthorizedAccessException e)
			{
				String message =
					"Error invoking constructor for view '" + spec.ObjectName +
					"', no invocation access";
				log.Fatal(".create " + message);
				throw new ViewProcessingException(message, e);
			}
			catch (TargetInvocationException e)
			{
				String message =
					"Error invoking constructor for view '" + spec.ObjectName +
					"', invocation threw exception: " + e.InnerException.Message;
				log.Fatal(".create " + message);
				throw new ViewProcessingException(message, e);
			}
			catch (System.Exception e)
			{
				String message =
					"Error invoking constructor for view '" + spec.ObjectName +
					"', could not instantiateChain";
				log.Fatal(".create " + message);
				throw new ViewProcessingException(message, e);
			}
			
			// Ask view if it can indeed hook into the parent
			String errorMessage = view.AttachesTo(parentView);
			if (errorMessage != null)
			{
				String message = "Cannot attach view, the view '" + spec.ObjectName + "' is incompatible to its parent view, explanation: " + errorMessage;
				log.Fatal(".create " + message);
				throw new ViewProcessingException(message);
			}
			
			return view;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(ViewFactory));
	}
}
