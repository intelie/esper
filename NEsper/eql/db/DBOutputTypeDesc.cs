using System;

namespace net.esper.eql.db
{
	/// <summary>
    /// Descriptor for SQL output columns.
    /// </summary>
	
    public class DBOutputTypeDesc
	{
		/// <summary> Returns the SQL type of the output column.</summary>
		/// <returns> sql type
		/// </returns>
		virtual public int SqlType
		{
			get { return sqlType; }
		}

		/// <summary> Returns the type that getObject on the output column produces.</summary>
		/// <returns> type from statement metadata
		/// </returns>
		
        virtual public Type DataType
		{
			get { return dataType; }
		}
		
        private int sqlType;
		private Type dataType ;
		
		/// <summary> Ctor.</summary>
		/// <param name="sqlType">the type of the column</param>
		/// <param name="dataType">the type reflecting column type</param>
		
        public DBOutputTypeDesc(int sqlType, Type dataType)
		{
			this.sqlType = sqlType;
            this.dataType = dataType;
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return "type=" + sqlType + " dataType=" + dataType;
		}
	}
}