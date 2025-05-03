package com.questions.practice2;

import java.util.*;

public class Test {
    public static void main(String[] args) {
//        int arr[] = {-1,2,3,3,4,5,-1};
//        int k = 4;
//        System.out.println(constantWindow(arr,k));
//        int arr[] = {2,5,1,7,10};
//        int k = 14;
//        System.out.println(longestSubArr2(arr,k));
//        int arr[] = {9,7,7,9,7,7,9};
//        int k = 7;
//        System.out.println(maxSum(arr,k));
//        String s = "cadbzabcd";
//        System.out.println(longestSub(s));
//        int arr[] = {1,1,1,0,0,0,1,1,1,1,0};
//        int k = 2;
//        System.out.println(maxOne(arr,k));
//        int arr[] = {3,3,3,1,2,1,1,2,3,3,4};
//        int k = 2;
//        System.out.println(fruitBasket2(arr,k));
//        String s = "aaabbccd";
//        int k =2;
//        System.out.println(longestSubDist2(s,k));
        String s = "bbacba";
//        System.out.println(sub2(s));
        System.out.println(sub3(s));
    }

    public static int sub3(String s) {
        int lastSeen[] = {-1,-1,-1};
        int count = 0;
        for(int i=0;i<s.length();i++){
            lastSeen[s.charAt(i) - 'a'] = i;
            if(lastSeen[0] != -1 &&
            lastSeen[1] != -1 &&
            lastSeen[2] != -1){
                count = count + (1 +
                        Math.min(lastSeen[0],
                                Math.min(lastSeen[1],lastSeen[2])));
            }
        }
        return count;
    }

    public static int sub2(String s){
        int count = 0,n = s.length();
        for (int i=0;i<n;i++){
            int hash[] = new int[3];
            for(int j=i;j<n;j++){
                hash[s.charAt(j) - 'a'] = 1;
                if(hash[0] + hash[1] + hash[2] == 3){
                    count = count + (n - j);
                    break;
                }
            }
        }
        return count;
    }

