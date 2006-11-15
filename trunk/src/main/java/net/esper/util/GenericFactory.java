package net.esper.util;

/**
 * Factory for an instance of any type. Employs Class.newInstance to instantiate.
 */
public class GenericFactory<T>
{
    private Class<T> clazz;

    /**
     * Ctor.
     * @param clazz Class of which instace must be created
     */
    public GenericFactory(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * Create instance of class.
     * @return instance
     * @throws IllegalAccessException is thrown by Class.newInstance
     * @throws InstantiationException is thrown by Class.newInstance
     */
    public T create() throws IllegalAccessException, InstantiationException
    {
        return clazz.newInstance();
    }
}
