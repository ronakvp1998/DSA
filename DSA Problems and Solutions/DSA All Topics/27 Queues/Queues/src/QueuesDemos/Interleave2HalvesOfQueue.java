package QueuesDemos;

// code 8 :- Interleave 2 havels of a queue (even length)

import java.util.LinkedList;
import java.util.Queue;

public class Interleave2HalvesOfQueue {

    public static void interLeave(Queue<Integer> q){
        Queue<Integer> firstHalf = new LinkedList<>();
        int size = q.size();    // we need to calculate the size initially because it will change

        for(int i=0;i<size/2;i++){
            firstHalf.add(q.remove());
        }

        while(!firstHalf.isEmpty()){
            q.add(firstHalf.remove());
            q.add(q.remove());
        }
    }

    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<>();
        int a = 1;
        while(a != 11){
            q.add(a);
            a++;
        }
        System.out.println(q);

        interLeave(q);

        System.out.println(q);
    }
}
