import java.util.List;

public class LargestStringLexographicallyCode {

    public static void main(String[] args) {
        String arr[] = {"apple","mango","banana"};
        // output should be mango
        largestStringLexographicallyCode(arr);
    }

    public static void largestStringLexographicallyCode(String [] arr){

        String largest = arr[0];
        for(int i =0;i< arr.length;i++){
            if(largest.compareTo(arr[i]) < 0){
                largest = arr[i];
            }
        }
        System.out.println(largest);
    }
}
