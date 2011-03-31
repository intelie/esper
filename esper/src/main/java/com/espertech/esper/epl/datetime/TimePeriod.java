package com.espertech.esper.epl.datetime;

public class TimePeriod {
    private Integer years;
    private Integer months;
    private Integer weeks;
    private Integer days;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;
    private Integer milliseconds;

    public TimePeriod(Integer years, Integer months, Integer weeks, Integer days, Integer hours, Integer minutes, Integer seconds, Integer milliseconds) {
        this.years = years;
        this.months = months;
        this.weeks = weeks;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }

    public Integer getYears() {
        return years;
    }

    public Integer getMonths() {
        return months;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public Integer getDays() {
        return days;
    }

    public Integer getHours() {
        return hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public Integer getMilliseconds() {
        return milliseconds;
    }
}