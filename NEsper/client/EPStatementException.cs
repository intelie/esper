/// <summary>***********************************************************************************
/// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
/// http://esper.codehaus.org                                                          *
/// ---------------------------------------------------------------------------------- *
/// The software in this package is published under the terms of the GPL license       *
/// a copy of which has been included with this distribution in the license.txt file.  *
/// ************************************************************************************
/// </summary>
using System;
namespace net.esper.client
{
	/// <summary> This exception is thrown to indicate a problem in statement creation, such as syntax error or type checking problem
	/// etc.
	/// </summary>
	
    [Serializable]
	public class EPStatementException:EPException
	{
        private String expression;
        
        /// <summary> Returns expression text for statement.</summary>
		/// <returns> expression text
		/// </returns>
		/// <summary> Sets expression text for statement.</summary>
		/// <param name="expression">text
		/// </param>
		virtual public String Expression
		{
			get { return expression; }
			set { this.expression = value; }
		}
		
        public override String Message
		{
			get
			{
				String msg = base.Message;
				if (expression != null)
				{
					msg += (" [" + expression + ']');
				}
				return msg;
			}
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="message">- error message
		/// </param>
		/// <param name="expression">- expression text
		/// </param>
		public EPStatementException(String message, String expression):base(message)
		{
			this.expression = expression;
		}
	}
}