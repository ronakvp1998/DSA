package ArraysDemos;

public class LinearSearch {

    public static void main(String[] args) {
        int num [] = {2,3,4,10,5,6,7};
        System.out.println(linearSearch(num,4));
    }

    public static int linearSearch(int[] arr, int n){

        for(int i=0;i<arr.length;i++){
            if(arr[i] == n){
                return i;
            }
        }
        return -1;
    }
}
