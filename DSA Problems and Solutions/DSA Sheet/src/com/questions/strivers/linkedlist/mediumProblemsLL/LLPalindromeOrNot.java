package com.questions.strivers.linkedlist.mediumProblemsLL;

import java.util.Stack;

public class LLPalindromeOrNot {

    public static void main(String[] args) {
        int arr[] = {1,2,3,3,2,1};
        int arr1[] = {1,2,3,4,2,1};
        Node head  = convertArrToDLL(arr);
//        System.out.println(checkIsPalindrome(head));
        System.out.println(checkIsPalindrome2(head));
    }

    // approach 2
    private static boolean checkIsPalindrome2(Node head) {
        Stack<Integer> st = new Stack<>();
        Node temp = head;
        while (temp != null){
            st.push(temp.data);
            temp = temp.next;
        }

        temp = head;
        while (temp != null){
            if(temp.data != st.pop())   return false;
            temp = temp.next;
        }
        return true;
    }

    // approach 1
    private static boolean checkIsPalindrome(Node head){
        if(head == null || head.next == null ) return true;
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        Node newHead = reverseLinkedList(slow.next);
        Node first = head;
        Node second = newHead;
        while(second != null){
            if(first.data != second.data){
                reverseLinkedList(newHead);
                return false;
            }
            first = first.next;
            second = second.next;
        }
        reverseLinkedList(newHead);
        return true;
    }

    private static Node reverseLinkedList(Node head){
        if(head == null || head.next == null) return head;
        Node newHead = reverseLinkedList(head.next);
        Node front = head.next;
        front.next = head;
        head.next = null;
        return newHead;
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
