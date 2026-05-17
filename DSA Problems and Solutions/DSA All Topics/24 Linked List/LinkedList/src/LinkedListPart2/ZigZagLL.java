package LinkedListPart2;

import static LinkedListPart1.LinkedList.*;
import LinkedListPart1.LinkedList;

// code 4 -> zigzag linkedlist
public class ZigZagLL {

    public void zigZag(){
        // 1 find mid
        Node slow = head;
        Node fast = head.next;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        Node mid = slow;

        // 2 reverse 2nd half
        Node curr = mid.next;
        mid.next = null;
        Node prev = null;
        Node next ;
        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        Node left = head;
        Node right = prev;
        Node nextL, nextR;

        // 3 alt merge - zig-zag merge
        while(left != null && right != null){
            nextL = left.next;
            left.next = right;
            nextR = right.next;
            right.next = nextL;

            left = nextL;
            right = nextR;
        }

    }

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        ZigZagLL zzl = new ZigZagLL();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);
        ll.addLast(4);
        ll.addLast(5);
        ll.addLast(5);

        LinkedList.printLL();
        zzl.zigZag();
        LinkedList.printLL();

    }
}
