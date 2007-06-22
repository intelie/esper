using System;

namespace net.esper.support.util
{
    /// <summary>
    /// An object that has a method that can be called and returns a value.
    /// </summary>

    public interface Callable
    {
        Object Call();
    }

    public delegate Object CallableDelegate() ;

    public class CallableImpl : Callable
    {
        public CallableImpl(CallableDelegate @delegate)
        {
            this.@delegate = @delegate;
        }

        private CallableDelegate @delegate;

        public object Call()
        {
            return @delegate();
        }
    }
}
