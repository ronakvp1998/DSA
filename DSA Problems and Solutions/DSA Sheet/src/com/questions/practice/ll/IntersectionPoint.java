package com.questions.practice.ll;

import java.util.HashMap;
import java.util.Map;

public class IntersectionPoint {

    public static void main(String[] args) {
        int arr1[] = {3,1};
        int arr2[] = {1,2,4,5};
        int arr3[] = {4,6,2};

        Node head1 = convertArrToDLL(arr1);
        Node head2 = convertArrToDLL(arr2);
        Node head3 = convertArrToDLL(arr3);

        Node temp = head1;
        while (temp.next!=null){
            temp = temp.next;
        }
        temp.next = head3;

        temp = head2;
        while (temp.next!=null){
            temp = temp.next;
        }
        temp.next = head3;

        Node res = intersectPoint2(head1,head2);
        System.out.println(res.data);
    }

    // apporach3 TC->O(n1+n2) SC->O(1)
    public static Node intersectPoint2(Node head1, Node head2){
        if(head1 == null || head2 == null){
            return null;
        }

        Node t1 = head1, t2 = head2;
        while (t1 != t2){
            t1 = t1.next;
            t2 = t2.next;
            if(t1 == t2) return t1;
            if(t1 == null) t1 = head2;
            if(t2 == null) t2 = head1;
        }
        return t1;
    }


    // approach2 optimzed space O(1) TC->O(n1+n1)+O(n1-n2)
    public static Node intersectPoint1(Node head1, Node head2){
        // step1 find the length of both LL
        Node temp1 = head1;
        int n1 = 0;
        while (temp1 != null){
            n1++;
            temp1 = temp1.next;
        }

        Node temp2 = head2;
        int n2 = 0;
        while (temp2 != null){
            n2++;
            temp2 = temp2.next;
        }

        if(n1 < n2){
            // smaller, larger, difference
            return collisionPoint(head1,head2,n2-n1);
        }else {
            return collisionPoint(head2,head1,n1-n2);
        }
    }

    public static Node collisionPoint(Node head1, Node head2, int n){
        while (n != 0){
            n--;
            head2 = head2.next;
        }

        while (head1 != head2){
            head1 = head1.next;
            head2 = head2.next;
        }
        return head1;
    }

    // approach1 using hashmap TC->O(N) + O(N + logN) SC->O(N1) or O(N2)
    public static Node intersectPoint(Node head1, Node head2){
        Map<Node,Integer> map = new HashMap<Node,Integer>();
        Node temp = head1;
        while (temp != null){
            map.put(temp,1);
            temp = temp.next;
        }
        temp = head2;
        while (temp != null){
            if(map.get(temp) != null){
                return temp;
            }
            temp = temp.next;
        }
        return null;
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
