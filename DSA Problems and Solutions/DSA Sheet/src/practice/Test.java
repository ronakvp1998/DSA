package practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    public static int[] optimalApproachPQ(int[] arr) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[0],b[0]));
        for(int i=0;i<arr.length;i++){
            pq.offer(new int[]{arr[i],i});
        }
        int res[] = new int[arr.length];
        int currentRank = 0;
        Integer previousSmall = null;
        for(int i=0;i< arr.length;i++){
            int[] node = pq.poll();
            int currentNode = node[0];
            int currentIndex = node[1];
            if(previousSmall == null || currentNode > previousSmall){
                currentRank++;
            }
            res[currentIndex] = currentRank;
            previousSmall = currentNode;
        }
        return res;
    }
}
