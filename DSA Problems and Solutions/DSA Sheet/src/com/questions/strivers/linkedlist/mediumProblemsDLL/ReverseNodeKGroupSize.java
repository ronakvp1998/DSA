//package com.questions.strivers.linkedlist.mediumProblemsDLL;
//
//public class ReverseNodeKGroupSize {
//
//    public static void main(String[] args) {
//
//    }
//
//    public static NodeDLL reverseKthGroup(NodeDLL head, int k){
//        NodeDLL temp = head, nextNode = null, prevNode = null;
//        while (temp != null){
//            NodeDLL kthNode = findKNode(temp,k);
//            if (kthNode == null){
//                if(prevNode != null) prevNode.next = temp;
//                break;
//            }
//
//            // seperate those k groups of nodes
//            nextNode = kthNode.next;
//            kthNode.next = null;
//
//            // reverse those k group of nodes
//            reverse(temp);
//
//            if(temp == head){
//                head = kthNode;
//            }else {
//                prevNode.next = kthNode;
//            }
//            prevNode = temp;
//            temp = nextNode;
//
//        }
//        return head;
//    }
//
//    public static Node
//
//    public static NodeDLL findKNode(NodeDLL temp, int k){
//        k -= 1;
//        while (temp != null && k >0 ){
//            k--;
//            temp = temp.next;
//        }
//        return temp;
//    }
//}
