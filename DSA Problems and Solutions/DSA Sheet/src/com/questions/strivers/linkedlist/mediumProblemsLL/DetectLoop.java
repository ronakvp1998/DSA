package com.questions.strivers.linkedlist.mediumProblemsLL;

public class DetectLoop {

    public static void main(String[] args) {


    }

    public static boolean checkLoop(Node head){
        Node slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) return true;
        }
        return false;
    }

    // iterative approach
    public static void traverseDLL(Node head){
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();

    }


}
