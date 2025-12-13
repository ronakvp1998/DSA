package com.questions.strivers.linkedlist.mediumProblemsDLL;

public class DeleteAllOccOfKey {

    public static void main(String[] args) {

    }

    private static NodeDLL deleteAllOccKey(NodeDLL head, int key){

        NodeDLL temp = head;
        while (temp != null){
            if(temp.data == key){
                if(temp == head) head = head.next;
                NodeDLL nextNode = temp.next;
                NodeDLL prevNode = temp.prev;

                if(nextNode != null){
                    nextNode.prev = prevNode;
                }if(prevNode != null){
                    prevNode.next = nextNode;
                }
                temp = nextNode;
            }else{
                temp = temp.next;
            }
        }
        return head;
    }

}
