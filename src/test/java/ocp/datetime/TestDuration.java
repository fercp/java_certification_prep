package ocp.datetime;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDuration {
    @Test
    public void testDuration() {
        Duration daily = Duration.ofDays(1); // PT24H
        assertEquals("PT24H", daily.toString());
        Duration hourly = Duration.ofHours(1); // PT1H
        assertEquals("PT1H", hourly.toString());
        Duration everyMinute = Duration.ofMinutes(1); // PT1M
        assertEquals("PT1M", everyMinute.toString());
        Duration everyTenSeconds = Duration.ofSeconds(10); // PT10S
        assertEquals("PT10S", everyTenSeconds.toString());
        Duration everyMilli = Duration.ofMillis(1); // PT0.001S
        assertEquals("PT0.001S", everyMilli.toString());
        Duration everyNano = Duration.ofNanos(1); // PT0.000000001S
        assertEquals("PT0.000000001S", everyNano.toString());

        daily = Duration.of(1, ChronoUnit.DAYS);
        assertEquals("PT24H", daily.toString());
        hourly = Duration.of(1, ChronoUnit.HOURS);
        assertEquals("PT1H", hourly.toString());
        everyMinute = Duration.of(1, ChronoUnit.MINUTES);
        assertEquals("PT1M", everyMinute.toString());
        everyTenSeconds = Duration.of(10, ChronoUnit.SECONDS);
        assertEquals("PT10S", everyTenSeconds.toString());
        everyMilli = Duration.of(1, ChronoUnit.MILLIS);
        assertEquals("PT0.001S", everyMilli.toString());
        everyNano = Duration.of(1, ChronoUnit.NANOS);
        assertEquals("PT0.000000001S", everyNano.toString());
        Duration halfDay = Duration.of(1, ChronoUnit.HALF_DAYS);
        assertEquals("PT12H", halfDay.toString());
        daily = Duration.of(1, ChronoUnit.DAYS);
        assertEquals("PT24H", daily.toString());
        everyMinute = Duration.of(60, ChronoUnit.SECONDS);
        assertEquals("PT1M", everyMinute.toString());
        everyMinute = Duration.of(4500, ChronoUnit.SECONDS);
        assertEquals("PT1H15M", everyMinute.toString());
        assertThrows(UnsupportedTemporalTypeException.class, () -> Duration.of(1, ChronoUnit.WEEKS));
        assertThrows(UnsupportedTemporalTypeException.class, () -> Duration.of(1, ChronoUnit.MONTHS));

        Duration d = Duration.ofDays(1);
        assertEquals("PT24H",d.toString());
        d = Duration.ofMinutes(0);
        assertEquals("PT0S",d.toString());

    }

    @Test
    public void testChronoUnit() {
        LocalTime one = LocalTime.of(5, 15);
        LocalTime two = LocalTime.of(6, 30);
        LocalDate date = LocalDate.of(2016, 1, 20);
        assertEquals(1, ChronoUnit.HOURS.between(one, two));
        assertEquals(75, ChronoUnit.MINUTES.between(one, two));
        assertThrows(DateTimeException.class, () -> ChronoUnit.MINUTES.between(one, date));
        assertEquals("PT1H15M", Duration.between(one, two).toString());//positive
        assertEquals("PT-1H-15M", Duration.between(two, one).toString());//negative

        Duration tenYears = ChronoUnit.YEARS.getDuration().multipliedBy(10);
        Duration aDecade = ChronoUnit.DECADES.getDuration();
        assertEquals(tenYears, aDecade);
    }

}
