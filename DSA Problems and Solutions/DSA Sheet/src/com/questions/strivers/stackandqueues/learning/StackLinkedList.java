package com.questions.strivers.stackandqueues.learning;

class Node{
    Node next;
    int data;
    int size;

    public Node(int data){
        this.data = data;
        this.next = null;
    }
}

public class StackLinkedList {
    Node head;
    int size;

    public StackLinkedList(){
        this.head = null;
        this.size = 0;
    }

    public static void main(String[] args) {

    }

    public Node stackPush(int x){
        Node newNode = new Node(x);
        newNode.next = head;
        head = newNode;
        System.out.println("Element pushed");
        size++;
        return head;
    }

    public Node stackPop(){
        if(head == null) return null;
        int topData = head.data;
        System.out.println("proped " + topData);
        Node temp = head;
        head = head.next;
        return head;
    }

    public int stackSize(){
        return size;
    }

    public boolean stackIsEmpty(){
        return head == null;
    }
}
