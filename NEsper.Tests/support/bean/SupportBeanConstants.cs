using System;

namespace net.esper.support.bean
{
	
	public struct SupportBeanConstants_Fields{
		public readonly static String EVENT_BEAN_PACKAGE = "net.esper.support.bean.";
		public readonly static String EVENT_A_CLASS;
		public readonly static String EVENT_B_CLASS;
		public readonly static String EVENT_C_CLASS;
		public readonly static String EVENT_D_CLASS;
		public readonly static String EVENT_E_CLASS;
		public readonly static String EVENT_F_CLASS;
		public readonly static String EVENT_G_CLASS;
		static SupportBeanConstants_Fields()
		{
			EVENT_A_CLASS = net.esper.support.bean.SupportBeanConstants_Fields.EVENT_BEAN_PACKAGE + "SupportBean_A";
			EVENT_B_CLASS = net.esper.support.bean.SupportBeanConstants_Fields.EVENT_BEAN_PACKAGE + "SupportBean_B";
			EVENT_C_CLASS = net.esper.support.bean.SupportBeanConstants_Fields.EVENT_BEAN_PACKAGE + "SupportBean_C";
			EVENT_D_CLASS = net.esper.support.bean.SupportBeanConstants_Fields.EVENT_BEAN_PACKAGE + "SupportBean_D";
			EVENT_E_CLASS = net.esper.support.bean.SupportBeanConstants_Fields.EVENT_BEAN_PACKAGE + "SupportBean_E";
			EVENT_F_CLASS = net.esper.support.bean.SupportBeanConstants_Fields.EVENT_BEAN_PACKAGE + "SupportBean_F";
			EVENT_G_CLASS = net.esper.support.bean.SupportBeanConstants_Fields.EVENT_BEAN_PACKAGE + "SupportBean_G";
		}
	}
	public interface SupportBeanConstants
	{
		//UPGRADE_NOTE: Members of interface 'SupportBeanConstants' were extracted into structure 'SupportBeanConstants_Fields'. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1045'"
		
	}
}
