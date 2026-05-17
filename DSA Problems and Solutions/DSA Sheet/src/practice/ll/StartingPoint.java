package com.questions.practice.ll;

import java.util.HashMap;
import java.util.Map;

public class StartingPoint {

    public static void main(String[] args) {
        int arr1[] = {1,2,3,15,4,13,6,7,8,9};
        Node head1 = convertArrToDLL(arr1);

        Node tempInt = head1;
        while (tempInt != null){
            if(tempInt.data == 15){
                break;
            }
            tempInt = tempInt.next;
        }

        Node tempLast = head1;
        while (tempLast.next != null){
            tempLast = tempLast.next;
        }

        tempLast.next = tempInt;
        System.out.println(startingPoint1(head1).data);

    }

    // approach 2 fast and slow
    private static Node startingPoint1(Node head){
        Node slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                slow = head;
                while (slow != fast){
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

    // approach 1 hashmap TC->O(N)*[O(N) + O(N)]
    private static Node startingPoint(Node head){
        Map<Node,Integer> map = new HashMap<>();
        Node temp = head;
        while (temp != null){
            if(map.get(temp) != null){
                return temp;
            }
            map.put(temp,1);
            temp = temp.next;
        }
        return null;
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
            Node temp = new Node(arr[i], null);
            prev.next = temp; // Update the 'next' pointer of the previous node to point to the new node
            prev = temp; // Move 'prev' to the newly created node for the next iteration
        }
        return head; // Return the head of the doubly linked list
    }
}
