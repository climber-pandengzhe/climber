package com.climber.cloud_basecore.jvm.bytecode;

public class MTest {

    public static void test(G g){
        System.out.println("g");
    }
    public  void test(F f){
        System.out.println("f");
    }
    public  void test(S s){
        System.out.println("s");
    }
    public  void main(String[] args) {

        G g1 =new F();
        G g2 =new S();

        MTest.test(g1);
        MTest.test(g2);


//        g1.speak();
//        g2.speak();
    }

}
class G {
//    public void speak(){
//        System.out.println("g say");
//    }
}
class F extends  G {
//    F(){
//        System.out.println("f new");
//    }
//    public void speak(){
//        System.out.println("f say");
//    }
}
class S extends F {
//    S(){
//        System.out.println("s new");
//    }
//    public void speak(){
//        System.out.println("s say");
//    }
}