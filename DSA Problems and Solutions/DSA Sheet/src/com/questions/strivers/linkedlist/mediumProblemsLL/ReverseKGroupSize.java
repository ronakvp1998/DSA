package com.questions.strivers.linkedlist.mediumProblemsLL;

public class ReverseKGroupSize {
    public static void main(String[] args) {

    }

    public static Node getKthNode(Node temp, int k){
        k -= 1;
        while (temp != null && k > 0 ){
            k--;
            temp = temp.next;
        }
        return temp;
    }

    public static Node kReverse(Node head, int k){
        Node temp = head;
        Node prevLast = null;
        while (temp != null){
            Node kthNode = getKthNode(temp, k);
//            if(kthNode)
        }
        return null;
    }
}
