using System;
namespace net.esper.util
{
	/// <summary>
	/// Utility class around indenting and formatting text.
	/// </summary>

    public class Indent
    {
        /// <summary> Utility method to indent a text for a number of characters.</summary>
        /// <param name="numChars">is the number of character to indent with spaces
        /// </param>
        /// <returns> the formatted string
        /// </returns>

        public static String CreateIndent(int numChars)
        {
            if (numChars < 0)
            {
                throw new ArgumentException("Number of characters less then zero");
            }

            char[] buf = new char[numChars];
            for (int ii = 0; ii < buf.Length; ii++)
            {
                buf[ii] = ' ';
            }

            return (new String(buf));
        }
    }
}
