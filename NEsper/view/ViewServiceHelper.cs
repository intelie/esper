using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;

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

            ELinkedList<ViewSpec> mergeViewSpecs = new ELinkedList<ViewSpec>();

			foreach ( ViewSpec spec in specifications )
			{
				ViewEnum viewEnum = ViewEnum.forName( spec.ObjectNamespace, spec.ObjectName );
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
				// This enables group views to stagger ie. marketdata.group("symbol").group("feed").xxx.merge(...).merge(...)
				mergeViewSpecs.AddFirst( mergeViewSpec );
			}

			CollectionHelper.AddAll( specifications, mergeViewSpecs ) ;

			if ( log.IsDebugEnabled )
			{
				log.Debug( ".addMergeViews Outgoing specifications=" + specifications.ToString() ) ;
			}
		}

		/// <summary> Instantiate a chain of views.</summary>
		/// <param name="existingParentViews">- parent views
		/// </param>
		/// <param name="parentViewable">- parent view to add the chain to
		/// </param>
		/// <param name="specifications">- view specification, one for each chain element
		/// </param>
		/// <param name="context">- dependent services
		/// </param>
		/// <returns> chain of views instantiated
		/// </returns>
		/// <throws>  ViewProcessingException is throw to indicate an error instantiating the chain </throws>
		public static IList<View> InstantiateChain( IList<View> existingParentViews, Viewable parentViewable, IList<ViewSpec> specifications, ViewServiceContext context )
		{
			IList<View> newViews = new List<View>();
			Viewable parent = parentViewable;

			foreach ( ViewSpec spec in specifications )
			{
				// Create the new view object
				View currentView = ViewFactory.Create( parent, spec );
				newViews.Add( currentView );
				parent.AddView( currentView );

				// Set context
                ContextAwareView contextAwareView = currentView as ContextAwareView;
                if ( contextAwareView != null )
				{
                    contextAwareView.ViewServiceContext = context;
				}

				// New views get their ParentAwareView interface invoked if required
                ParentAwareView parentAwareView = currentView as ParentAwareView;
                if ( parentAwareView != null )
				{
					IList<View> parentViewList = new List<View>();
					CollectionHelper.AddAll( parentViewList, existingParentViews );
					CollectionHelper.AddAll( parentViewList, newViews );
                    parentAwareView.ParentAware = parentViewList;
					( (ParentAwareView) currentView ).ParentAware = parentViewList ;
				}

				// Next parent is the new view
				parent = currentView;
			}

			return newViews;
		}

		/// <summary> Removes a view from a parent view returning the orphaned parent views in a list.</summary>
		/// <param name="parentViewable">- parent to remove view from
		/// </param>
		/// <param name="viewToRemove">- view to remove
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
			IList<View> viewPath = ViewSupport.findDescendent( parentViewable, viewToRemove );
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
		/// <param name="specificationRepository">is a map of view and specification that enables view specification comparison
		/// </param>
		/// <param name="specifications">is the non-empty list of specifications describing the new chain of views to create.
		/// This parameter is changed by this method, ie. specifications are removed if they match existing views.
		/// </param>
		/// <returns> a pair of (A) the stream if no views matched, or the last child view that matched (B) the full list
		/// of parent views
		/// </returns>
		public static Pair<Viewable, IList<View>> MatchExistingViews(
            Viewable rootViewable,
            IDictionary<View, ViewSpec> specificationRepository,
            IList<ViewSpec> specifications )
		{
			Viewable currentParent = rootViewable;
            IList<View> matchedViewList = new List<View>();

			bool foundMatch = false;

			do
			// while ((foundMatch) && (specifications.Count > 0));
			{
				foundMatch = false;

				foreach ( View childView in currentParent.GetViews() )
				{
					ViewSpec spec = specificationRepository[ childView ];

					// It's possible that a child view is not known to this service since the
					// child view may not be reusable, such as a stateless filter (where-clause),
					// output rate limiting view, or such.
					// Continue and ignore that child view if it's view specification is not known.
					if ( spec == null )
					{
						continue;
					}

					// If the specification is found equal, remove
					if ( spec.Equals( specifications[0] ) )
					{
						specifications.RemoveAt(0) ;
						currentParent = childView;
						foundMatch = true;
						matchedViewList.Add( childView );
						break;
					}
				}
			}
			while ( ( foundMatch ) && ( specifications.Count > 0 ) );

			return new Pair<Viewable, IList<View>>( currentParent, matchedViewList );
		}

		private static readonly Log log = LogFactory.GetLog( typeof( ViewServiceHelper ) );
	}
}
