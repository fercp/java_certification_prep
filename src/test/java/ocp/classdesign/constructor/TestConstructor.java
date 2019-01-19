package ocp.classdesign.constructor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Summary
 *
 * @author Ferat Capar
 * @version Creation Date : 19.05.2018 12:23
 */
public class TestConstructor {
    static String s = "";

    @Test
    public void testConstructor() {
        s += "pre ";
        new Raptor();
        s += "howk ";
        assertEquals("pre s1 s2 r1 r4 b1 b3 b2 r3 r2 howk ", s);


    }

}

class Bird {
    static {
        TestConstructor.s += "s1 ";
    }

    {
        TestConstructor.s += "b1 ";
    }

    public Bird() {
        TestConstructor.s += "b2 ";
    }

    {
        TestConstructor.s += "b3 ";
    }

    static {
        TestConstructor.s += "s2 ";
    }
}

class Raptor extends Bird {
    static {
        TestConstructor.s += "r1 ";
    }

    public Raptor() {
        TestConstructor.s += "r2 ";
        ;
    }

    {
        TestConstructor.s += "r3 ";
    }

    static {
        TestConstructor.s += "r4 ";
    }

}