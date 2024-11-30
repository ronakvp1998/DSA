package Basics;

public class Main {
    public static void main(String[] args) {
//        OuterClass.NestedClass nestedClass = new OuterClass.NestedClass();
//        nestedClass.print();
        new OuterClass2().display();
    }
}

class OuterClass{
    int instanceVariable = 10;
    static int classVariable = 20;

    static class NestedClass{
        public void print(){
//            System.out.println(classVariable + instanceVariable);
            System.out.println(classVariable );
        }
    }
}

class OuterClass2{
    int instanceVariable = 10;
    static int classVariable = 20;

    private static class NestedClass2{
        public void print(){
//            System.out.println(classVariable + instanceVariable);
            System.out.println(classVariable );
        }
    }
    public void display(){
        NestedClass2 nestedClass2 = new NestedClass2();
        nestedClass2.print();
    }
}
