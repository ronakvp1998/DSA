package com.questions.strivers.linkedlist.old;

public class LinkedList {
    public static void main(String[] args) {
        int arr[] = {2,5,6,9,8,44};
        Node head = arrayToLL(arr);
        printLL(head);
        head = deleteKthElement(head , 4);
        printLL(head);
    }

    // 3 delete kth element of ll
    public static Node deleteKthElement(Node head, int k){
        if(head == null){
            return head;
        }
        if(k == 1){
            deleteHead(head);
        }
        Node temp = head;
        Node prev = null;
        int count =0 ;
        while (temp.next.next != null && count != k){
            count++;
            if(count == k){
                prev.next = temp.next;
            }
            prev = temp;
            temp = temp.next;
        }
        return head;
    }


    // 2 delete tail of ll
    public static Node deleteTail(Node head){
        if(head == null || head.next == null){
            return head;
        }
        Node temp = head;
        while (temp.next.next != null){
            temp = temp.next;
        }
        temp.next = null;
        return head;
    }

    // 1 delete head of LL
    public static Node deleteHead(Node head){
        if(head == null){
            return head;
        }
        head = head.next;
        return head;
    }

    public static int searchElement(int data, Node head){
        Node temp = head;
        int count = 0;
        while (temp != null){
            if(temp.data == data){
                return count;
            }
            temp = temp.next;
            count++;
        }
        return -1;
    }

    public static void printLL(Node head){
        Node temp = head;
        int count = 0;
        while (temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
            count++;
        }
        System.out.println("count " + count);
    }

    public static Node arrayToLL(int arr[]){
        Node head = new Node(arr[0]);
        Node temp = head;
        int i=1;
        while (i < arr.length){
            Node newNode = new Node(arr[i]);
            i++;
            temp.next = newNode;
            temp = newNode;
        }
        return head;
    }
}

class Node{
    Node next;
    int data;

    Node(int data){
        this.data = data;
        this.next = null;
    }

    Node(int data, Node next){
        this.data = data;
        this.next = next;
    }

}
