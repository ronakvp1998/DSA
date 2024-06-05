package QueuesDemos;

// code 6 : create stack using 2 queues where pop takes O(n) and others take O(1)

import java.util.LinkedList;
import java.util.Queue;

public class Stackusing2QueuePopOn {

    static class Stack{
        static Queue<Integer> q1 = new LinkedList<>();
        static Queue<Integer> q2 = new LinkedList<>();

        public static boolean isEmpty(){
            return q1.isEmpty() && q2.isEmpty();
        }

        public static void push(int data){
            if(!q1.isEmpty()){ // q1 is not empty
                q1.add(data);
            }else{  // q1 is empty
                q2.add(data);
            }
        }

        public static int pop(){
            if(isEmpty()){
                System.out.println("Empty Stack");
                return -1;
            }

            int top = -1;
            // pop till you reach the last element

            // case 1
            if(!q1.isEmpty()){
                while (!q1.isEmpty()){
                    top = q1.remove();
                    if(q1.isEmpty()){
                        break;   // reached the last element
                    }
                    q2.add(top);    // add the last element to q2
                }
            }else{  // case 2
                while (!q2.isEmpty()){
                    top = q2.remove();
                    if(q2.isEmpty()){
                        break;
                    }
                    q1.add(top);
                }
            }
            return top;
        }

        public static int peek(){
            if(isEmpty()){
                System.out.println("Empty Stack");
                return -1;
            }
            int top = -1;
            // pop till you reach the last element
            // case 1
            if(!q1.isEmpty()){
                while (!q1.isEmpty()){
                    top = q1.remove();
                    q2.add(top);    // add the last element to q2
                }
            }else{  // case 2
                while (!q2.isEmpty()){
                    top = q2.remove();
                    q1.add(top);
                }
            }
            return top;
        }

    }

    public static void main(String[] args) {
        Stack s = new Stack();
        s.push(1);
        s.push(2);
        s.push(3);

        while (!s.isEmpty()){
            System.out.println(s.peek());
            s.pop();
        }

    }
}
