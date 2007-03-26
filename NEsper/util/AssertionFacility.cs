using System;
namespace net.esper.util
{
	
	/// <summary> Simple facility for asserting at runtime.</summary>
    public class AssertionFacility
    {
        /// <summary> Assert the value is true and raise exception if false.</summary>
        /// <param name="value">boolean to check
        /// </param>
        /// <param name="message">message to place in exception
        /// </param>
        /// <throws>  AssertionException thrown if value is false </throws>
        public static void AssertTrue(bool value, String message)
        {
            if (!value)
            {
                throw new AssertionException(message);
            }
        }

        /// <summary> Assert the value is false and raise exception if true.</summary>
        /// <param name="value">boolean to check
        /// </param>
        /// <param name="message">message to place in exception
        /// </param>
        /// <throws>  AssertionException thrown if value is true </throws>
        public static void AssertFalse(bool value, String message)
        {
            if (value)
            {
                throw new AssertionException(message);
            }
        }
    }
}