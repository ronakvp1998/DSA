package LinkedListPart1;
// code 11 check if the LL is Palindrome or not
import LinkedListPart1.LinkedList.*;
public class Palindrome {


    // Slow fast approach to find the middle Node
    public Node findMid(Node head){
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null){
            slow = slow.next; // +1
            fast = fast.next.next; // +2
        }
        return slow; // slow is my middleNode
    }

    // check the palindrome
    public boolean checkPalindrome(){
        if(LinkedList.head == null || LinkedList.head.next == null){ // 0 or 1 condition
            return true;
        }
        // step 1 - find middle
        Node midNode = findMid(LinkedList.head);

        // step 2 - reverse 2nd half
        Node prev = null;
        Node curr = midNode;
        Node next ;
        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        Node right = prev; // right half head
        Node left = LinkedList.head;

        // step 3 - check left half & right half
        while(right != null){
            if(left.data != right.data){
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;

    }

    public static void main(String[] args) {
        Palindrome p = new Palindrome();
        LinkedList ll = new LinkedList();
        ll.addLast(1);
        ll.addLast(2);
//        ll.addLast(3);
//        ll.addLast(2);
//        ll.addLast(1);
        ll.printLL();
        System.out.println(p.checkPalindrome());
        ll.printLL();
    }
}
