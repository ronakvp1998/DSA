package LinkedListPart2;

// code 1 -> check cycle exists in LL

import LinkedListPart1.LinkedList;
import LinkedListPart1.LinkedList.*;

import static LinkedListPart1.LinkedList.*;

public class IsCycle {


    public static void main(String[] args) {
        head = new Node(1);
        Node temp = new Node(2);
        head.next = temp;
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = temp;
        // 1->2->3->1(head)  true
//        System.out.println(isCycle());
        // 1->2->3->null  false
//        System.out.println(isCycle());
        System.out.println(isCycle());
//        LinkedList.printLL();
        removeCycle();
        System.out.println(isCycle());
        LinkedList.printLL();

    }

    public static boolean isCycle(){    // Floyd's Cycle finding algorithm
        Node slow = head;
        Node fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next; // +1
            fast = fast.next.next;  // +2
            if(slow == fast){
                return true; // cycle exists
            }
        }
        return false; // cycle does not exists
    }

    public static void removeCycle(){
        // detect cycle
        Node slow = head;
        Node fast = head;
        boolean cycle = false;
        while(fast != null && fast.next!= null){
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow){
                cycle = true;
                break;
            }
        }
        if(cycle == false){
            return;
        }
        // find meeting point
        slow = head;  // slow is now point to head, fast is still at the last meeting point
        Node prev = null;  // last node
        while(slow != fast){
            prev = fast;
            slow = slow.next;
            fast = fast.next;
        }

        // remove cycle -> last.next = null
        prev.next = null;

    }
}
