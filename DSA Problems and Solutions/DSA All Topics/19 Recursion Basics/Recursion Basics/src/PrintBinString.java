// code 14 print binary String
// print all binary String of size N without consecutive ones
public class PrintBinString {

    public static void main(String[] args) {
        printBinString(3,0,"");
    }

    public static void printBinString(int n, int lastPlace, String str){

//         kaam
//        if(lastPlace == 0){
//             //set 0 on chair n
//            printBinString(n-1,0,str.append("0"));
//            printBinString(n-1,1,str.append("1"));
//        }else{
//            printBinString(n-1,0,str.append("0"));
//        }

        // base case
        if(n == 0){
            System.out.println(str);
            return;
        }

        // shotcut
        printBinString(n-1,0,str+"0");
        if(lastPlace == 0){
            printBinString(n-1,1,str + "1");
        }

    }
}
