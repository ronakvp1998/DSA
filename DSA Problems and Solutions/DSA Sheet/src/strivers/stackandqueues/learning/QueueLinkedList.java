package com.questions.strivers.stackandqueues.learning;

public class QueueLinkedList {

    Node head;
    int size;

    public QueueLinkedList(){
        this.head = null;
        this.size = 0;
    }

    public void push(int x){
        Node newNode = new Node(x);
        if(head == null){
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        temp.next = newNode;
        return;
    }

    public int pop(){
        if(head == null){
            return -1;
        }
        Node res = head;
        head = head.next;
        return res.data;
    }

}
