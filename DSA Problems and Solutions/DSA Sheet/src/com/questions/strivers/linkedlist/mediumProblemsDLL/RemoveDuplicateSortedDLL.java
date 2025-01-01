package com.questions.strivers.linkedlist.mediumProblemsDLL;

public class RemoveDuplicateSortedDLL {

    public static void main(String[] args) {

    }

    public static NodeDLL removeDuplicates(NodeDLL head){
        NodeDLL temp = head;
        while (temp != null && temp.next != null){
            NodeDLL nextNode = temp.next;
            while (nextNode != null && nextNode.data == temp.data){
                nextNode = nextNode.next;
            }
            temp.next = nextNode;
            if(nextNode != null) nextNode.prev = temp;
            temp = temp.next;
        }
        return head;
    }
}
