package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.Month;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

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
        LocalDate date = LocalDate.parse(str, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return "«" + date.getDayOfMonth() + "» " + Month.fromId(date.getMonthValue()).getName(language) + " " + date.getYear();
    }

    public static String getStringDateFromDate(LocalDate localDate, Language language){
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return day + " " + Month.fromId(month).getName(language) + " " + year;
    }

}
