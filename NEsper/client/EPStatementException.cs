// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.Text

namespace net.esper.client
{
    /// <summary>
    /// This exception is thrown to indicate a problem in statement creation, such as syntax error or type
    /// checking problem etc.
    /// </summary>

    [Serializable]
    public class EPStatementException : EPException
    {
        private String expression;

        /// <summary>
        /// Gets or sets the expression text for statement.
        /// </summary>
        /// <value>The expression.</value>
        virtual public String Expression
        {
            get { return expression; }
            set { this.expression = value; }
        }

        /// <summary>
        /// Gets a message that describes the current exception.
        /// </summary>
        /// <value></value>
        /// <returns>The error message that explains the reason for the exception, or an empty string("").</returns>
        public override String Message
        {
            get
            {
                StringBuilder msg = new StringBuilder(base.Message);
                if (expression != null)
                {
					msg.Append( " [" ) ;
					msg.Append( expression ) ;
					msg.Append( ']' ) ;
                }
				
                return msg.ToString();
            }
        }

        /// <summary> Ctor.</summary>
        /// <param name="message">error message
        /// </param>
        /// <param name="expression">expression text
        /// </param>
        public EPStatementException(String message, String expression)
            : base(message)
        {
            this.expression = expression;
        }
    }
}