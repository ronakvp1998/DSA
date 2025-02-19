package com.questions.practice.ll;

import java.util.HashMap;
import java.util.Map;

public class DetectLoop {
    public static void main(String[] args) {
        int arr1[] = {1,2,3,4,5,6,7,8,9};
        Node head1 = convertArrToDLL(arr1);

        Node tempInt = head1;
        while (tempInt != null){
            if(tempInt.data == 3){
                break;
            }
            tempInt = tempInt.next;
        }

        Node tempLast = head1;
        while (tempLast.next != null){
            tempLast = tempLast.next;
        }

//        tempLast.next = tempInt;

        System.out.println(detectLoop1(head1));
    }

    // approach 2 using fast slow pointer
    public static boolean detectLoop1(Node head){
        Node slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) return true;
        }
        return false;
    }

    // approach 1 using hashmap TC->O(n*2*logn) SC->O(n)
    public static boolean detectLoop(Node head){
        Map<Node,Integer> map = new HashMap<>();
        Node temp = head;
        while (temp != null){
            if(map.get(temp) != null){
                return true;
            }else{
                map.put(temp,1);
                temp = temp.next;
            }
        }
        return false;
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
