package com.espertech.esper.epl.datetime;

import com.espertech.esper.epl.methodbase.DotMethodFP;

public enum DatetimeMethodEnum {

    // calendar ops
    WITHTIME("withTime", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.WITHTIME),
    WITHDATE("withDate", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.WITHDATE),
    PLUS("plus", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.PLUSMINUS),
    MINUS("minus", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.PLUSMINUS),
    WITHMAX("withMax", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.CALFIELD),
    WITHMIN("withMin", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.CALFIELD),
    SET("set", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.CALFIELD_PLUS_INT),
    ROUNDCEILING("roundCeiling", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.CALFIELD),
    ROUNDFLOOR("roundFloor", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.CALFIELD),
    ROUNDHALF("roundHalf", DatetimeMethodEnumStatics.calendarOpFactory, DatetimeMethodEnumParams.CALFIELD),

    // reformat ops
    GET("get", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.CALFIELD),
    FORMAT("format", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    TOCALENDAR("toCalendar", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    TODATE("toDate", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    TOMILLISEC("toMillisec", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETMINUTEOFHOUR("getMinuteOfHour", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETMONTHOFYEAR("getMonthOfYear", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETDAYOFMONTH("getDayOfMonth", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETDAYOFWEEK("getDayOfWeek", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETDAYOFYEAR("getDayOfYear", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETERA("getEra", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETHOUROFDAY("getHourOfDay", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETMILLISOFSECOND("getMillisOfSecond", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETSECONDOFMINUTE("getSecondOfMinute", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETWEEKYEAR("getWeekyear", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM),
    GETYEAR("getYear", DatetimeMethodEnumStatics.reformatOpFactory, DatetimeMethodEnumParams.NOPARAM)
    ;

    private final String nameCamel;
    private final OpFactory opFactory;
    private DotMethodFP[] footprints;

    private DatetimeMethodEnum(String nameCamel, OpFactory opFactory, DotMethodFP[] footprints) {
        this.nameCamel = nameCamel;
        this.opFactory = opFactory;
        this.footprints = footprints;
    }

    public OpFactory getOpFactory() {
        return opFactory;
    }

    public String getNameCamel() {
        return nameCamel;
    }

    public static boolean isDateTimeMethod(String name) {
        for (DatetimeMethodEnum e : DatetimeMethodEnum.values()) {
            if (e.getNameCamel().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static DatetimeMethodEnum fromName(String name) {
        for (DatetimeMethodEnum e : DatetimeMethodEnum.values()) {
            if (e.getNameCamel().toLowerCase().equals(name.toLowerCase())) {
                return e;
            }
        }
        return null;
    }

    public DotMethodFP[] getFootprints() {
        return footprints;
    }
}
