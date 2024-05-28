package LinkedListPart2;

import static LinkedListPart1.LinkedList.*;

import LinkedListPart1.LinkedList;

// code 3 Linked List merge sort
public class LinkedListMergeSort {

    private Node getMid(Node head){
        Node slow = head;
        Node fast = head.next;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;

        }

        return slow;  // mid node
    }

    private Node merge(Node head1, Node head2){ // head1-> left half head , head2-> right half head
        Node mergedLL = new Node(-1);
        Node temp = mergedLL;
        while(head1 != null && head2 != null){
            if(head1.data <= head2.data){
                temp.next = head1;
                head1 = head1.next;
                temp = temp.next;
            }else{
                temp.next = head2;
                head2 = head2.next;
                temp = temp.next;
            }
        }

        while(head1 != null){
            temp.next = head1;
            head1 = head1.next;
            temp = temp.next;
        }

        while(head2 != null){
            temp.next = head2;
            head2 = head2.next;
            temp = temp.next;
        }

        return mergedLL.next;
    }

    public Node mergeSort(Node head){
        // base case
        if(head == null || head.next == null){
            return head;
        }

        // find middle -> last Node of left half
        Node mid = getMid(head);

        // left & right call merge sort
        Node rightHead = mid.next;
        mid.next = null;

        // call merge sort for left half
        Node newLeft = mergeSort(head);

        // call merge sort for right half
        Node newRight = mergeSort(rightHead);

        // merge
        return merge(newLeft,newRight);
    }

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        LinkedListMergeSort lms = new LinkedListMergeSort();
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addFirst(5);
        // 5->4->3->2->1
        LinkedList.printLL();
        ll.head = lms.mergeSort(ll.head);
        LinkedList.printLL();

    }
}
