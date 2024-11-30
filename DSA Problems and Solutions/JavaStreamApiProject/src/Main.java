import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        int[] arr = new int[]{12,3,9,11,4,2,1};
        int sum = 24;
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<arr.length-2;i++){
            for(int j=i+1;j<arr.length-1;j++){
                int temp = arr[i] + arr[j] + arr[j+1];
                if(temp == sum){
                    List<Integer> tempList = new ArrayList<>();
                    tempList.add(arr[i]);
                    tempList.add(arr[j]);
                    tempList.add(arr[j+1]);
                    res.add(tempList);
                }
                j++;

            }
        }
        res.stream().forEach(s->System.out.println(s));
    }
}