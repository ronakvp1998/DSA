package Test;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class ListDemo {

    public static void main(String[] args) {
        int[] arr = new int[]{7,18,1,20};
        // find the K the largest element

//        System.out.println(Arrays.stream(arr).sorted().skip(2).findFirst());

        Queue<Integer> pqueue = new PriorityQueue<>();
        pqueue.add(7);
        pqueue.add(18);
        pqueue.add(1);
        pqueue.add(20);
        for(Integer a : pqueue){
            System.out.println(pqueue.peek());
        }


    }


}
