package ocp.datetime;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestDateTime {
    @Test
    public void testLocalDate() {
        LocalDate localDate = LocalDate.of(2018, 1, 30);
        assertEquals("2018-01-30", localDate.toString());
        localDate = LocalDate.of(2018, Month.JANUARY, 30);
        assertEquals("2018-01-30", localDate.toString());
        assertThrows(DateTimeException.class, () -> LocalDate.of(2015, Month.JANUARY, 32));
        localDate=localDate.plusDays(2);
        assertEquals("2018-02-01", localDate.toString());
        localDate=localDate.plus(2,ChronoUnit.DAYS);
        assertEquals("2018-02-03", localDate.toString());
        LocalDate localDateCopy=localDate;
        assertThrows(UnsupportedTemporalTypeException.class,()->localDateCopy.plus(2,ChronoUnit.HALF_DAYS));
        localDate=localDate.plus(1,ChronoUnit.WEEKS);
        assertEquals("2018-02-10", localDate.toString());
        Temporal temporal=TemporalAdjusters.firstDayOfMonth().adjustInto(localDate);
        assertEquals("2018-02-01",temporal.toString());
        localDate=localDate.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals("2018-02-28",localDate.toString());
        localDate=localDate.with(ChronoField.DAY_OF_MONTH,15);
        assertEquals("2018-02-15",localDate.toString());
        localDate=localDate.plus(1,ChronoUnit.WEEKS);
        assertEquals("2018-02-22",localDate.toString());
        assertEquals(6,localDate.until(LocalDate.of(2018,2,28),ChronoUnit.DAYS));
        assertThrows(UnsupportedTemporalTypeException.class,()->localDateCopy.until(LocalDate.of(2018,2,28),ChronoUnit.HALF_DAYS));
    }

    @Test
    public void testLocalTime() {
        LocalTime localTime = LocalTime.of(12, 30);
        assertEquals("12:30", localTime.toString());
        localTime = LocalTime.of(12, 30, 45);
        assertEquals("12:30:45", localTime.toString());
        localTime = LocalTime.of(12, 30, 45, 15);
        assertEquals("12:30:45.000000015", localTime.toString());
        assertThrows(DateTimeException.class, () -> LocalTime.of(25, 30));
        localTime=localTime.minusHours(2);
        assertEquals("10:30:45.000000015",localTime.toString());
        localTime=localTime.plusHours(2).plusMinutes(15).plusSeconds(15);
        assertEquals("12:46:00.000000015",localTime.toString());
        LocalTime current=LocalTime.now();
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 30, 12, 30, 45, 15);
        assertEquals("2018-01-30T12:30:45.000000015", localDateTime.toString());
        LocalDate localDate = LocalDate.of(2018, 1, 30);
        LocalTime localTime = LocalTime.of(12, 30, 45, 15);
        localDateTime = LocalDateTime.of(localDate, localTime);
        assertEquals("2018-01-30T12:30:45.000000015", localDateTime.toString());
        LocalDateTime.parse("2018-01-3T12:30:45.000000015");
    }

    @Test
    public void testZonedDateTime() {
        ZoneId zoneId = ZoneId.of("US/Eastern");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2018, 1, 30, 12, 30, 45, 15, zoneId);
        assertEquals("2018-01-30T12:30:45.000000015-05:00[US/Eastern]", zonedDateTime.toString());
        LocalDate localDate = LocalDate.of(2018, 1, 30);
        LocalTime localTime = LocalTime.of(12, 30, 45, 15);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        assertEquals("2018-01-30T12:30:45.000000015-05:00[US/Eastern]", zonedDateTime.toString());
        assertEquals("-05:00", zoneId.getRules().getOffset(localDateTime).toString());
        assertThrows(DateTimeParseException.class,()->ZonedDateTime.parse("2018-01-30T12:30:45"));
        assertEquals("2018-01-30T17:30:45.000000015Z[GMT]",zonedDateTime.withZoneSameInstant(ZoneId.of("GMT")).toString()) ;
        assertEquals("2018-01-30T17:30:45.000000015Z[GMT]",ZonedDateTime.ofInstant(zonedDateTime.toInstant(),ZoneId.of("GMT")).toString());
    }

    @Test
    public void testMonthDay(){
        LocalDate dateOfBirth = LocalDate.of(1988, Month.NOVEMBER, 4);
        MonthDay monthDay =
                MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        boolean ifTodayBirthday =
                monthDay.equals(MonthDay.from(LocalDate.of(2005,Month.NOVEMBER,4)));
        assertTrue(ifTodayBirthday );
    }

}
