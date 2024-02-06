package FunctionMethod;

public class FactorialDemo {

    public static void main(String[] args) {
        System.out.println(factorialRecursion(5));
        System.out.println(factorialFun(5));

    }

    public static int factorialFun(int a){
        int fact = 1;
        if(a == 0){
            return fact;
        }

        for(int i=1;i<=a;i++){
            fact = fact * i;
        }
        return fact;
    }

    public static int factorialRecursion(int a){
        if(a == 0){
            return 1;
        }
        int fact = a * factorialRecursion(a-1);
        return fact;
    }
}
