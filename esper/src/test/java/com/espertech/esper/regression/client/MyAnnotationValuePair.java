package com.espertech.esper.regression.client;

public @interface MyAnnotationValuePair
{
    String stringVal();
    byte byteVal();
    short shortVal();
    int intVal();
    long longVal();
    boolean booleanVal();
    char charVal();
    double doubleVal();

    String stringValDef() default "def";
    int intValDef() default 100;
    long longValDef() default 200;
    boolean booleanValDef() default true;
    char charValDef() default 'D';
    double doubleValDef() default 1.1;
}
