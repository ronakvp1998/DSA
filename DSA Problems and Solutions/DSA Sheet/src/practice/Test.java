package com.questions.practice;

import java.util.*;

public class Test {
    public static void main(String[] args) {

    }

    public static int findDuplicateOptimal(int[] nums) {
        int slow = nums[0], fast = nums[0];
        do{
            slow = nums[slow];
            fast = nums[nums[fast]];
        }while (slow != fast);

        slow = nums[0];
        while (slow!=fast){
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
