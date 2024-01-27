public class LastOccuInArray{
        // WAP to find the first occurance of a value in an array
    private static int res = -1;
        public static void main(String[] args) {
            int[] arr = {8,3,5,9,5,10,2,15,5};
            System.out.println(LastOccurance(arr,5,0));
//            System.out.println(LastOccurance1(arr,5,0));
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


        public static int LastOccurance1(int arr[], int key,int i){
            if(i == arr.length){
                return res;
            }
            if(arr[i] == key){
                res = i;
            }
            return LastOccurance1(arr,key,i+1);

        }


}
