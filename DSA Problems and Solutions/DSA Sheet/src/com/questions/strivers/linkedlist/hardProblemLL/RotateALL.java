package com.questions.strivers.linkedlist.hardProblemLL;


public class RotateALL {

    public static void main(String[] args) {

    }

    public static Node rotateLL(Node head,int k){
        int len = 1;
        Node tail = head;
        while (tail.next != null){
            len++;
            tail = tail.next;
        }
        if(k%len == 0) return head;
        k = k%len;
        tail.next = head;

        Node newLastNode = findKthNode(head,len-k);
        head = newLastNode.next;
        newLastNode.next  = null;
        return head;
    }

    public static Node findKthNode(Node temp , int k){
        int count = 1;
        while (temp != null){
            if(count == k) return temp;
            count++;
            temp = temp.next;
        }
        return temp;
    }
}
