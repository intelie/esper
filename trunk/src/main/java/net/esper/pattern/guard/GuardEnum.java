package net.esper.pattern.guard;

/**
 * Enum for all build-in guards.
 */
public enum GuardEnum
{
    /**
     * Timer guard.
     */
    TIMER_WITHIN("timer", "within", TimerWithinGuardFactory.class);

    private final String namespace;
    private final String name;
    private final Class clazz;

    GuardEnum(String namespace, String name, Class clazz)
    {
        this.namespace = namespace;
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * Returns the namespace name.
     * @return namespace name
     */
    public String getNamespace()
    {
        return namespace;
    }

    /**
     * Returns name.
     * @return short name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the implementation class.
     * @return implementation class
     */
    public Class getClazz()
    {
        return clazz;
    }

    /**
     * Returns the enum for the given namespace and name.
     * @param namespace - guard namespace
     * @param name - guard name
     * @return enum
     */
    public static GuardEnum forName(String namespace, String name)
    {
        for (GuardEnum guardEnum : GuardEnum.values())
        {
            if ((guardEnum.getNamespace().equals(namespace)) && (guardEnum.getName().equals(name)))
            {
                return guardEnum;
            }
        }

        return null;
    }
}