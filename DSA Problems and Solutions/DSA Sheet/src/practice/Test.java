package practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    public static int[] topKFrequentMinHeap(int[] nums, int k) {
        Map<Integer,Integer> freqMap = new HashMap<>();
        for(int num : nums){
            freqMap.put(num,freqMap.getOrDefault(num,0) + 1);
        }
        PriorityQueue<Map.Entry<Integer,Integer>> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for(Map.Entry<Integer,Integer> entry : freqMap.entrySet()){
            minHeap.offer(entry);
            if(minHeap.size() > k){
                minHeap.poll();
            }
        }
        int res[] = new int[k];
        for(int i=k-1;i>=0;i--){
            res[i] = minHeap.poll().getKey();
        }
        return res;
    }

    public static int[][] kClosestAlternative(int[][] points, int k) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (p1,p2) -> Integer.compare(squareDist(p2),squareDist(p1))
        );
        for(int [] point : points){
            maxHeap.offer(point);
            if(maxHeap.size() > k){
                maxHeap.poll();
            }
        }
        int [][] res = new int[k][2];
        for(int i=0;i<k;i++){
            res[i] = maxHeap.poll();
        }
        return res;
    }

    public static int squareDist(int [] point){
        return point[0]*point[0] + point[1]*point[1];
    }

    final int k;
    final PriorityQueue<Integer> minHeap;

    public Test(int k, int [] nums){
        this.k = k;
        this.minHeap = new PriorityQueue<>(k);
        for(int i : nums){
            add(i);
        }
    }

    public int add(int val){
        minHeap.offer(val);
        if(minHeap.size() > k){
            minHeap.poll();
        }
        return minHeap.size() < k ? -1 : minHeap.peek();
    }

    public static int optimalApproachMaxHeap(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(int stone : stones){
            maxHeap.offer(stone);
        }
        while (maxHeap.size() > 1){
            int y = maxHeap.poll();
            int x = maxHeap.poll();
            if(x != y){
                maxHeap.offer(y-x);
            }
        }
        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    public static boolean isNStraightHandStreamAPI(int[] hand, int groupSize) {
        Map<Integer,Long> freqMap = Arrays.stream(hand)
                .boxed().collect(Collectors.groupingBy(e->e, Collectors.counting()));

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(freqMap.keySet());

        while (!minHeap.isEmpty()){
            int smallestCard = minHeap.peek();
            if(freqMap.get(smallestCard) == 0){
                minHeap.poll();
                continue;
            }
            for(int i=0;i<groupSize;i++){
                int currentCard = smallestCard + i;
                long count = freqMap.getOrDefault(currentCard,0L);
                if(count == 0){
                    return false;
                }
                freqMap.put(currentCard,count-1);
            }
        }
        return true;
    }


    public static int leastIntervalSimulation(char[] tasks, int n) {
        if(n == 0){
            return tasks.length;
        }
        int frequency[] = new int[26];
        for(char task : tasks){
            frequency[task - 'A']++;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b-1);
        for(int freq : frequency){
            if(freq > 0){
                maxHeap.add(freq);
            }
        }
        int totalTime = 0;
        while (!maxHeap.isEmpty()){
            List<Integer> waitList = new ArrayList<>();
            int cycle = n+1;
            int taskExecuted = 0;
            while (cycle > 0 && !maxHeap.isEmpty()){
                int currentFreq = maxHeap.poll();
                if(currentFreq > 1){
                    waitList.add(currentFreq-1);
                }
                taskExecuted++;
                cycle--;
            }
            waitList.forEach(maxHeap::add);
            if(maxHeap.isEmpty()){
                totalTime += taskExecuted;
            }else{
                totalTime += (n+1);
            }
        }
        return totalTime;
    }

    public static int leastIntervalOptimal(char[] tasks, int n) {
        int[] freqs = new int[26];
        int maxFreq = 0;
        for(char task : tasks){
            freqs[task - 'A']++;
            maxFreq = Math.max(maxFreq,freqs[task - 'A']);
        }
        int maxCount = 0;
        for(int freq : freqs ){
            if(freq == maxFreq){
                maxCount++;
            }
        }
        int requiredIntervals = (maxFreq-1)*(n+1)+maxCount;
        return Math.max(tasks.length,requiredIntervals);
    }

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
