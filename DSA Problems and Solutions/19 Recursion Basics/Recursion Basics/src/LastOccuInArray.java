
// code 8 WAP to find the last occurance of a value in an array

public class LastOccuInArray{
    private static int res = -1;
        public static void main(String[] args) {
            int[] arr = {8,3,5,9,5,10,2,15,5};
//            System.out.println(LastOccurance(arr,5,0));
//            System.out.println(LastOccurance1(arr,5,0));

            System.out.println(LastOccurance2(arr,10, arr.length-1));


        }

    public static int LastOccurance(int arr[], int key,int i){
            if(arr.length == i){
                return -1;
            }
            int isFound = LastOccurance(arr,key,i+1);
            if(isFound == -1 && arr[i] == key){
                return i;
            }
            return isFound;
    }


    public static int LastOccurance2(int [] arr, int n,int counti){
        if(counti == arr.length || counti < 0){
            return -1;
        }
        if(n == arr[counti]){
            return counti;
        }
        counti--;
        return LastOccurance2(arr,n, counti);
    }

}
