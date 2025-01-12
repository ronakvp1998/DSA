package com.questions.strivers.practice.ll;

public class DeleteAllKey {

    public static void main(String[] args) {
        int arr[] = {10};
        Node head = convertArrToDLL(arr);
        traverseDLL(head);
        int key = 10;
        head = deleteAllKey(head,key);
        traverseDLL(head);
    }

    public static Node deleteAllKey(Node head, int key){
        if(head == null){
            return null;
        }

        Node temp = head;
        Node prev = null;
        while (head.data == key){
            prev = head;
            head = head.next;
        }
        while (temp != null && prev != null){
            if(temp.data == key){
                prev.next = temp.next;
                temp = temp.next;
            }else{
            prev = temp ;
            temp = temp.next;}
        }
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
            Node temp = new Node(arr[i], null);
            prev.next = temp; // Update the 'next' pointer of the previous node to point to the new node
            prev = temp; // Move 'prev' to the newly created node for the next iteration
        }
        return head; // Return the head of the doubly linked list
    }
}
