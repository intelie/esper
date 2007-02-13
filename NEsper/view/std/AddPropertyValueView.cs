using System;
using System.Collections.Generic;
using System.Collections.Specialized;

using net.esper.compat;
using net.esper.events;
using net.esper.view;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.view.std
{
    /// <summary>
    /// This view simply adds a property to the events posted to it. This is useful for the group-merge views.
    /// </summary>

    public sealed class AddPropertyValueView : ViewSupport, ContextAwareView
    {
        private ViewServiceContext viewServiceContext;
        private String[] propertyNames;
        private Object[] propertyValues;
        private EventType eventType;
        private bool mustAddProperty;

        /**
         * Empty constructor - required for Java bean.
         */
        public AddPropertyValueView()
        {
        }

        public ViewServiceContext ViewServiceContext
        {
            get
            {
                return viewServiceContext;
            }

            set
            {
                this.viewServiceContext = value;
            }
        }

        public override Viewable Parent
        {
            set
            {
                Viewable parent = value;

                if (log.IsDebugEnabled)
                {
                    log.Debug(".setParent parent=" + parent);
                }

                base.Parent = value;

                if (parent == null)
                {
                    return;
                }

                // If the parent event type contains the merge fields, we use the same event type
                if (parent.EventType.isProperty(propertyNames[0]))
                {
                    mustAddProperty = false;
                    eventType = parent.EventType;
                }
                // If the parent event type does not contain the event type (generates a map or such like the statistics views)
                // then we need to add in the merge field as an event property thus changing event types.
                else
                {
                    mustAddProperty = true;
                    Type[] propertyValueTypes = new Type[propertyValues.Length];
                    for (int i = 0; i < propertyValueTypes.Length; i++)
                    {
                        propertyValueTypes[i] = propertyValues[i].GetType();
                    }
                    eventType = viewServiceContext.EventAdapterService.CreateAddToEventType(
                            parent.EventType, propertyNames, propertyValueTypes);
                }
            }
        }

        /**
         * Constructor.
         * @param fieldNames is the name of the field that is added to any events received by this view.
         * @param mergeValues is the values of the field that is added to any events received by this view.
         */
        public AddPropertyValueView(String[] fieldNames, Object[] mergeValues)
        {
            this.propertyNames = fieldNames;
            this.propertyValues = mergeValues;
        }

        public override String AttachesTo(Viewable obj)
        {
            // Attaches to all views
            return null;
        }
        
        /// <summary>
        /// Gets or sets the field name for which to set the
        /// merge value for.
        /// </summary>

        public String[] PropertyNames
        {
        	get { return propertyNames; }
        	set { propertyNames = value ; }
        }

        /// <summary>
        /// Gets or sets the value to set for the field
        /// </summary>
        
        public Object[] PropertyValues
        {
        	get { return propertyValues; }
        	set { propertyValues = value ; }
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (!mustAddProperty)
            {
                updateChildren(newData, oldData);
                return;
            }

            EventBean[] newEvents = null;
            EventBean[] oldEvents = null;

            if (newData != null)
            {
                newEvents = new EventBean[newData.Length];

                int index = 0;
                foreach (EventBean newEvent in newData)
                {
                    EventBean ev = AddProperty(newEvent, propertyNames, propertyValues, eventType, viewServiceContext.EventAdapterService);
                    newEvents[index++] = ev;
                }
            }

            if (oldData != null)
            {
                oldEvents = new EventBean[oldData.Length];

                int index = 0;
                foreach (EventBean oldEvent in oldData)
                {
                    EventBean ev = AddProperty(oldEvent, propertyNames, propertyValues, eventType, viewServiceContext.EventAdapterService);
                    oldEvents[index++] = ev;
                }
            }

            updateChildren(newEvents, oldEvents);
        }

        public override EventType EventType
        {
            get { return eventType; }
            set { }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            IEnumerator<EventBean> parentIterator = parent.GetEnumerator() ;
            while (parentIterator.MoveNext())
            {
                EventBean nextEvent = parentIterator.Current;
                if (this.mustAddProperty)
                {
                    EventBean ev = AddProperty(
                        nextEvent,
                        this.PropertyNames,
                        this.PropertyValues,
                        this.eventType,
                        this.viewServiceContext.EventAdapterService);
                    yield return ev;
                }
                else
                {
                    yield return nextEvent;
                }
            }
        }

        /// <summary>
        /// Add a property to the event passed in.
        /// </summary>
        /// <param name="originalEvent">event to add property to</param>
        /// <param name="propertyNames">names of properties to add</param>
        /// <param name="propertyValues">value of properties to add</param>
        /// <param name="targetEventType">new event type</param>
        /// <param name="eventAdapterService">service for generating events and handling event types</param>param>
        /// <returns>event with added property</returns>

        public static EventBean AddProperty(
        	EventBean originalEvent,
            String[] propertyNames,
            Object[] propertyValues,
            EventType targetEventType,
            EventAdapterService eventAdapterService)
        {
            EDataDictionary values = new EDataDictionary();

            // Copy properties of original event, add property value
            foreach (String property in originalEvent.EventType.PropertyNames)
            {
                values[property] = originalEvent[property];
            }

            for (int i = 0; i < propertyNames.Length; i++)
            {
                values[propertyNames[i]] = propertyValues[i];
            }

            return eventAdapterService.CreateMapFromValues(values, targetEventType);
        }

        public override String ToString()
        {
            return
                this.GetType().Name +
                " propertyNames=" + CollectionHelper.Render(propertyNames) +
                " propertyValue=" + CollectionHelper.Render(propertyValues);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(AddPropertyValueView));
    }
}