    public static int sub(String s){
        int count = 0;
        for(int i=0;i<s.length();i++){
            int hash [] = new int [3];
            for(int j=i;j<s.length();j++){
                hash[s.charAt(j) - 'a'] = 1;
                if(hash[0] + hash[1] + hash[2] == 3){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    public static int longestSubDist2(String s,int k){
        int maxLen = 0,l=0,r=0;
        Map<Character,Integer> map = new HashMap<>();
        while (r < s.length()){
            map.put(s.charAt(r),map.getOrDefault(s.charAt(r),0) + 1);
            if (map.size() > k){
                map.put(s.charAt(l),map.get(s.charAt(l)) - 1);
                if(map.get(s.charAt(l)) == 0){
                    map.remove(s.charAt(l));
                }
                l++;
            }
            if(map.size() <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }

    public static int longestSubDist(String s,int k){
        int maxLen = 0,n = s.length();
        Map<Character,Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            map.clear();
            for(int j=i;j<n;j++){
                map.put(s.charAt(j),map.getOrDefault(s.charAt(j),0) + 1);
                if(map.size() <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }

    public static int fruitBasket2(int arr[] ,int k){
        int maxLen = 0,l=0,r=0,n=arr.length;
        Map<Integer,Integer> map = new HashMap<>();
        while (r < n){
            map.put(arr[r],map.getOrDefault(arr[r],0) +1 );
            while (map.size() > k){
                map.put(arr[l],map.get(arr[l]) - 1);
                if(map.get(arr[l]) == 0){
                    map.remove(arr[l]);
                }
                l++;
            }
            maxLen = Math.max(maxLen,r-l+1);
            r++;
        }
        return maxLen;
    }

    public static int fruitBasket(int arr[],int k){
        int maxLen = 0;
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<arr.length;i++){
            set.clear();
            for(int j=i;j<arr.length;j++){
                set.add(arr[j]);
                if(set.size() > k){
                    break;
                }else{
                    maxLen = Math.max(maxLen,j-i+1);
                }
            }
        }
        return maxLen;
    }

    public static int maxOne1(int arr[], int k) {
        int maxLen = 0,l=0,r=0,n=arr.length,count=0;
        while (r < arr.length){
            if(arr[r] == 0){
                count++;
            }
            if (count > k){
                if(arr[l] == 0){
                    count = count - 1;
                }
                 l = l + 1;
            }
            if(count <= k){
                int len = r- l + 1;
                maxLen = Math.max(maxLen,len);
            }
            r++;
        }
        return maxLen;
    }

    public static int maxOne(int arr[], int k){
        int maxLen=0,count=0;
        for(int i=0;i<arr.length;i++){
            count =0;
            for(int j=i;j<arr.length;j++){
                if(arr[j] == 0){
                    count++;
                }
                if(count > k){
                    break;
                }
                int len = j - i + 1;
                maxLen = Math.max(maxLen,len);
            }
        }
        return maxLen;

    }

    public static int longestSub2(String s) {
        int maxLen = 0,n=s.length(),l=0,r=0;
        int arr[] = new int[256];
        Arrays.fill(arr,-1);;
        while (r < n){
            if(arr[s.charAt(r)] != -1){
                if(arr[s.charAt(r)] >= l){
                    l = arr[s.charAt(r)] + 1;
                }
            }
            int len = r-l+1;
            maxLen = Math.max(maxLen,len);
            arr[s.charAt(r)] = r;
            r++;
        }
        return maxLen;
    }

    public static int longestSub(String s){
        int n = s.length();
        int maxLen=0;
        int arr[] = new int[256];
        for(int i=0;i<n;i++){
            Arrays.fill(arr,0);
            for(int j=i;j<n;j++){
                if(arr[s.charAt(j)] == 1){
                    break;
                }
                int len = j - i + 1;
                maxLen = Math.max(maxLen,len);
                arr[s.charAt(j)] += 1;
            }
        }
        return maxLen;
    }

    public static int maxSum(int arr[], int k){
        int lSum=0,rSum=0,maxSum=0,n=arr.length;
        for(int i=0;i<k;i++){
            lSum = lSum + arr[i];
        }
        maxSum = lSum;
        int r = n-1;
        for(int i=k-1;i>=0;i--){
            lSum = lSum - arr[i];
            rSum = rSum + arr[r];
            r = r - 1;
            maxSum = Math.max(maxSum,lSum + rSum);
        }
        return maxSum;
    }

    public static int longestSubArr2(int arr[], int k) {
        int l=0,r=0,sum=0,maxLen = 0,n=arr.length;
        while (r < n){
            sum = sum + arr[r];
            if (sum > k){
                sum = sum - arr[l];
                l++;
            }
            if(sum <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }

    // longest subarray with sum <= k
    public static int longestSubArr(int arr[], int k){
        int maxLen = 0,n = arr.length;
        for(int i=0;i<n;i++){
            int sum = 0;
            for(int j=i;j<n;j++){
                sum = sum + arr[j];
                if(sum <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }

    public static int constantWindow(int arr[], int k){
        int l=0,r=k-1,maxSum=Integer.MIN_VALUE,sum=0,n=arr.length;
        for(int i=0;i<=r;i++){
            sum = sum + arr[i];
        }
        maxSum = sum;
        while (r < n-1){
            sum = sum - arr[l];
            r++;
            l++;
            sum = sum + arr[r];
            maxSum = Math.max(maxSum,sum);
        }
        return maxSum;
    }

    public static int numSubstr(String str,int k){
        int maxLen = 0,n=str.length();
        for(int i=0;i<n;i++){
            int map[] = new int [26];
            int maxFreq = 0;
            for(int j=i;j<n;j++){
                map[str.charAt(j) - 'a']++;
                maxFreq = Math.max(maxFreq, map[str.charAt(j) - 'a']);
                int changes = (j-i+1) - maxFreq;
                if(changes <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }
}
