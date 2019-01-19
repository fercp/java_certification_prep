package ocp;

import java.io.Console;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

/**
 * Hello world!
 */


public class App {
    public static void main(String[] args) {
        Locale i1 = Locale.getDefault();       // US
        Locale i2 = new Locale("it");          // Italy
        Locale i3 = new Locale("it", "CH");    // Switzerland
        System.out.println(i3.getDisplayCountry(i1));
        Console console = System.console();
        if (console != null) {
            console.writer().format(new Locale("fr", "CA"), "Hello World");
            console.writer().printf(new Locale("fr", "CA"), "Hello World");
            for (File file : new File("A").listFiles()) {

            }
        }
    }
}

class Car2 {
    static int i1 = 5;
    int i2 = 6;

    public static void m1() {
        System.out.print(i1);
    }
}

class Mini2 extends Car2 {
    static int i1 = 7;
    int i2 = 8;

    public static void m1() {
        System.out.print(i1);
    }

    public static void main(String[] args) {
        Car2 c = new Mini2();
        c.m1();
        System.out.print(" " + c.i1 + c.i2);
    }
}


class Test {
    static int i1, i2, i3;

    public static void main(String[] args) {
        List<Integer> names = Arrays.asList(1, 2, 3); //1
        names.forEach(x -> x = x + 1);
        names.forEach(System.out::println); //3
    }

    static int oops(int i) throws Exception {
        throw new Exception("oops");
    }

    static int test(int a, int b, int c) {
        return a + b + c;
    }
}


interface Eatable {
    int types = 10;
}

class Food implements Eatable {
    public static int types = 20;
}

class Fruit extends Food implements Eatable {  //LINE1

    public static void main(String[] args) {
        Food.types = 30; //LINE 2

    }
}

 class TestClass {
    public static void m1() throws Exception {
        throw new Exception("Exception from m1");
    }

    public static void m2() throws Exception {
        try {
            m1();
        } catch (Exception e) {
            throw e;
        } finally {
            throw new RuntimeException("Exception from finally");
        }
    }

    public static void main(String[] args) {
        try {
            IntStream is1 = IntStream.of(1, 3, 5);   OptionalDouble x = is1.filter(i->i%2 == 0).average();  System.out.println(x);
            IntStream is2 = IntStream.of(2, 4, 6);  int y = is2.filter( i->i%2 != 0 ).sum();
            List<Integer> names = Arrays.asList(1, 2, 3);
        } catch (Exception e) {
            Throwable[] ta = e.getSuppressed();
            for (Throwable t : ta) {
                System.out.println(t.getMessage());
            }
        }

    }
}

