package practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    public long subArrayRangesBruteForce(int[] nums) {
        long totalSum = 0;
        int n = nums.length;
        for(int i=0;i<n;i++){
            int min = nums[i];
            int max = nums[i];
            for(int j=i;j<n;j++){
                min = Math.min(min,nums[j]);
                max = Math.max(max,nums[j]);
                totalSum += (max - min);
            }
        }
        return totalSum;
    }

    public int[] asteroidCollisionOptimal(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for(int ast : asteroids){
            boolean destroyed = false;
            while (!stack.isEmpty() && stack.peek() > 0 && ast < 0){
                int topSize = stack.peek();
                int incomingSize = Math.abs(ast);
                if(topSize < incomingSize){
                    stack.pop();
                    continue;
                } else if (topSize == incomingSize) {
                    stack.pop();
                }
                destroyed = true;
                break;
            }
            if(!destroyed){
                stack.push(ast);
            }
        }
        int res[] = new int[stack.size()];
        for(int i = res.length-1;i >= 0 ;i--){
            res[i] = stack.pop();
        }
        return res;
    }

    public int[] asteroid(int []asteroids){
        List<Integer> list = Arrays.stream(asteroids).boxed().collect(Collectors.toList());
        boolean collisionHappened = true;
        while (collisionHappened){
            collisionHappened = false;
            for(int i=0;i<list.size()-1;i++){
                int left = list.get(i);
                int right = list.get(i+1);
                if(left > 0 && right < 0){
                    collisionHappened = true;
                    if(Math.abs(left) > Math.abs(right)){
                        list.remove(i+1);
                    } else if (Math.abs(left) < Math.abs(right)) {
                        list.remove(i);
                    }else{
                        list.remove(i+1);
                        list.remove(i);
                    }
                    break;
                }
            }
        }
        return list.stream().mapToInt(i ->i).toArray();
    }

    public static final int MOD = 1000000007;
    public int sumSubarrayMin(int arr[]){
        int n = arr.length;

        int []pse = new int[n];
        int []nse = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        // find PSE
        for(int i=0;i<n;i++){
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]){
                stack.pop();
            }
            pse[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        stack.clear();

        // find NSE
        for(int i=n-1;i>=0;i--){
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]){
                stack.pop();
            }
            nse[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // total
        long totalSum = 0;
        for(int i=0;i<n;i++){
            long leftDist = i - pse[i];
            long rightDist = nse[i] - i;
            long subarrayCount = (leftDist * rightDist) % MOD;
            long contribute = (subarrayCount * arr[i]) % MOD;
            totalSum = (totalSum + contribute) % MOD;
        }
        return (int) totalSum;
    }

    public int trapPrefixSuffix(int[] height) {
        int n = height.length;
        if(n <= 2){
            return 0;
        }
        int []leftMax = new int[n];
        int []rightMax = new int[n];

        leftMax[0] = height[0];
        for(int i=1;i<n;i++){
            leftMax[i] = Math.max(height[i],leftMax[i-1]);
        }
        rightMax[n-1] = height[n-1];
        for(int i=n-2;i>=0;i--){
            rightMax[i] = Math.max(height[i],rightMax[i+1]);
        }
        return IntStream.range(0,n)
                .map(i -> Math.min(leftMax[i],rightMax[i]) - height[i])
                .sum();
    }

    public int trapStack(int[] height) {
        if(height == null || height.length <= 2){
            return 0;
        }
        int left=0,right = height.length-1;
        int leftMax=0,rightMax=0,totalWater=0;
        while (left < right){
            if(height[left] <= height[right]){
                if(height[left] >= leftMax){
                    leftMax = height[left];
                }else{
                    totalWater += leftMax - height[left];
                }
                left++;
            }else{
                if(height[right] >= rightMax){
                    rightMax = height[right];
                }else{
                    totalWater += rightMax - height[right];
                }
                right--;
            }
        }
        return totalWater;
    }

    public int[] nextSmallestElement(int []nums){
        int n = nums.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int ans[] = new int[n];
        for(int i=n-1;i>=0;i--){
            int current = nums[i];
            while (!stack.isEmpty() && stack.peek() >= current){
                stack.pop();
            }
            ans[i] = stack.isEmpty() ? -1 : stack.peek() ;
            stack.push(current);
        }
        return ans;
    }

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int ans[] = new int[n];
        for(int i=(n-1)*2;i>=0;i--){
            int current = nums[i%n];
            while (!stack.isEmpty() && stack.peek() <= current){
                stack.pop();
            }
            ans[i%n] = stack.peek() == null ? -1 : stack.peek();
            stack.push(current);
        }
        return ans;
    }

}
