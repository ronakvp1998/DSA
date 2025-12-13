package com.questions.strivers.linkedlist.mediumProblemsLL;

import java.util.ArrayList;
import java.util.List;

public class OddEvenLinkedList {
    public static void main(String[] args) {
        int []arr1 = {1,2,3,4,5,6,7};
        Node head = convertArrToDLL(arr1);
        traverseDLL(head);
//        oddEvenLL(head);
//        traverseDLL(head);
        head = segregateOE(head);
        traverseDLL(head);
    }

    // approach 2
    private static Node segregateOE(Node head){
        if(head == null || head.next == null){
            return head;
        }
        Node odd=head, even=head.next, evenHead=head.next;
        while (even != null && even.next != null){
            odd.next = odd.next.next;
            even.next = even.next.next;

            odd = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    public Node oddEvenList(Node head) {
        if(head == null || head.next == null) return head;
        Node odd=head, even=head.next, evenHead=head.next;
        while (even != null && even.next != null){
            odd.next = odd.next.next;

            even.next = even.next.next;

            odd = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    // approach 1 TC->O(2N) SC->O(N)
    private static Node segregateEO(Node head){
        if(head == null || head.next == null){
            return head;
        }
        // odd
        List<Integer> list = new ArrayList<>();
        Node temp = head;
        while (temp != null && temp.next.next !=null){
            list.add(temp.data);
            temp = temp.next.next;
        }
        if(temp != null){
            list.add(temp.data);
        }

        // even
        temp = head.next;
        while (temp != null && temp.next != null){
            list.add(temp.data);
            temp = temp.next.next;
        }
        if(temp != null){
            list.add(temp.data);
        }
        int i=0;
        temp = head;
        while (temp != null){
            temp.data = list.get(i);
            i++;
            temp = temp.next;
        }
        return head;
    }

    // approach 2 TC->O(n/2)
    private static Node oddEvenLL(Node head){
        if(head == null || head.next == null) return head;
        Node odd=head, even=head.next, evenHead=head.next;
        while (even != null && even.next != null){
            odd.next = odd.next.next;

            even.next = even.next.next;
            odd = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    private static void traverseDLL(Node head){
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();

    }

    private static Node convertArrToDLL(int arr[]){
        Node head = new Node(arr[0]); // Create the head node with the first element of the array
        Node prev = head; // Initialize 'prev' to the head node

        for (int i = 1; i < arr.length; i++) {
            // Create a new node with data from the array and set its 'back' pointer to the previous node
            Node temp = new Node(arr[i], null, prev);
            prev.next = temp; // Update the 'next' pointer of the previous node to point to the new node
            prev = temp; // Move 'prev' to the newly created node for the next iteration
        }
        return head; // Return the head of the doubly linked list
    }

}


