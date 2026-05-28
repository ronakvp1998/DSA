package practice;

import strivers.linkedlist.ll.linkedlist1d.DeleteNodeInLinkedList;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        int arr[] = {100,4,200,1,3,2};
        System.out.println(longestConsecutive(arr));
    }
    public int[] twoSum2(int[] numbers, int target) {
        int left = 0,right=numbers.length-1;
        while (left < right){
            int sum = numbers[left] + numbers[right];
            if(sum == target){
                return new int[]{left+1,right+1};
            }
            if(sum < target){
                left++;
            }else if (sum > target){
                right--;
            }
        }
        return new int[]{};
    }

    public boolean isPalindrome(String s) {
        String temp = s.toLowerCase();
        int n = temp.length();
        int i=0,j=n-1;
        while (i < j){
            while (i < j && (temp.charAt(i) < 'a' || temp.charAt(i) > 'z')){
                i++;
            }
            while (i < j && (temp.charAt(j) < 'a' || temp.charAt(j) > 'z')){
                j--;
            }
            if(temp.charAt(i) != temp.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static int longestConsecutive(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        Set<Integer> numSet = new HashSet<>();
        for(int num : nums){
            numSet.add(num);
        }
        int longesStreak = 0;
        for(int num : numSet){
            if(!numSet.contains(num-1)){
                int currentNum = num;
                int currentStreak = 1;
                while (numSet.contains(currentNum + 1)){
                    currentNum += 1;
                    currentStreak += 1;
                }
                longesStreak = Math.max(longesStreak,currentStreak);
            }
        }
        return longesStreak;
    }

    public boolean isValidSudokuOptimal(char[][] board) {
        boolean [][] rows = new boolean[9][9];
        boolean [][] cols = new boolean[9][9];
        boolean [][] boxes = new boolean[9][9];

        for(int r=0;r<9;r++){
            for(int c=0;c<9;c++){
                if (board[r][c] == '.') {
                    continue;
                }
                int val = board[r][c] - '1';
                int boxIndex = (r / 3) * 3 + (c / 3);
                if(rows[r][val] || cols[c][val] || boxes[boxIndex][val]){
                    return false;
                }
                rows[r][val] = true;
                cols[c][val] = true;
                boxes[boxIndex][val] = true;
            }
        }
        return true;
    }

    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int []left = new int[n];
        int []right = new int[n];
        int []res = new int[n];
        left[0] = 1;
        right[n-1] = 1;
        for(int i=1;i<n;i++){
            left[i] = left[i-1] * nums[i-1];
        }
        for(int i=n-2;i>=0;i--){
            right[i] = right[i+1] * nums[i+1];
        }
        for(int i=0;i<n;i++){
            res[i] = left[i] * right[i];
        }
        return res;
    }

    public String encode(List<String> strs) {
        StringBuilder sb  = new StringBuilder();
        for(String s : strs){
            sb.append(s.length()).append("#").append(s);
        }
        return sb.toString();
    }

    public List<String> decode(String s){
        List<String> res = new ArrayList<>();
        int i = 0;
        while (i < s.length()){
            int delimiterPos = s.indexOf('#',i);
            int size = Integer.parseInt(s.substring(i,delimiterPos));
            int stringStart = delimiterPos + 1;
            String str = s.substring(stringStart,stringStart + size);
            res.add(str);
            i = stringStart + size;
        }
        return res;
    }


    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i : nums){
            map.put(i,map.getOrDefault(i,0) + 1);
        }
        return map.entrySet().stream().
                sorted((a,b) -> b.getValue() - a.getValue()).
                mapToInt(i -> i.getKey()).toArray();
    }

    public List<List<String>> groupAnagramsOptimal(String[] strs) {

        Map<String,List<String>> map = new HashMap<>();
        for(String s : strs){
            int count[] = new int[26];
            for(char c : s.toCharArray()){
                count[c - 'a']++;
            }
            String key = Arrays.toString(count);
            map.computeIfAbsent(key,k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<nums.length;i++){
            int diff = target - nums[i];
            if(map.containsKey(diff)){
                return new int[]{i,map.get(diff)};
            }
            map.put(nums[i],i);
        }
        return new int[]{};
    }

    public boolean isAnagram(String s, String t) {
        int freq[] = new int[26];
        for(char c : s.toCharArray()){
            freq[c - 'a']++;
        }
        for(char c : t.toCharArray()){
            if(freq[c - 'a'] <= 0){
                return false;
            }
            freq[c - 'a']--;
        }
        return true;
    }

    public boolean containsDuplicate(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i : nums){
            map.put(i,map.getOrDefault(i,0) + 1);
            if(map.get(i) > 1){
                return true;
            }
        }
        return false;
    }
}
