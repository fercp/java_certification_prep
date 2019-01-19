package ocp.i18nl10n;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDateFormat {
    @Test
    public void testFormatDate() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
        LocalTime time = LocalTime.of(11, 12, 34);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        assertEquals("2020-01-20", date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        assertEquals("11:12:34", time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        assertEquals("2020-01-20T11:12:34", dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        Locale.setDefault(Locale.US);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        assertThrows(UnsupportedTemporalTypeException.class, () -> dateTimeFormatter.format(date));
        assertEquals("1/20/20 11:12 AM", dateTimeFormatter.format(dateTime));
        assertThrows(UnsupportedTemporalTypeException.class, () -> dateTimeFormatter.format(time));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        assertEquals("1/20/20", dateFormatter.format(date));
        assertEquals("1/20/20", dateFormatter.format(dateTime));
        assertThrows(UnsupportedTemporalTypeException.class, () -> dateFormatter.format(time));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        assertThrows(UnsupportedTemporalTypeException.class, () -> timeFormatter.format(date));
        assertEquals("11:12 AM", timeFormatter.format(dateTime));
        assertEquals("11:12 AM", timeFormatter.format(time));

        DateTimeFormatter mediumF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        assertEquals("Jan 20, 2020 11:12:34 AM", mediumF.format(dateTime));

        DateTimeFormatter full = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a");
        assertEquals("January 20, 2020, 11:12 AM", full.format(dateTime));

        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM dd yyyy");
        LocalDate date2 = LocalDate.parse("01 02 2015", f);
        LocalTime time2 = LocalTime.parse("11:22");
        assertEquals("2015-01-02", date2.toString()); // 2015–01–02
        assertEquals("11:22", time2.toString()); // 11:22

        f = DateTimeFormatter.ofPattern("'holiday' MM dd yyyy");
        assertEquals("holiday 01 02 2015",date2.format(f));
    }
}
