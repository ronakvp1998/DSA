package FunctionMethods;

public class BinaryToDecimal {

    public static void main(String[] args) {
        binToDec(100);
    }

    public static void binToDec(int binNum){
        int bNum = binNum;
        int pow = 0;
        double decNum = 0;
        while(binNum > 0){
            int lastDigit = binNum%10;
            decNum = decNum + (lastDigit*Math.pow(2,pow++));
            binNum = binNum/10;
        }
        System.out.println("Binary " + bNum);
        System.out.println("Decimal " + decNum);
    }
}
