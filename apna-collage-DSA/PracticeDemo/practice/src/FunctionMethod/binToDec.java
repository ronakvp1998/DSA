package FunctionMethod;

public class binToDec {

    public static void main(String[] args) {
        binToDec(100);
    }

    public static void binToDec(int binNum) {
        int bNum = binNum;
        int pow = 0;
        double decNum = 0;
        while(bNum > 0){
            int lastDigit = bNum%10;
            decNum = decNum + (lastDigit * Math.pow(2,pow));
            pow++;
            bNum = bNum/10;

        }
        System.out.println("Binary " + binNum);
        System.out.println("Decimal " + decNum);
    }
}