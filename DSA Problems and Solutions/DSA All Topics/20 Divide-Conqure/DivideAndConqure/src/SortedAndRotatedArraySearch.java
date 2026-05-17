public class SortedAndRotatedArraySearch {

    public static void main(String[] args) {
        int arr[] = {4,5,6,7,0,1,2};
        int target = 1; // output = index 4
        System.out.println(rotateSearchArr(arr,target,0,arr.length-1));

    }

    public static int rotateSearchArr(int arr[],int target, int start, int end){
        // Base case
        if(start > end){
            return -1;
        }

        // find out mid
        int mid = start + (end - start) /2;

        // case where middle value is the target value
        if(arr[mid] == target){
            return mid;
        }

        // case 1 -> middle value lies on Line 1
        if(arr[start] <= arr[mid]){
            // case a -> search left in line 1
            if(arr[start] <= target && target<=arr[mid]){
                // recursion call to search left part of line 1
                return rotateSearchArr(arr,target,start,mid-1);
                // case b -> search left in line 1
            }else{
                // recusion call to search right part of line 1
                return rotateSearchArr(arr,target,mid+1,end);
            }
        }

        // case 2 -> middle value lies on line 2
        else{
            // case c -> search right in line 2
            if(arr[mid] <= target && target <= end){
                return rotateSearchArr(arr,target,mid+1,end);
            }else{
                // case d -> search left of line 2
                return rotateSearchArr(arr,target,start,mid-1);
            }
        }

    }
}
