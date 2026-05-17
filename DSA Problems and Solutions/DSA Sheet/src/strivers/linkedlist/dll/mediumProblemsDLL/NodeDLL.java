package com.questions.strivers.linkedlist.dll.mediumProblemsDLL;

public class NodeDLL {
    int data;
    NodeDLL prev;
    NodeDLL next;

    public NodeDLL(int data) {
        this.data = data;
    }

    public NodeDLL(int data, NodeDLL prev, NodeDLL next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}
