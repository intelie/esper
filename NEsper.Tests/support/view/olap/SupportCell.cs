using System;

namespace net.esper.support.view.olap
{
	
	public class SupportCell
	{
		virtual public int CellValue
		{
			get { return cellValue; }
			set { this.cellValue = value; }
		}

		private int cellValue;
		
		public SupportCell()
		{
		}
	}
}
