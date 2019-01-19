package ocp.datetime;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPeriod {
    @Test
    public void testPeriod() {
        Period annually = Period.ofYears(1); // every 1 year
        assertEquals("P1Y", annually.toString());
        Period quarterly = Period.ofMonths(3); // every 3 months
        assertEquals("P3M", quarterly.toString());
        Period everyThreeWeeks = Period.ofWeeks(3); // every 3 weeks
        assertEquals("P21D", everyThreeWeeks.toString());
        Period everyOtherDay = Period.ofDays(2); // every 2 days
        assertEquals("P2D", everyOtherDay.toString());
        Period everyYearAndAWeek = Period.of(1, 0, 7); // every year and 7 days
        assertEquals("P1Y7D", everyYearAndAWeek.toString());
        Period full=Period.of(1,2,3);
        assertEquals("P1Y2M3D", full.toString());
        annually=Period.of(0,36,0);
        assertEquals("P36M", annually.toString()); // not converted to years

        Period p = Period.ofMonths(0);
        assertEquals("P0D",p.toString());
    }

    @Test
    public void testPeriodAddMinus() {
        LocalDate date = LocalDate.of(2015, 1, 20);
        LocalDate date2 = LocalDate.of(2016, 3, 23);
        LocalTime time = LocalTime.of(6, 15);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        ZoneId zoneId = ZoneId.of("US/Eastern");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneId);
        Period period = Period.ofMonths(1);
        Period full=Period.of(1,2,3);
        assertEquals("2015-02-20",date.plus(period).toString());
        assertEquals("P-1Y-2M-3D",Period.between(date2,date).toString());
        assertEquals("2015-02-20T06:15",dateTime.plus(period).toString());
        assertEquals("2015-02-20T06:15-05:00[US/Eastern]",zonedDateTime.plus(period).toString());
        assertEquals("2016-03-23T06:15-04:00[US/Eastern]",zonedDateTime.plus(full).toString());
        assertThrows(UnsupportedTemporalTypeException.class,()->time.plus(period));
    }
}
