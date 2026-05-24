package practice;

import strivers.linkedlist.ll.linkedlist1d.DeleteNodeInLinkedList;

import java.util.*;

public class Test {

    static class Node{
        Node next;
        int data;
        Node(int data){
            this.data = data;
        }
        Node(int data,Node next){
            this.data = data;
            this.next = next;
        }
    }
    
    private static boolean isPalindrome(Node head){
        if(head == null){
            return true;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        if(fast.next != null){
            slow = slow.next;
        }
        Node midHead = slow;
        Node reversedMidHead = reverseLLTest(midHead);

        Node temp = head;
        while (temp.next != null){
            if(temp.data != reversedMidHead.data){
                return false;
            }
            temp = temp.next;
            reversedMidHead = reversedMidHead.next;
        }
        return true;
    }

    private static Node reverseLLTest(Node head){
        Node temp = head;
        Node prev = null;
        while (temp != null){
            Node t = temp.next;
            temp.next = prev;
            prev = temp;
            temp = t;
        }
        return prev;
    }

    private static Node startLL(Node head){
        Node fast = head;
        Node slow = head;
        boolean isCycle = false;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                isCycle = true;
                break;
            }
        }
        if(!isCycle){
            return null;
        }
        slow = head;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    private static boolean detectLoop(Node head){
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                return true;
            }
        }
        return false;
    }

    private static Node reverseLL(Node head){
        Node temp = head;
        Node prev = null;
        while (temp != null){
            Node next = temp.next;
            temp.next = prev;
            prev = temp;
            temp = next;
        }
        return prev;
    }

    private static Node middle(Node head){
        Node slow = head;
        Node fast = head;
        while (fast.next != null){
            if(fast.next.next != null){
                fast = fast.next.next;
            }else{
                break;
            }
            slow = slow.next;
        }
        return slow;
    }

    private static Node reverse(Node head){
        if(head == null || head.next == null){
            return head;
        }
        Node current = head;
        Node prev = null;
        while (current != null){
            prev = current.prev;
            current.prev = current.next;
            current.next = prev;
            current = current.prev;
        }
        return prev.prev;
    }

    // delete by index
    private static Node deleteIndex(Node head,int index){
        if(head == null){
            return null;
        }
        if(index == 0){
            return deleteHead(head);
        }
        Node temp = head;
        int count = 0 ;
        while (temp != null){
            if(count == index){
                break;
            }
            count++;
            temp = temp.next;
        }
        if(temp == null){
            return head;
        }
        Node prev = temp.prev;
        Node next = temp.next;

        prev.next = next;
        if(next != null){
            next.prev = prev;
        }
        temp.prev = null;
        temp.next = null;
        return head;
    }

    // delete by value
    private static Node deleteValue(Node head,int value){
        if(head == null ){
            return null;
        }
        Node temp = head;
        while (temp != null){
            if(temp.data == value){
                break;
            }
            temp = temp.next;
        }
        if(temp == null){
            return head;
        }

        if(temp == head){
            head = head.next;
            if(head != null){
                head.prev = null;
            }
            temp.next = null;
            return head;
        }
        Node prev = temp.prev;
        prev.next = temp.next;
        if(temp.next != null){
            temp.next.prev = prev;
        }
        temp.prev = null;
        temp.next = null;
        return head;
    }

    // delete tail
    private static Node deleteTail(Node head){
        if(head == null || head.next == null){
            return null;
        }
        Node temp = head;
        while (temp.next.next != null){
            temp = temp.next;
        }
        Node tail = temp.next;
        temp.next = null;
        tail.prev = null;
        return head;
    }

    // delete head
    private static Node deleteHead(Node head){
        if(head == null || head.next == null){
            return null;
        }
        Node prevHead = head;
        head = head.next;

        head.prev = null;
        prevHead.next = null;

        return head;
    }

    // insert at position
    private static Node insertBeforeKthPos(Node head,int k,int data){
        if(k == 0){
            return insertHead(head,data);
        }
        Node temp = head;
        int count = 0;
        while (temp != null){
            if(count == k){
                break;
            }
            count++;
            temp = temp.next;
        }
        if(temp == null){
            return insertTail(head,data);
        }
        Node node = new Node(data,temp,temp.prev);
        temp.prev.next = node;
        temp.prev = node;
        return head;
    }

    // insert at tail
    private static Node insertTail(Node head,int data){
        if(head == null ){
            return new Node(data);
        }
        Node temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        Node node = new Node(data,null,temp);
        temp.next = node;
        return head;
    }

    // array into DLL
    private static Node arrToDLL(int arr[]){
        if(arr == null || arr.length == 0){
            return null;
        }
        Node head = new Node(arr[0]);
        Node temp = head;
        for(int i =1;i<arr.length;i++){
            Node node = new Node(arr[i],null,temp);
            temp.next = node;
            temp = temp.next;
        }
        return head;
    }

    // insert at head
    private static Node insertHead(Node head,int data){
        if(head == null){
            return new Node(data);
        }
        Node node = new Node(data,head,null);
        head.prev = node;
        return node;
    }

}
