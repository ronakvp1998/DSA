package com.questions.practice.ll;

public class PalindromeLL {
    public static void main(String[] args) {
        int arr[] = {1,1,2};
        Node head = convertArrToDLL(arr);
        System.out.println(checkPalindrome(head));
    }

    public static boolean checkPalindrome(Node head){
        if(head == null || head.next == null){
            return true;
        }

        // step1 find middle
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        // step2 reverse from middle

        // step3 check the data and reverse the middle again return true false

        return true;
    }

    public static Node reverse(Node head){
        if(head == null || head.next == null){
            return head;
        }

        Node newHead = reverse(head.next);
        Node front = head.next;
        front.next = head;
        head.next = null;
        return newHead;
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
