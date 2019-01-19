package ocp.classdesign.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestString {
    @Test
    public void testString(){
        String a="a"+"b";
        assertSame("ab", a);
        String b=a+"c";
        assertNotSame("abc", b);
    }
}
