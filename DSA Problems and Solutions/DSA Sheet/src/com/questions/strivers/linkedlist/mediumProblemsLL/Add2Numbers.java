package com.questions.strivers.linkedlist.mediumProblemsLL;

public class Add2Numbers {
    public static void main(String[] args) {
        int []arr1 = {1,2,3};
        int []arr2 = {4,5,6};
        Node head1 = convertArrToDLL(arr1);
        Node head2 = convertArrToDLL(arr2);
        Node head = addTwoNumber(head1,head2);
        traverseDLL(head);
    }

    public static Node addTwoNumber(Node head1, Node head2){
        Node dummyHead = new Node(-1);
        Node curr = dummyHead;
        Node temp1 = head1;
        Node temp2 = head2;
        int carry = 0;
        while (temp1 != null || temp2 != null){
            int sum = carry;
            if(temp1 != null) sum += temp1.data;
            if(temp2 != null) sum += temp2.data;
            Node newNode = new Node(sum%10);
            carry = sum/10;
            curr.next = newNode;
            curr = curr.next;
            if(temp1 != null) temp1 = temp1.next;
            if(temp2 != null) temp2 = temp2.next;
        }
        if(carry > 0){
            Node newNode = new Node(carry);
            curr.next = newNode;
        }

        return dummyHead.next;
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

class Node{
    int data;
    Node next;
    Node prev;

    Node(int data, Node next, Node prev){
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    Node(int data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }

}
