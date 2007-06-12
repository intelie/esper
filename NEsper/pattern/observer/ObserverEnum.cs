using System;

namespace net.esper.pattern.observer
{
    /// <summary>
    /// Enum for all build-in observers.
    /// </summary>
    
    public class ObserverEnum
    {
        /// <summary>
        /// Observer for letting pass/waiting an interval amount of time.
        /// </summary>
        public static readonly ObserverEnum TIMER_INTERVAL = new ObserverEnum("timer", "interval", typeof(TimerIntervalObserverFactory));

        /// <summary>
        /// Observer for 'at' (crontab) observation of timer events.
        /// </summary>
        public static readonly ObserverEnum TIMER_CRON = new ObserverEnum("timer", "at", typeof(TimerAtObserverFactory));

        /// <summary>
        /// All values available through this pseudo-enum
        /// </summary>
        
        public static readonly ObserverEnum[] Values = new ObserverEnum[]
        {
        	TIMER_INTERVAL,
        	TIMER_CRON
        };

        private readonly String nspace;
        private readonly String name;
        private readonly Type type;

        /// <summary>
        /// Constructor
        /// </summary>
        
        ObserverEnum(String nspace, String name, Type type)
        {
            this.nspace = nspace;
            this.name = name;
            this.type = type;
        }

        /// <summary>
        /// Gets the observer namespace name.
        /// </summary>
        /// <value>The observer namespace name.</value>

        public String Namespace
        {
            get { return nspace; }
        }

        /// <summary>
        /// Gets the name.
        /// </summary>
        /// <value>The name.</value>

        public String Name
        {
            get { return name; }
        }

        /// <summary>
        /// Gets the implementation clazz.
        /// </summary>
        /// <value>The implementation clazz.</value>

        public Type Type
        {
            get { return type; }
        }

		/// <summary>
		/// Returns observer enum for namespace name and observer name.
		/// </summary>
		/// <param name="nspace">namespace name</param>
		/// <param name="name">observer name</param>
        
        public static ObserverEnum ForName(String nspace, String name)
        {
            foreach (ObserverEnum observerEnum in Values)
            {
                if ((observerEnum.Namespace == nspace) &&
                    (observerEnum.Name == name))
                {
                    return observerEnum;
                }
            }

            return null;
        }
    }
}
