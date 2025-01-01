package com.questions.strivers.linkedlist.mediumProblemsLL;

public class MiddleNode {

    public static void main(String[] args) {
        int arr1[] = {1,2,3,4,5};
        int arr2[] = { 3,4,5,6};

        Node head1 = convertArrToDLL(arr1);
        Node head2 = convertArrToDLL(arr2);
        System.out.println(middleNode(head2).data);
    }

    public static Node middleNode(Node head){
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
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
