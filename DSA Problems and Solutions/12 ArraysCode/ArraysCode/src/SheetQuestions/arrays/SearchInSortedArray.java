package SheetQuestions.arrays;

// Search in Rotated Sorted Array
public class SearchInSortedArray {
    public static void main(String[] args) {
        int arr [] = {4,5,6,7,0,1,2};
        int key = 0;
        System.out.println(binarySearchRotated(arr,key,0,arr.length-1));
    }

    public static int binarySearchRotated(int arr[], int target, int start, int end){
        if(start > end){ return -1;}

        int mid = start + (end - start)/2;

        if(arr[mid] == target){
            return mid;
        }

        if(arr[start] <= arr[mid]){
            if(arr[start] <= target && target <= arr[mid]){
                return binarySearchRotated(arr,target,start,mid-1);
            }else{
                return binarySearchRotated(arr,target,mid+1,end);
            }
        }else{
            if(arr[mid] <= target && target <= arr[end]){
                return binarySearchRotated(arr,target,mid+1,end);
            }

            else {
                return binarySearchRotated(arr,target,start,mid-1);
            }
        }
    }
}
