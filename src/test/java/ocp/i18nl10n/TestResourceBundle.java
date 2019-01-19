package ocp.i18nl10n;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestResourceBundle {
    @Test
    public void testResourceBundle() {
        Locale locale = new Locale("en", "US");
        Locale.setDefault(locale);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ocp.i18nl10n.E");
        assertEquals("e", resourceBundle.getString("a"));
        resourceBundle = ResourceBundle.getBundle("ocp.i18nl10n.E", new Locale("fr"));
        assertEquals("3", resourceBundle.getString("a"));
        resourceBundle = ResourceBundle.getBundle("ocp.i18nl10n.E", new Locale("tr"));
        assertEquals("e", resourceBundle.getString("a"));
        Locale.setDefault(Locale.GERMAN);
        resourceBundle = ResourceBundle.getBundle("ocp.i18nl10n.E", new Locale("tr"));
        assertEquals("1", resourceBundle.getString("a"));
        assertEquals("1", resourceBundle.getObject("a"));
        assertEquals("1", resourceBundle.getObject("b"));
        Locale.setDefault(locale);
        resourceBundle = ResourceBundle.getBundle("ocp.i18nl10n.E", locale);
        assertEquals("e", resourceBundle.getString("a"));
        assertEquals("1", resourceBundle.getObject("b"));
        Locale.setDefault(new Locale("tr","TR"));
        resourceBundle = ResourceBundle.getBundle("ocp.i18nl10n.E", Locale.GERMAN);
        assertEquals("t", resourceBundle.getString("a"));
    }

    @Test
    public void testProperties() {
        Locale.setDefault(new Locale("tr_TR"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ocp.i18nl10n.E");
        Properties properties = new Properties();
        resourceBundle.keySet().forEach(k -> properties.put(k, resourceBundle.getString(k)));
        assertEquals("1", properties.getProperty("a"));
        assertEquals("6", properties.getProperty("e", "6"));
        assertEquals("1", properties.get("a"));
    }

    @Test
    public void testClassBundle() {
        Locale locale = new Locale("en", "US");
        Locale.setDefault(locale);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ocp.i18nl10n.E");
        assertEquals("e", resourceBundle.getString("a"));
        resourceBundle.getKeys();
    }
}
