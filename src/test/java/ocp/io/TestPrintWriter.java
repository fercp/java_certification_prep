package ocp.io;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestPrintWriter {
    static class A{
        @Override
        public String toString(){
            return "A";
        }
    }


    @Test
    public void testPrintWriter()  {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintWriter printWriter=new PrintWriter(byteArrayOutputStream);
        //PrintWriter printWriter=new PrintWriter(Charset.forName("UTF-8",new File(..);
        printWriter.println("test"); // println not throws exception
        printWriter.println(1);
        printWriter.println(new A());
        printWriter.printf("Printf %02d\n",2);
        printWriter.format("Format %02d\n",2);
        printWriter.close();


        BufferedReader reader=new BufferedReader(new StringReader(byteArrayOutputStream.toString()));
        try {
            assertEquals("test", reader.readLine());//readLine throws exception
            assertEquals("1", reader.readLine());
            assertEquals("A", reader.readLine());
            assertEquals("Printf 02", reader.readLine());
            assertEquals("Format 02", reader.readLine());
        }catch (IOException e){
            fail();
        }
    }
}
