package com.questions.strivers.linkedlist.mediumProblemsLL;

public class LoopStartingPoint {
    public static void main(String[] args) {

    }

    private static Node startingPoint(Node head){
        Node slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                slow = head;
                while (slow != fast){
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }
}
