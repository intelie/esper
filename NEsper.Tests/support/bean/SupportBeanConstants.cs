using System;

namespace net.esper.support.bean
{
    public class SupportBeanConstants
    {
        public readonly static String EVENT_BEAN_PACKAGE = "net.esper.support.bean.";
        public readonly static String EVENT_A_CLASS;
        public readonly static String EVENT_B_CLASS;
        public readonly static String EVENT_C_CLASS;
        public readonly static String EVENT_D_CLASS;
        public readonly static String EVENT_E_CLASS;
        public readonly static String EVENT_F_CLASS;
        public readonly static String EVENT_G_CLASS;

        static SupportBeanConstants()
        {
            EVENT_A_CLASS = EVENT_BEAN_PACKAGE + "SupportBean_A";
            EVENT_B_CLASS = EVENT_BEAN_PACKAGE + "SupportBean_B";
            EVENT_C_CLASS = EVENT_BEAN_PACKAGE + "SupportBean_C";
            EVENT_D_CLASS = EVENT_BEAN_PACKAGE + "SupportBean_D";
            EVENT_E_CLASS = EVENT_BEAN_PACKAGE + "SupportBean_E";
            EVENT_F_CLASS = EVENT_BEAN_PACKAGE + "SupportBean_F";
            EVENT_G_CLASS = EVENT_BEAN_PACKAGE + "SupportBean_G";
        }
    }
}
