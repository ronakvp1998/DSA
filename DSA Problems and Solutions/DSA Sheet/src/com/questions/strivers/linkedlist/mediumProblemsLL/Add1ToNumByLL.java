package com.questions.strivers.linkedlist.mediumProblemsLL;

public class Add1ToNumByLL {

    public static void main(String[] args) {
        int arr[] = { 1,5,9};
        Node head = convertArrToDLL(arr);
        traverseDLL(head);
        Node newHead = addOne(head);
        traverseDLL(newHead);
    }

    // recursive approach
    public static Node addOne(Node head){
        int carry = helper(head);
        if(carry == 1){
            Node newNode = new Node(1);
            newNode.next = head;
            return newNode;
        }
        return head;
    }

    public static int helper(Node temp){
        if(temp == null)    return 1;
        int carry = helper(temp.next);
        temp.data = temp.data + carry;
        if (temp.data < 10){
            return 0;
        }else {
            temp.data = 0;
            return 1;
        }
    }

    // iterative approach
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
