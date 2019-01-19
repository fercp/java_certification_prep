package ocp.i18nl10n;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLocale {
    @Test
    public void testLocale(){
        Locale locale=new Locale("DE");
        assertEquals("de",locale.getLanguage());
        assertEquals("German",locale.getDisplayLanguage());
        Locale l1 = new Locale.Builder()
                .setLanguage("en")
                .setRegion("US")
                .build();
        assertEquals("en",l1.getLanguage());
        assertEquals("United States",l1.getDisplayCountry());


    }
}
