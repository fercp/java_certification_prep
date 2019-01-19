package ocp.io;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestObjectStreams {
    static class A implements Serializable{
        private B b=new B();
    }

    static class D extends B implements Serializable{
        protected String d="D";
        public D(String a){
            d=a;
        }

    }

    static class E{
        protected String e="e";
        public E(){
            e="X";
        }

    }

    static class B extends E{
        protected String b="b";
        public B(){
            b="X";
        }

    }

    static class C extends D implements Serializable{
        private transient B bb=new B();
        private String x;
        private static String y;

        {
            x="Z";
        }


        public C(){
            super("Y");
            x="Y";
        }

        static {
            y="A";
        }

    }

    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        A a=new A();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
        assertThrows(NotSerializableException.class,()->objectOutputStream.writeObject(a));
        C c=new C();
        C.y="B";
        c.x="X";
        c.d="A";
        c.b="A";
        c.e="A";


        byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream2=new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream2.writeObject(c);
        objectOutputStream2.flush();
        objectOutputStream2.close();

        ObjectInputStream objectInputStream=new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        try {
            c = (C) objectInputStream.readObject();
            assertNull(c.bb);
            assertEquals("X", c.x);
            assertEquals("B", c.y);
            assertEquals("A", c.d);
            assertEquals("X", c.b);
            assertEquals("X", c.e);
        }catch (EOFException e){ //readObject eof
            fail();
        }
    }

    static class Pojo implements Serializable, ObjectInputValidation {

        private String msg;
        private transient int x;

        public Pojo(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        private void writeObject(java.io.ObjectOutputStream out) throws IOException {
            System.out.println("writeObject");
            out.defaultWriteObject();
            out.writeInt(5);
        }

        private Object writeReplace() throws ObjectStreamException {
            System.out.println("writeReplace");
            return this;
        }

        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
            System.out.println("readObject");
            in.registerValidation(this, 0);
            in.defaultReadObject();
            x=in.readInt();
        }

        @Override
        public void validateObject() throws InvalidObjectException {
            System.out.println("validateObject");
        }

        private Object readResolve() throws ObjectStreamException {
            System.out.println("readResolve");
            return this;
        }
    }

    @Test
    public void testDefaultReadWrite() throws IOException, ClassNotFoundException {
        Pojo pojo = new Pojo("Hello world");
        byte[] bytes = serialize(pojo); // Serialization
        Pojo p = (Pojo) deserialize(bytes); // De-serialization
        System.out.println(p.getMsg());
        assertEquals(5,p.x);
    }

    private byte[] serialize(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.flush();
        oos.close();
        return baos.toByteArray();
    }

    private  Object deserialize(byte[] bytes) throws ClassNotFoundException, IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object o = ois.readObject();
        ois.close();
        return o;
    }

}
