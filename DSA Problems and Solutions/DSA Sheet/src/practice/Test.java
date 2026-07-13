package practice;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int pre[] = new int[n];
        int post[] = new int[n];

        pre[0] = 1;
        post[n-1] = 1;
        for(int i=1;i<n;i++){
            pre[i] = pre[i-1] * nums[i-1];
            post[n-i-1] = post[n-i] * nums[n-i];
        }
        int res[] = new int[n];
        for(int i=0;i<res.length;i++){
            res[i] = pre[i] * post[i];
        }
        return res;
    }

    public String encode(List<String> strs){
        StringBuilder sb = new StringBuilder();
        for(String s : strs){
            sb.append(s.length()).append('#').append(s);
        }
        return sb.toString();
    }

    public List<String> decode(String s){
        List<String> res = new ArrayList<>();
        int i = 0;
        while (i < s.length()){
            int delimiterPos = s.indexOf('#',i);
            int size = Integer.parseInt(s.substring(i,delimiterPos));
            int stringStart = delimiterPos+1;
            String str = s.substring(stringStart,stringStart + size);
            res.add(str);
            i = stringStart + size;
        }
        return res;
    }

    public int[] topKFrequent(int[] nums, int k) {
        return Arrays.stream(nums).boxed()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream()
                .sorted((a,b) -> Long.compare(b.getValue(),a.getValue()))
                .mapToInt(e->e.getKey()).limit(k).toArray();
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String,List<String>> hashMap = new HashMap<>();
        for(String s : strs){
            int freq[] = new int[26];
            for(char c : s.toCharArray()){
                freq[c - 'a']++;
            }
            String key = Arrays.toString(freq);
            hashMap.computeIfAbsent(key,k->new ArrayList<>()).add(s);
        }
        return new ArrayList<>(hashMap.values());
    }

    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            int rem = target - nums[i];
            if(map.containsKey(rem)){
                return new int[]{map.get(rem),i};
            }
            map.put(nums[i],i);
        }
        return new int[]{};
    }

    public boolean isAnagram(String s, String t) {
        Map<Integer,Long> sFreq = s.codePoints().boxed()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        Map<Integer,Long> tFreq = s.chars().boxed()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        s.codePoints().boxed()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        return tFreq.equals(sFreq);
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
