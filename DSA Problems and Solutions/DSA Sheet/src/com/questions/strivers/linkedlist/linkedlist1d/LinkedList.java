package com.questions.strivers.linkedlist.linkedlist1d;

class Node {

    int data;
    Node next;

    Node(int data1, Node next1){
        this.data = data1;
        this.next = next1;
    }

    Node(int data){
        this.data = data;
        this.next = null;
    }
}

public class LinkedList{
    public static void main(String[] args) {
        int arr[] = {2, 5, 6, 9, 8,44};
        Node head = arrayToLinkedList(arr);
//        System.out.println(head.data);
//        traverseLinkedList(head);
//        System.out.println(searchElement(9,head));
//        System.out.println(deleteKthElement(head,31).data);
//        System.out.println(deleteValue(head,9).data);
//        System.out.println(insertTail(head,44));
        System.out.println(insertAtKthPos(head,5,22));
        traverseLinkedList(head);

    }


    // 3 inserting at kth index
    public static Node insertAtKthPos(Node head,int k, int data){
        if (k == 1){
            return insertHead(head,data);
        }
        Node newNode = new Node(data);
        Node temp = head;
        int count = 0;
        while (temp != null){
            count++;
            if(count == k){
                break;
            }
            temp = temp.next;
        }
        Node next = temp.next;
        temp.next = newNode;
        newNode.next = next;

        return head;
    }


    // 2 inserting at tail
    public static Node insertTail(Node head, int data){
        Node newNode = new Node(data);
        if(head == null){
            return insertHead(head,data);
        }
        Node temp = head;
        while (temp.next !=null){
            temp = temp.next;
        }
        temp.next = newNode;
        return head;
    }

    // 1 inserting at head
    public static Node insertHead(Node head, int data){
        Node newNode = new Node(data);
        if(head == null){
            head = newNode;
        }

        newNode.next = head;
        head = newNode;
        return head;
    }

    // 4 delete value from LL
    public static Node deleteValue(Node head, int data){
        if(head == null){
            return head;
        }
        Node temp = head;
        Node prev = null;
        while (temp != null){
            if(temp.data == data){
                prev.next = temp.next;
                return head;
            }
            prev = temp;
            temp = temp.next;
        }
        return head;
    }

    // 3 delete kth element of LL
    public static Node deleteKthElement(Node head, int k){
        if(head == null){
            return head;
        }
        if(k == 1){
            deleteHead(head);
        }
        Node prev = null;
        Node temp = head;
        int count =0;
        while (count != k && temp.next.next != null){
            count++;
            if (count == k){
                prev.next = prev.next.next;
                break;
            }
            prev = temp;
            temp = temp.next;
        }
        return head;
    }


    // 2 delete tail of LL
    public static Node deleteTail(Node head){
        if(head == null || head.next == null){
            return head;
        }
        Node temp = head;
        while (temp.next.next != null){
            temp = temp.next;
        }
        temp.next = null;
        return head;
    }


    // 1 delete head of LL
    public static Node deleteHead(Node head){
        if(head == null){
            return head;
        }
        head = head.next;
        return head;
    }

    // 3 search an element in linkedlist
    public static int searchElement(int data, Node head){
        Node temp =head;
        int count=0;
        while (temp != null){
            if(temp.data == data){
                return count;
            }
            temp = temp.next;
            count++;
        }
        return -1;
    }

    // 2 traversal in LinkedList
    public static void traverseLinkedList(Node head){
        Node temp =head;
        int count=0;
        while (temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
            count++;
        }
        System.out.println("count " + count);
    }

    // 1 convert array into linkedlist
    public static Node arrayToLinkedList(int arr[]){
        Node head = new Node(arr[0]);
        Node mover = head;
        for(int i=1;i< arr.length;i++){
            Node temp = new Node(arr[i]);
            mover.next = temp;
            mover = temp;
        }
        return head;
    }

}

