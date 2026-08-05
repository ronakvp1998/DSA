package practice;

public class Test {

    public void moveZeroes(int[] nums) {

    }

    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        int i = 0;
        for(int j=1;j<nums.length;j++){
            if(nums[j] != nums[i]){
                i++;
                nums[i] = nums[j];
            }
        }
        return i+1;
    }

    public boolean check(int[] nums) {
        if(nums == null || nums.length <= 1){
            return true;
        }
        int dip=0,n=nums.length;
        for(int i=0;i<n;i++){
            if(nums[i] > nums[(i+1)%n]){
                dip++;
            }
            if(dip > 1){
                return false;
            }
        }
        return true;
    }

}