package ArraysCode;
// 12

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DuplicateInArray {

    public static void main(String[] args) {
        String arr[] = {"Ronak","Ronak","Panchal","Panchals"};
//        System.out.println(isDuplicate(arr));
//        isDuplicate2(arr);
        isDuplicateStreamAPI(arr);

    }

    public static void isDuplicateStreamAPI(String arr[]){
        List<Map.Entry<String, Long>> res = Arrays.stream(arr)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() > 1).collect(Collectors.toList());
        System.out.println(res);
    }

    public static void isDuplicate2(String arr[]){
        Map<String,Integer> counts = new HashMap<>();
        for (int i=0;i<arr.length;i++){
            if(counts.containsKey(arr[i])){
                counts.put(arr[i],counts.get(arr[i]) + 1);
            }else{
                counts.put(arr[i],1);
            }
        }
        System.out.println(counts);

    }

    public static boolean isDuplicate(String arr[]){
        Map<String,Integer> counts = new HashMap<>();
        for (int i=0;i<arr.length;i++){
            if(counts.containsKey(arr[i])){
                counts.put(arr[i],counts.get(arr[i]) + 1);
                return true;
            }else{
                counts.put(arr[i],1);
            }
        }
        return false;

    }
}


