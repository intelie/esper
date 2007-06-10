using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.core;
using net.esper.eql.spec;

using org.apache.commons.logging;

namespace net.esper.view
{
	/// <summary>
	/// Utility methods to deal with chains of views, and for merge/group-by views.
	/// </summary>

	public class ViewServiceHelper
	{
		/// <summary> Add merge views for any views in the chain requiring a merge (group view).
		/// Appends to the list of view specifications passed in one ore more
		/// new view specifications that represent merge views.
		/// Merge views have the same parameter list as the (group) view they merge data for.
		/// </summary>
		/// <param name="specifications">is a list of view definitions defining the chain of views.
		/// </param>

		public static void AddMergeViews( IList<ViewSpec> specifications )
		{
			if ( log.IsDebugEnabled )
			{
				log.Debug( ".addMergeViews Incoming specifications=" + specifications ) ;
			}

            LinkedList<ViewSpec> mergeViewSpecs = new LinkedList<ViewSpec>();

			foreach ( ViewSpec spec in specifications )
			{
				ViewEnum viewEnum = ViewEnum.ForName( spec.ObjectNamespace, spec.ObjectName );
				if ( viewEnum == null )
				{
					continue;
				}

				if ( viewEnum.MergeView == null )
				{
					continue;
				}

				// The merge view gets the same parameters as the view that requires the merge
				ViewSpec mergeViewSpec = new ViewSpec(
                    viewEnum.MergeView.Namespace,
                    viewEnum.MergeView.Name,
                    spec.ObjectParameters );

				// The merge views are added to the beginning of the list.
				// This enables group views to stagger ie. marketdata.group("symbol").group("feed").xxx.Merge(...).Merge(...)
				mergeViewSpecs.AddFirst( mergeViewSpec );
			}

			CollectionHelper.AddAll( specifications, mergeViewSpecs ) ;

			if ( log.IsDebugEnabled )
			{
				log.Debug( ".addMergeViews Outgoing specifications=" + specifications.ToString() ) ;
			}
		}

		/// <summary> Instantiate a chain of views.</summary>
		/// <param name="parentViewable">parent view to add the chain to
		/// </param>
		/// <param name="viewFactories">the view factories to use to make each view, or reuse and existing view
		/// </param>
		/// <param name="context">dependent services
		/// </param>
		/// <returns> chain of views instantiated
		/// </returns>
		/// <throws>  ViewProcessingException is throw to indicate an error instantiating the chain </throws>
		public static IList<View> InstantiateChain(
			Viewable parentViewable,
            IList<ViewFactory> viewFactories,
            StatementContext context)

		{
	        IList<View> newViews = new List<View>();
	        Viewable parent = parentViewable;

	        foreach (ViewFactory viewFactory in viewFactories)
	        {
	            // Create the new view object
	            View currentView = viewFactory.MakeView(context);

	            newViews.Add(currentView);
	            parent.AddView(currentView);

	            // Next parent is the new view
	            parent = currentView;
	        }

	        return newViews;
		}

		/// <summary> Removes a view from a parent view returning the orphaned parent views in a list.</summary>
		/// <param name="parentViewable">parent to remove view from
		/// </param>
		/// <param name="viewToRemove">view to remove
		/// </param>
		/// <returns> chain of orphaned views
		/// </returns>
		public static IList<View> RemoveChainLeafView( Viewable parentViewable, Viewable viewToRemove )
		{
            IList<View> removedViews = new List<View>();

			// The view to remove must be a leaf node - non-leaf views are just not removed
			if ( viewToRemove.HasViews )
			{
				return removedViews;
			}

			// Find child viewToRemove among descendent views
			IList<View> viewPath = ViewSupport.FindDescendent( parentViewable, viewToRemove );
			if ( viewPath == null )
			{
				String message = "Viewable not found when removing view " + viewToRemove;
				throw new ArgumentException( message );
			}

			// The viewToRemove is a direct child view of the stream
			if ( viewPath.Count == 0 )
			{
				bool isViewRemoved = parentViewable.RemoveView( (View) viewToRemove );

				if ( !isViewRemoved )
				{
					String message = "Failed to remove immediate child view " + viewToRemove;
					log.Fatal( ".remove " + message );
					throw new SystemException( message );
				}

				removedViews.Add( (View) viewToRemove );
				return removedViews;
			}

			View[] viewPathArray = CollectionHelper.ToArray( viewPath ) ;
			View currentView = (View) viewToRemove;

			// Remove child from parent views until a parent view has more children,
			// or there are no more parents (index=0).
			for ( int index = viewPathArray.Length - 1 ; index >= 0 ; index-- )
			{
				bool isViewRemoved = viewPathArray[index].RemoveView( currentView );
				removedViews.Add( currentView );

				if ( !isViewRemoved )
				{
					String message = "Failed to remove view " + currentView;
					log.Fatal( ".remove " + message );
					throw new SystemException( message );
				}

				// If the parent views has more child views, we are done
				if ( viewPathArray[index].HasViews )
				{
					break;
				}

				// The parent of the top parent is the stream, remove from stream
				if ( index == 0 )
				{
					parentViewable.RemoveView( viewPathArray[0] );
					removedViews.Add( viewPathArray[0] );
				}
				else
				{
					currentView = viewPathArray[index];
				}
			}

			return removedViews;
		}

