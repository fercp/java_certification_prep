package ocp.io;

import org.junit.jupiter.api.Test;

import java.io.Console;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TestConsole {
    @Test
    public void testConsole() {
        Console console = System.console();
        assertNull(console);
        if (console != null) {
            console.writer().format(new Locale("fr", "CA"), "Hello World");
            console.writer().printf(new Locale("fr", "CA"), "Hello World");
            console.writer().println();
            console.format("%d", 1);
            console.printf("%d", 2);
            char[] password = console.readPassword();

            console.flush();
            console.readLine();

        }
        LocalDateTime ldt = LocalDateTime.of(2017, 12, 02, 6, 0, 0);
        ZonedDateTime nyZdt = ldt.atZone(TimeZone.getTimeZone("America/New_York").toZoneId());
        ZonedDateTime laZdt = ldt.atZone(TimeZone.getTimeZone("America/Los_Angeles").toZoneId());
        Duration d = Duration.between(nyZdt, laZdt);
        System.out.println(d);
    }
}
