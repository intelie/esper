using System;
using System.IO;
using System.Collections.Generic;
using System.Reflection;

using net.esper.compat;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.view
{
    /// <summary>
    /// A helper class for View implementations that provides generic implementation for some of the methods.
    /// Methods that contain the actual logic of the view are not implemented in this class.
    /// A common implementation normally does not need to override any of the methods implemented here, their
    /// implementation is generic and should suffice.
    /// 
    /// The class provides a convenience method for updateing it's children data updateChildren(Object[], Object[]).
    /// This method should be called from within the View.update(Object[], Object[]) methods in the subclasses.
    /// </summary>

    public abstract class ViewSupport : View
    {
        private Guid m_id = Guid.NewGuid();

        /// <summary>
        /// Gets the unique id for the view
        /// </summary>

        public Guid Id
        {
            get { return m_id; }
        }

        /// <summary>
        /// Parent viewable to this view - directly accessible by subclasses.
        /// </summary>

        protected Viewable parent;

        private readonly ELinkedList<View> children;

        /// <summary>
        /// Constructor
        /// </summary>
        
        protected ViewSupport()
        {
            children = new ELinkedList<View>();
        }

        public virtual Viewable Parent
        {
            get { return parent; }
            set { this.parent = value; }
        }

        public virtual View AddView(View view)
        {
            children.Add(view);
            view.Parent = this;
            return view;
        }

        public virtual bool RemoveView(View view)
        {
            bool isRemoved = children.Remove(view);
            view.Parent = null;
            return isRemoved;
        }

        public virtual IList<View> GetViews()
        {
            return children;
        }

        public virtual Boolean HasViews
        {
            get { return (children.Count > 0); }
        }

        /// <summary>
        /// Updates all the children with new data.Views may want to use the hasViews method on the Viewable interface to determineif there are any child views attached at all, and save the work of constructing the arrays and
        /// making the call to updateChildren() in case there aren't any children attached.
        /// </summary>
        /// <param name="newData">is the array of new event data</param>
        /// <param name="oldData">is the array of old event data</param>

        public void updateChildren(EventBean[] newData, EventBean[] oldData)
        {
            int size = children.Count;

            // Provide a shortcut for a single child view since this is a very common case.
            // No iteration required here.
            if (size == 1)
            {
                children.First.Update(newData, oldData);
            }
            else
            {
                foreach (View child in children)
                {
                    child.Update(newData, oldData);
                }
            }
        }

        /// <summary>
        /// Updates all the children with new data. Static convenience method that accepts the list of child
        /// views as a parameter.
        /// </summary>
        /// <param name="childViews">is the list of child views to send the data to</param>
        /// <param name="newData">is the array of new event data</param>
        /// <param name="oldData">is the array of old event data</param>
        
        protected static void updateChildren(IList<View> childViews, EventBean[] newData, EventBean[] oldData)
        {
            foreach (View child in childViews)
            {
                child.Update(newData, oldData);
            }
        }

        /// <summary>
        /// Copies the view by copying the bean properties of the view but does not
        /// copy the views children,does not copy or set the property for the parent view.
        /// </summary>
        /// <param name="view">to be copied</param>
        /// <returns>
        /// copy of the view, populated via Java bean property getter and setter methods
        /// </returns>

        public static View shallowCopyView(View view)
        {
            View copied = null;

            try
            {
                Type viewType = view.GetType() ;

                // Create a dictionary that contains all of the properties
                // known to the view type.  We will need the dictionary to
                // be indexed by name so that we can remove properties that
                // we should not be copying.
                Dictionary<String, PropertyInfo> properties = new Dictionary<string, PropertyInfo>();
                foreach (PropertyInfo propertyInfo in viewType.GetProperties())
                {
                    switch (propertyInfo.Name)
                    {
                        case "Views":
                        case "Parent":
                        case "EventType":
                            // Case statement represents specific properties we with to
                            // ignore.
                            break;
                        default:
                            // We are performing a shallow copy so it only makes sense
                            // to look at properties that we can read and write.  The
                            // read occurs on the source, the write on the target.

                            if (propertyInfo.CanRead && propertyInfo.CanWrite)
                            {
                                properties[propertyInfo.Name] = propertyInfo;
                            }
                            break;
                    }
                }

                // Remove properties that would and could not be copied
                //properties.Remove("Views");
                //properties.Remove("Parent");

                copied = (View) Activator.CreateInstance( viewType ) ;

                // Perform a shallow-copy of properties
                foreach (PropertyInfo propertyInfo in properties.Values )
                {
                    Object value = propertyInfo.GetValue( view, null ) ;
                    propertyInfo.SetValue(copied, value, null);
                }
            }
            catch (Exception e)
            {
            	log.Fatal(".shallowCopyView Failed to copy view " + view.GetType().Name);
				throw e;
            }

            return copied;
        }

        /// <summary>
        /// Convenience method for logging the parameters passed to the update method.
        /// Only logs if debug is enabled.
        /// </summary>
        /// <param name="prefix">is a prefix text to output for each line</param>
        /// <param name="newData">is the new data in an update call</param>
        /// <param name="oldData">is the old data in an update call</param>

        public static void dumpUpdateParams(String prefix, Object[] newData, Object[] oldData)
        {
            if (!log.IsDebugEnabled)
            {
                return;
            }

            StringWriter writer = new StringWriter();
            if (newData == null)
            {
                writer.WriteLine(prefix + " newData=null ");
            }
            else
            {
                writer.WriteLine(prefix + " newData.size=" + newData.Length + "...");
                printObjectArray(prefix, writer, newData);
            }

            if (oldData == null)
            {
                writer.WriteLine(prefix + " oldData=null ");
            }
            else
            {
                writer.WriteLine(prefix + " oldData.size=" + oldData.Length + "...");
                printObjectArray(prefix, writer, oldData);
            }

            log.Debug(".dumpUpdateParams Dumping update parameters..." + writer.ToString());
        }

        private static void printObjectArray(String prefix, TextWriter writer, Object[] objects)
        {
            int count = 0;
            foreach (Object obj in objects)
            {
                writer.WriteLine(prefix + " #" + count + " = " + obj.ToString());
            }
        }

        /// <summary>
        /// Convenience method for logging the child views of a Viewable. Only logs if
        /// debug is enabled.This is a recursive method.
        /// </summary>
        /// <param name="prefix">is a text to print for each view printed</param>
        /// <param name="parentViewable">is the parent for which the child views are displayed.</param>
        
        public static void dumpChildViews(String prefix, Viewable parentViewable)
        {
            if (log.IsDebugEnabled)
            {
                foreach (View child in parentViewable.GetViews())
                {
                    log.Debug(".dumpChildViews " + prefix + " " + child.ToString());
                    dumpChildViews(prefix + "  ", child);
                }
            }
        }

        /// <summary>
        /// Find the descendent view in the view tree under the parent view returning
        /// the list of view nodesbetween the parent view and the descendent view. Returns
        /// null if the descendent view is not found.Returns an empty list if the descendent
        /// view is a child view of the parent view.
        /// </summary>
        /// <param name="parentView">is the view to Start searching under</param>
        /// <param name="descendentView">is the view to find</param>
        /// <returns>list of Viewable nodes between parent and descendent view.</returns>
        
        public static IList<View> findDescendent(Viewable parentView, Viewable descendentView)
        {
            Stack<View> stack = new Stack<View>();

            Boolean found = false;

            foreach (View view in parentView.GetViews())
            {
                if (view == descendentView)
                {
                	List<View> viewList = new List<View>( stack ) ;
                    viewList.Reverse();
                    return viewList;
                }

                found = findDescendentRecusive(view, descendentView, stack);

                if (found)
                {
                	List<View> viewList = new List<View>( stack );
                    viewList.Reverse();
                    return viewList;
                }
            }

            return null;
        }

        private static bool findDescendentRecusive(View parentView, Viewable descendentView, Stack<View> stack)
        {
            stack.Push(parentView);

            Boolean found = false;
            foreach (View view in parentView.GetViews())
            {
                if (view == descendentView)
                {
                    return true;
                }

                found = findDescendentRecusive(view, descendentView, stack);

                if (found)
                {
                    break;
                }
            }

            if (!found)
            {
                stack.Pop();
                return false;
            }

            return true;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(ViewSupport));

        #region View Members
        
        /// <summary>
        /// Return null if the view will accept being attached to a particular object.
        /// </summary>
        /// <param name="parentViewable">is the potential parent for this view</param>
        /// <returns>
        /// null if this view can successfully attach to the parent, an error message if it cannot.
        /// </returns>
        
        abstract public string AttachesTo(Viewable parentViewable);

        /// <summary>
        /// Notify that data has been added or removed from the Viewable parent.
        /// The last object in the newData array of objects would be the newest object added to the parent view.
        /// The first object of the oldData array of objects would be the oldest object removed from the parent view.
        /// If the call to update contains new (inserted) data, then the first argument will be a non-empty list and the
        /// second will be empty. Similarly, if the call is a notification of deleted data, then the first argument will be
        /// empty and the second will be non-empty. Either the newData or oldData will be non-null.
        /// This method won't be called with both arguments being null, but either one could be null.
        /// The same is true for zero-length arrays. Either newData or oldData will be non-empty.
        /// If both are non-empty, then the update is a modification notification.
        /// When update() is called on a view by the parent object, the data in newData will be in the collection of the
        /// parent, and its data structures will be arranged to reflect that.
        /// The data in oldData will not be in the parent's data structures, and any access to the parent will indicate that
        /// that data is no longer there.
        /// </summary>
        /// <param name="newData">is the new data that has been added to the parent view</param>
        /// <param name="oldData">is the old data that has been removed from the parent view</param>
        
        abstract public void Update(EventBean[] newData, EventBean[] oldData);

        #endregion

        #region EventCollection Members

        /// <summary>
        /// Provides metadata information about the type of object the event collection contains.
        /// </summary>
        /// <returns>
        /// metadata for the objects in the collection
        /// </returns>

        abstract public EventType EventType
        {
            get;
            set; // Allows sub-classes to define this otherwise many things fail
        }

        #endregion

        #region IEnumerable<EventBean> Members

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>

        abstract public IEnumerator<EventBean> GetEnumerator() ;

        #endregion

        #region IEnumerable Members

        /// <summary>
        /// Returns an enumerator that iterates through a collection.
        /// </summary>
        /// <returns>
        /// An <see cref="T:System.Collections.IEnumerator"></see> object that can be used to iterate through the collection.
        /// </returns>
        
        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        #endregion
    }
}
