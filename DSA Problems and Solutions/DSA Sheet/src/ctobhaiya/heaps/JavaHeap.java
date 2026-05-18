package ctobhaiya.heaps;

import java.util.Collections;
import java.util.PriorityQueue;

public class JavaHeap {

    public static void main(String[] args) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap.add(20);
        minHeap.add(10);
        minHeap.add(17);
        minHeap.add(30);

        maxHeap.add(20);
        maxHeap.add(10);
        maxHeap.add(17);
        maxHeap.add(40);

        System.out.println(maxHeap.peek());
        System.out.println(minHeap.peek());

        minHeap.poll();
        minHeap.poll();
        System.out.println(maxHeap.peek());
        System.out.println(minHeap.peek());

        System.out.println(minHeap.size());
        System.out.println(minHeap.size());

        minHeap.poll();
        minHeap.poll();
        minHeap.poll();
        minHeap.poll();
        System.out.println(maxHeap.peek());
        System.out.println(minHeap.peek());

        System.out.println(minHeap.size());
        System.out.println(minHeap.size());


    }

}
