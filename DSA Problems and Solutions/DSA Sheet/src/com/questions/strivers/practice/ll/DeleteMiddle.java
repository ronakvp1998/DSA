package com.questions.strivers.practice.ll;

public class DeleteMiddle {

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5};
        Node head = convertArrToDLL(arr);
        traverseDLL(head);
//        head = deleteMiddle(head);
        head = deleteMiddle1(head);
        traverseDLL(head);
    }

    // approach 2 fast and slow
    public static Node deleteMiddle1(Node head){
        if(head == null || head.next == null){
            return null;
        }
        Node slow = head, fast = head;
        fast = fast.next.next;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        slow.next = slow.next.next;
        return head;

    }

    // approach 1
    public static Node deleteMiddle(Node head){
        if(head == null || head.next == null){
            return null;
        }
        Node temp = head;
        int n1 = 0;
        while (temp != null){
            n1++;
            temp = temp.next;
        }
        int res = n1/2;
        temp = head;
        while (temp != null){
            res--;
            if(res == 0){
                temp.next = temp.next.next;
            }
            temp = temp.next;
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
