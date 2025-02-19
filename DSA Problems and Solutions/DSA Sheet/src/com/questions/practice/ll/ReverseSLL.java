package com.questions.practice.ll;

public class ReverseSLL {

    public static void main(String[] args) {
        int arr[] = {1,3,2,5};
        Node head = convertArrToDLL(arr);
        traverseDLL(head);
        head = reverseLL2(head,null,head.next,head);
        traverseDLL(head);
    }

    // approach 2 using Recursion
    public static Node reverseLL2(Node head,Node prev,Node next,Node curr){
        if(curr == null){
            return prev;
        }
        next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
        return reverseLL2(head,prev,next,curr);
    }

    // approach 1 using link changes
    public static Node reverseLL(Node head){
        if(head == null || head.next == null){
            return head;
        }
        Node prev = null;
        Node next = null;
        Node temp = head;
        while (temp != null){
            next = temp.next;
            temp.next = prev;
            prev = temp;
            temp = next;
        }
        return prev;
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
            Node temp = new Node(arr[i], null);
            prev.next = temp; // Update the 'next' pointer of the previous node to point to the new node
            prev = temp; // Move 'prev' to the newly created node for the next iteration
        }
        return head; // Return the head of the doubly linked list
    }
}
