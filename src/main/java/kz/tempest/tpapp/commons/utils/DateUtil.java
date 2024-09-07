package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.Month;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter PATTERN_DASH_DD_MM_YYYY = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter PATTERN_DASH_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter PATTERN_DASH_MM_DD_YYYY = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    public static final DateTimeFormatter PATTERN_DOT_DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter PATTERN_DOT_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public static final DateTimeFormatter PATTERN_DOT_MM_DD_YYYY = DateTimeFormatter.ofPattern("MM.dd.yyyy");
    public static final DateTimeFormatter PATTERN_DASH_DD_MM_YYYY_HH_MM = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static final DateTimeFormatter PATTERN_DASH_YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter PATTERN_DASH_MM_DD_YYYY_HH_MM = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
    public static final DateTimeFormatter PATTERN_DOT_DD_MM_YYYY_HH_MM = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static final DateTimeFormatter PATTERN_DOT_YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    public static final DateTimeFormatter PATTERN_DOT_MM_DD_YYYY_HH_MM = DateTimeFormatter.ofPattern("MM.dd.yyyy HH:mm");
    public static final DateTimeFormatter PATTERN_DASH_DD_MM_YYYY_HH_MM_SS = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final DateTimeFormatter PATTERN_DASH_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter PATTERN_DASH_MM_DD_YYYY_HH_MM_SS = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static final DateTimeFormatter PATTERN_DOT_DD_MM_YYYY_HH_MM_SS = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static final DateTimeFormatter PATTERN_DOT_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    public static final DateTimeFormatter PATTERN_DOT_MM_DD_YYYY_HH_MM_SS = DateTimeFormatter.ofPattern("MM.dd.yyyy HH:mm:ss");


    public static String getStringDateTimeFromDateTime(String str, Language language){
        return getStringDateTimeFromDateTime(str, null, language);
    }

    public static String getStringDateTimeFromDateTime(String str, DateTimeFormatter formatter, Language language){
        if (formatter == null) {
            return getStringDateTimeFromDateTime(LocalDateTime.parse(str), language);
        }
        return getStringDateTimeFromDateTime(LocalDateTime.parse(str, formatter), language);
    }

    public static String getStringDateTimeFromDateTime(LocalDateTime localDateTime, Language language){
        int day = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        return day + " " + Month.fromId(month).getName(language) + " "
                + year + " " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

    public static String modifyStringDateForDocument(String str, Language language) {
        return modifyStringDateForDocument(str, null, language);
    }

    public static String modifyStringDateForDocument(String str, DateTimeFormatter formatter, Language language) {
        if (formatter == null) {
            return modifyStringDateForDocument(LocalDate.parse(str), language);
        }
        return modifyStringDateForDocument(LocalDate.parse(str, formatter), language);
    }

    public static String modifyStringDateForDocument(LocalDate date, Language language) {
        return "«" + date.getDayOfMonth() + "» " + Month.fromId(date.getMonthValue()).getName(language) + " " + date.getYear();
    }

    public static String getStringDateFromDate(String str, Language language){
        return getStringDateFromDate(str, null, language);
    }

    public static String getStringDateFromDate(String str, DateTimeFormatter formatter, Language language){
        if (formatter == null) {
            return getStringDateFromDate(LocalDate.parse(str), language);
        }
        return getStringDateFromDate(LocalDate.parse(str, formatter), language);
    }

    public static String getStringDateFromDate(LocalDate localDate, Language language){
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return day + " " + Month.fromId(month).getName(language) + " " + year;
    }

}
