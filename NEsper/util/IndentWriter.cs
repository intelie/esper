using System;
using System.IO;

namespace net.esper.util
{
	/// <summary>
    /// Writer that uses an underlying PrintWriter to indent output text for easy reading.
    /// </summary>

    public class IndentWriter
    {
        private readonly TextWriter writer;
        private readonly int deltaIndent;
        private int currentIndent;

        /// <summary> Ctor.</summary>
        /// <param name="writer">to output to
        /// </param>
        /// <param name="StartIndent">is the depth of indent to Start
        /// </param>
        /// <param name="deltaIndent">is the number of characters to indent for every incrIndent() call
        /// </param>
        public IndentWriter(TextWriter writer, int StartIndent, int deltaIndent)
        {
            if (StartIndent < 0)
            {
                throw new ArgumentException("Invalid Start indent");
            }
            if (deltaIndent < 0)
            {
                throw new ArgumentException("Invalid delta indent");
            }

            this.writer = writer;
            this.deltaIndent = deltaIndent;
            this.currentIndent = StartIndent;
        }

        /// <summary> Increase the indentation one level.</summary>
        public virtual void incrIndent()
        {
            currentIndent += deltaIndent;
        }

        /// <summary> Decrease the indentation one level.</summary>
        public virtual void decrIndent()
        {
            currentIndent -= deltaIndent;
        }

        /// <summary> Print text to the underlying writer.</summary>
        /// <param name="text">to print
        /// </param>
        public virtual void WriteLine(String text)
        {
            int indent = currentIndent;
            if (indent < 0)
            {
                indent = 0;
            }

            writer.WriteLine(Indent.indent(indent) + text);
        }
    }
}