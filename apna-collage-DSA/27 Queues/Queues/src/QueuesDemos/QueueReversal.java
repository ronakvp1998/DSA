package QueuesDemos;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// code 9 :- Queue Reversal

public class QueueReversal {

    public static void reverseQueue(Queue<Integer> q){
        Stack<Integer> s = new Stack<>();

        while ((!q.isEmpty())){
            s.push(q.remove());
        }

        while (!s.isEmpty()){
            q.add(s.pop());
        }

    }

    public static void main(String[] args) {

        Queue<Integer> q = new LinkedList<>();
        int a = 1;
        while(a != 6){
            q.add(a);
            a++;
        }
        System.out.println(q);
        reverseQueue(q);
        System.out.println(q);
    }
}
