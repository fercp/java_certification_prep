package ocp.classdesign.modifiers.p2;

import ocp.classdesign.modifiers.p1.A;


public class B extends A {
    private int y;
    void m1(){
        A a=new A();
        //a.x=5; Compilation error
        //a.y=5; Compilation error
        //y=5; Compilation error
        x=5; //OK
        y=6;//OK
        B b=new B();
        b.x=5; //OK
        b.y=5; //OK
    }
}
