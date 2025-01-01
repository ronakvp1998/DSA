package com.questions.strivers.linkedlist.mediumProblemsLL;

public class OddEvenLinkedList {
    public static void main(String[] args) {
        int []arr1 = {1,2,3,4,5,6,7};
        Node head = convertArrToDLL(arr1);
        traverseDLL(head);
        oddEvenLL(head);
        traverseDLL(head);

    }

    public static Node oddEvenLL(Node head){
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

    public static void traverseDLL(Node head){
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();

    }

    public static Node convertArrToDLL(int arr[]){
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


