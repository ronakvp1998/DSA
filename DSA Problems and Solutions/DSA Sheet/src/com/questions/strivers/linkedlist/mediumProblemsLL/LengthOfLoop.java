package com.questions.strivers.linkedlist.mediumProblemsLL;


import java.util.HashMap;
import java.util.Map;

public class LengthOfLoop {
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

        tempLast.next = tempInt;

        System.out.println(detectLoop1(head1));
    }

    // approach 2 using fast slow pointer
    private static int detectLoop1(Node head){
        Node slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) {
                int length = 1;
                fast = fast.next;
                while (fast != slow){
                    length++;
                    fast = fast.next;
                }
                return length;
            }
        }
        return 0;
    }

    // approach 1 using hashmap TC->O(n*2*logn) SC->O(n)
    private static int detectLoop(Node head){
        Map<Node,Integer> map = new HashMap<>();
        int timer = 1;
        Node temp = head;
        while (temp != null){
            if(map.get(temp) != null){
                int value = map.get(temp);
                return timer - value;
            }else{
                map.put(temp,timer);
                timer++;
                temp = temp.next;
            }
        }
        return 0;
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
            Node temp = new Node(arr[i]);
            prev.next = temp; // Update the 'next' pointer of the previous node to point to the new node
            prev = temp; // Move 'prev' to the newly created node for the next iteration
        }
        return head; // Return the head of the doubly linked list
    }
}
