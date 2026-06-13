package practice;

import java.util.*;

public class Test {

    static class OptQueue{
        private final int arr[];
        private int start, end, currSize, maxSize;

        public OptQueue(){
            this.maxSize = 16;
            this.arr = new int[maxSize];
            this.start = -1;
            this.end = -1;
            this.currSize = 0;
        }

        public OptQueue(int maxSize){
            this.maxSize = maxSize;
            this.arr = new int[maxSize];
            this.start = -1;
            this.end = -1;
            this.currSize = 0;
        }

        public void push(int newElement){
            if(currSize == maxSize){
                System.out.println("Queue is full " + newElement);
                return;
            }
            if(end == -1){
                start = 0;
                end = 0;
            }else{
                end = (end + 1) % maxSize;
            }
            arr[end] = newElement;
            currSize++;
        }

        public int pop(){
            if(start == -1){
                System.out.println("Queue is empty");
                return -1;
            }
            int popped = arr[start];
            if(currSize == 1){
                start = -1;
                end = -1;
            }else{
                start = (start + 1) % maxSize;
            }
            currSize--;
            return popped;
        }
    }

    public int[] nextGreate(int [] nums1, int [] nums2){
        Map<Integer, Integer> nextGreaterMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i=nums2.length-1;i >= 0; i--){
            int current = nums2[i];

            while (!stack.isEmpty() && stack.peek() <= current){
                stack.pop();
            }

            nextGreaterMap.put(current,stack.isEmpty() ? -1 : stack.peek());
            stack.push(current);
        }
        return Arrays.stream(nums1)
                .map(nextGreaterMap::get).toArray();
    }

    public int[] nextGreater(int nums[]){
        int n = nums.length;
        int [] ans = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i=2*n-1;i>=0;i--){
            int current = nums[i%n];
            while (!stack.isEmpty() && stack.peek() <= current){
                stack.pop();
            }
            if(i < n){
                ans[i] = stack.isEmpty() ? -1 : stack.peek();
            }
            stack.push(current);
        }
        return ans;
    }
}
