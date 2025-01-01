package com.questions.strivers.linkedlist.mediumProblemsLL;

public class DeleteMiddleNode {

    public static void main(String[] args) {

    }

    public static Node deleteMiddle(Node head) {
        if(head == null || head.next == null) {
            return null;
        }
        Node slow = head, fast = head;
        fast = fast.next.next;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        slow.next = slow.next.next;
        return head;
    }
}
