package com.espertech.esper.regression.client;

import com.espertech.esper.support.bean.SupportEnum;

public @interface MyAnnotationValueEnum
{
    SupportEnum supportEnum();
    SupportEnum supportEnumDef() default SupportEnum.ENUM_VALUE_2;
}
