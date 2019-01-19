package ocp.datetime;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDayLightSaving {
    @Test
    public void testDayLightSaving(){
        LocalDate date = LocalDate.of(2016, Month.MARCH, 13);
        LocalTime time = LocalTime.of(1, 30);
        ZoneId zone = ZoneId.of("US/Eastern");
        ZonedDateTime dateTime = ZonedDateTime.of(date, time, zone);
        assertEquals("2016-03-13T01:30-05:00[US/Eastern]",dateTime.toString());
        dateTime = dateTime.plusHours(1);
        assertEquals("2016-03-13T03:30-04:00[US/Eastern]",dateTime.toString());
        time=LocalTime.of(2,30);
        dateTime = ZonedDateTime.of(date, time, zone);
        assertEquals("2016-03-13T03:30-04:00[US/Eastern]",dateTime.toString());

        date = LocalDate.of(2016, Month.NOVEMBER, 6);
        time = LocalTime.of(1, 30);
        dateTime = ZonedDateTime.of(date, time, zone);
        assertEquals("2016-11-06T01:30-04:00[US/Eastern]",dateTime.toString());  
        dateTime = dateTime.plusHours(1);
        assertEquals("2016-11-06T01:30-05:00[US/Eastern]",dateTime.toString());
        dateTime = dateTime.plusHours(1);
        assertEquals("2016-11-06T02:30-05:00[US/Eastern]",dateTime.toString());
    }
}
