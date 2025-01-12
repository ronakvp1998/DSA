package com.questions.strivers.practice.ll;

import java.util.ArrayList;
import java.util.List;

public class SegregateEvenOdd {
    public static void main(String[] args) {
        int arr[] = {1,3,4,2,5,6};
        Node head = arrayToLinkedList(arr);
        printLL(head);
        head = segregateEO(head);
        printLL(head);
    }



    // approach 1
    public static Node segregateEO(Node head){
        // odd
        List<Integer> list = new ArrayList<>();
        Node temp = head;
        while (temp != null && temp.next.next !=null){
            list.add(temp.data);
            temp = temp.next.next;
        }
        if(temp != null){
            list.add(temp.data);
        }

        // even
        temp = head.next;
        while (temp != null && temp.next != null){
            list.add(temp.data);
            temp = temp.next.next;
        }
        if(temp != null){
            list.add(temp.data);
        }
        int i=0;
        temp = head;
        while (temp != null){
            temp.data = list.get(i);
            i++;
            temp = temp.next;
        }
        return head;
    }

    // 2 traverse into ll
    public static void printLL(Node head){
        if(head == null ){
            System.out.println("Empty LL");
            return;
        }
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + "->");
            temp = temp.next;
        }
        System.out.println();
    }

    // 1 convert array into linkedlist
    public static Node arrayToLinkedList(int arr[]){
        if(arr.length <= 0){
            System.out.println("Empty LL");
            return null;
        }
        Node head = new Node(arr[0]);
        Node temp = head;
        for(int i=1;i< arr.length;i++){
            Node newNode = new Node(arr[i]);
            temp.next = newNode;
            temp = newNode;
        }
        return head;
    }



}
