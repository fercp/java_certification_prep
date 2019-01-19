package ocp.i18nl10n;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestNumberFormat {
    @Test
    public void testNumberFormat() throws ParseException {
        int attendeesPerYear = 3_200_000;
        int attendeesPerMonth = attendeesPerYear / 12;
        NumberFormat us = NumberFormat.getInstance(Locale.US);
        assertEquals("266,666",us.format(attendeesPerMonth));
        NumberFormat g = NumberFormat.getInstance(Locale.GERMANY);
        assertEquals("266.666",g.format(attendeesPerMonth));
        NumberFormat ca = NumberFormat.getNumberInstance(Locale.CANADA_FRENCH);
        assertEquals("266 666",ca.format(attendeesPerMonth));
        double price = 48;
        us=NumberFormat.getCurrencyInstance(Locale.US);
        assertEquals("$48.00",us.format(price));
        assertEquals(48L,us.parse(us.format(price)));

        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        String one = "456abc";
        String two = "-2.5165x10";
        String three = "x85.3";
        assertEquals(456L,nf.parse(one));
        assertEquals(-2.5165,nf.parse(two)); // -2.5165
        assertThrows(ParseException.class,()->nf.parse(three));// throws ParseException
    }
}
