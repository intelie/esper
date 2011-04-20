package com.espertech.esper.support.bean;

public class SupportBeanStaticInner {

    public SupportBeanStaticInnerTwo getInsideTwo() {
        return new SupportBeanStaticInnerTwo();
    }
    
    public static String getMyString() {
        return "hello";
    }
}
