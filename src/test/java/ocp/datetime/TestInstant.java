package ocp.datetime;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestInstant {
    @Test
    public void testInstant() {
        ZoneId zoneId = ZoneId.of("US/Eastern");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2018, 1, 30, 12, 30, 45, 15, zoneId);
        Instant instant = zonedDateTime.toInstant();
        assertEquals("2018-01-30T17:30:45.000000015Z", instant.toString());
        Instant nextDay = instant.plus(1, ChronoUnit.DAYS);
        assertEquals("2018-01-31T17:30:45.000000015Z", nextDay.toString());
        Instant nextHour = instant.plus(1, ChronoUnit.HOURS);
        assertEquals("2018-01-30T18:30:45.000000015Z", nextHour.toString());
        assertThrows(UnsupportedTemporalTypeException.class, () -> instant.plus(1, ChronoUnit.WEEKS)); // exception
        nextHour=nextHour.plus(Duration.of(1,ChronoUnit.HOURS));
        assertEquals("2018-01-30T19:30:45.000000015Z",nextHour.toString());
        nextDay=nextDay.plus(Period.ofDays(1));
        assertEquals("2018-02-01T17:30:45.000000015Z", nextDay.toString());
        Instant nextDay2=nextDay.plus(Period.ofWeeks(1));
        assertEquals("2018-02-08T17:30:45.000000015Z", nextDay2.toString());
        assertThrows(UnsupportedTemporalTypeException.class,()->nextDay2.plus(Period.ofMonths(1)));
    }

}
