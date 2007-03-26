using System;

namespace net.esper.pattern.guard
{
    /// <summary> 
    /// Enum for all build-in guards.
    /// </summary>

    public class GuardEnum
    {
        /// <summary>
        /// Timer guard.
        /// </summary>
        
        public static readonly GuardEnum TIMER_WITHIN = new GuardEnum("timer", "within", typeof(TimerWithinGuardFactory));

        /// <summary>
        /// All values available through this pseudo-enum
        /// </summary>
        
        public static readonly GuardEnum[] Values = new GuardEnum[]
        {
        	TIMER_WITHIN
        };
        
        private readonly String nspace;
        private readonly String name;
        private readonly Type clazz;

        /// <summary>
        /// Initializes a new instance of the <see cref="GuardEnum"/> class.
        /// </summary>
        /// <param name="nspace">The nspace.</param>
        /// <param name="name">The name.</param>
        /// <param name="clazz">The clazz.</param>
        GuardEnum(String nspace, String name, Type clazz)
        {
            this.nspace = nspace;
            this.name = name;
            this.clazz = clazz;
        }

        /// <summary>
        /// Gets the namespace.
        /// </summary>
        /// <value>The namespace.</value>
        
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
        
        public Type Clazz
        {
            get { return clazz; }
        }

        /// <summary>Returns the enum for the given namespace and name.</summary>
        /// <param name="nspace">guard namespace</param>
        /// <param name="name">guard name</param>
        /// <returns>enum</returns>

        public static GuardEnum forName(String nspace, String name)
        {
            foreach (GuardEnum guardEnum in GuardEnum.Values)
            {
                if ((guardEnum.Namespace.Equals(nspace)) && (guardEnum.Name.Equals(name)))
                {
                    return guardEnum;
                }
            }

            return null;
        }
    }
}
