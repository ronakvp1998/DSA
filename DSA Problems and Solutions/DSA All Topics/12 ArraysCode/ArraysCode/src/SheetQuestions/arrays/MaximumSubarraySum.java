package SheetQuestions.arrays;

// code 3 : Maximum subarray sum return sum
public class MaximumSubarraySum {

    public static void main(String[] args) {
        int arr[] = {5,4,-1,7,8};

        int ms=0;
        int cs=0;

        for(int i=0;i< arr.length;i++){
            cs = cs + arr[i];
            if(cs < 0){
                cs = 0;
            }
            if(cs > ms){
                ms = cs;
            }
        }
        System.out.println("Subarray sum " + ms);
    }

}
