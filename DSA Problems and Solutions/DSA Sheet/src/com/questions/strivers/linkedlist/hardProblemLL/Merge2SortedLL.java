package com.questions.strivers.linkedlist.hardProblemLL;

import com.sun.source.tree.BreakTree;

public class Merge2SortedLL {
    public static void main(String[] args) {

    }

    private static Node merge2LL(Node head1, Node head2){
        Node t1 = head1, t2 = head2;
        Node dummyNode = new Node(-1);
        Node temp = dummyNode;
        while (t1 != null && t2 != null){
            if(t1.data < t2.data){
                temp.next = t1;
                temp = t1;
                t1 = t1.next;
            }else{
                temp.next = t2;
                temp = t2;
                t2=t2.next;
            }
        }
        if(t1 != null) temp.next = t1;
        else temp.next = t2;
        return dummyNode.next;
    }
}
