package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCopyOnWriteArrayList {
    @Test
    public void testCopyOnWriteArrayList(){
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList=new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(4);
        Iterator<Integer> iterator = copyOnWriteArrayList.iterator();
        copyOnWriteArrayList.add(6);
        boolean onlyOneElement=true;
        while(iterator.hasNext()){
            assertTrue(onlyOneElement);
            assertEquals(Integer.valueOf(4),iterator.next());
            onlyOneElement=false;
        }

        copyOnWriteArrayList.add(2);

        iterator = copyOnWriteArrayList.iterator();
        copyOnWriteArrayList.remove(1);
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        System.out.println("---");
        iterator = copyOnWriteArrayList.iterator();

        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }
}
