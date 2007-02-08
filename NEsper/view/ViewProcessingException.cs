using System;

namespace net.esper.view
{
    /// <summary>
    /// This exception is thrown to indicate a problem with a view expression.
    /// </summary>

    [Serializable]
    public sealed class ViewProcessingException : Exception
    {
        /// <summary> Constructor.</summary>
        /// <param name="message">is the error message
        /// </param>

        public ViewProcessingException(String message)
            : base(message)
        {
        }

        /// <summary> Constructor for an inner exception and message.</summary>
        /// <param name="message">is the error message
        /// </param>
        /// <param name="cause">is the inner exception
        /// </param>

        public ViewProcessingException(String message, Exception cause)
            : base(message, cause)
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="cause">is the inner exception
        /// </param>

        public ViewProcessingException(Exception cause)
            : base("", cause)
        {
        }
    }
}