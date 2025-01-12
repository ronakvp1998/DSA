package com.questions.strivers.practice.ll;


public class Sor012LL {

    public static void main(String[] args) {
        int arr[] = {1,0,1,2,0,0,0,1,0,2,1};
        Node head = convertArrToDLL(arr);
        traverseDLL(head);
        head = sort0122(head);
        traverseDLL(head);
    }

    public static Node sort0122(Node head){
        if(head == null || head.next == null) return head;
        Node temp = head;
        Node dummy0 = new Node(-1);
        Node dummy1 = new Node(-1);
        Node dummy2 = new Node(-1);

        Node zero = dummy0, one = dummy1, two = dummy2;

        while (temp != null){
            if(temp.data == 0){
                zero.next = temp;
                zero = temp;
            } else if (temp.data == 1) {
                one.next = temp;
                one = temp;
            } else if (temp.data == 2) {
                two.next = temp;
                two = temp;
            }
            temp = temp.next;
        }
        zero.next = (dummy1.next != null) ? dummy1.next : dummy2.next;
        one.next = dummy2.next;
        two.next = null;
        return dummy0.next;
    }

    public static Node sort012(Node head){
        Node temp = head;
        int count0=0, count1=0, count2=2;
        while (temp != null){
            if(temp.data == 0){
                count0++;
            } else if (temp.data == 1) {
                count1++;
            } else if (temp.data == 2) {
                count2++;
            }
            temp = temp.next;
        }

        temp = head;
        while (temp != null){
            if(count0 != 0){
                temp.data = 0;
                count0--;
            } else if (count1 != 0) {
                temp.data = 1;
                count1--;
            } else if (count2 != 0) {
                temp.data = 2;
                count2--;
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
