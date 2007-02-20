using System;

namespace net.esper.support.bean
{
	public enum SupportEnum
	{ 
		ENUM_VALUE_1, 
		ENUM_VALUE_2, 
		ENUM_VALUE_3
	}

    public class SupportEnumHelper
    {
        public static SupportEnum GetValueForEnum(int value)
        {
            return (SupportEnum)Enum.ToObject(typeof(SupportEnum), value);
        }
    }
}