		/// <summary> Match the views under the stream to the list of view specications passed in.
		/// The method changes the view specifications list passed in and removes those
		/// specifications for which matcing views have been found.
		/// If none of the views under the stream matches the first view specification passed in,
		/// the method returns the stream itself and leaves the view specification list unchanged.
		/// If one view under the stream matches, the view's specification is removed from the list.
		/// The method will then attempt to determine if any child views of that view also match
		/// specifications.
		/// </summary>
		/// <param name="rootViewable">is the top rootViewable event stream to which all views are attached as child views
		/// </param>
		/// <param name="viewFactories">viewFactories is the view specifications for making views
		/// </param>
		/// <returns> a pair of (A) the stream if no views matched, or the last child view that matched (B) the full list
		/// of parent views
		/// </returns>
		public static Pair<Viewable, IList<View>> MatchExistingViews(
			Viewable rootViewable,
            IList<ViewFactory> viewFactories)
		{
	        Viewable currentParent = rootViewable;
	        IList<View> matchedViewList = new List<View>();

	        bool foundMatch;

	        if (viewFactories.Count == 0)
	        {
	            return new Pair<Viewable, IList<View>>(rootViewable, new List<View>());
	        }

	        do      // while ((foundMatch) && (specifications.size() > 0));
	        {
	            foundMatch = false;

	            foreach (View childView in currentParent.Views)
	            {
	                ViewFactory currentFactory = viewFactories[0];

	                if (!(currentFactory.CanReuse(childView)))
	                {
	                     continue;
	                }

	                // The specifications match, check current data window size
	                viewFactories.RemoveAt(0);
	                currentParent = childView;
	                foundMatch = true;
	                matchedViewList.Add(childView);
	                break;
	            }
	        }
	        while ((foundMatch) && (viewFactories.Count != 0));

	        return new Pair<Viewable, IList<View>>(currentParent, matchedViewList);
	    }

	    /// <summary>
	    /// Given a list of view specifications obtained from by parsing this method instantiates a list of view factories.
	    /// The view factories are not yet aware of each other after leaving this method (so not yet chained logically).
	    /// They are simply instantiated and assigned view parameters.
	    /// </summary>
	    /// <param name="streamNum">the stream number</param>
	    /// <param name="viewSpecList">the view definition</param>
	    /// <param name="statementContext">statement service context and statement info</param>
	    /// <returns>list of view factories</returns>
	    /// <throws>
	    /// ViewProcessingException if the factory cannot be creates such as for invalid view spec
	    /// </throws>
	    public static IList<ViewFactory> InstantiateFactories(
			int streamNum,
	        IList<ViewSpec> viewSpecList,
	        StatementContext statementContext)
	    {
	        List<ViewFactory> factoryChain = new List<ViewFactory>();

	        int viewNum = 0;
	        foreach (ViewSpec spec in viewSpecList)
	        {
	            // Create the new view factory
	            ViewFactory viewFactory = statementContext.ViewResultionService.Create(spec);
	            factoryChain.Add(viewFactory);

	            // Set view factory parameters
	            try
	            {
	                ViewFactoryContext context = new ViewFactoryContext(statementContext, streamNum, viewNum, spec.ObjectNamespace, spec.ObjectName);
	                viewFactory.SetViewParameters(context, spec.ObjectParameters);
	            }
	            catch (ViewParameterException e)
	            {
	                throw new ViewProcessingException("Error in view '" + spec.ObjectNamespace + ':' + spec.ObjectName +
	                        "', " + e.Message);
	            }
	            viewNum++;
	        }

	        return factoryChain;
	    }

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
