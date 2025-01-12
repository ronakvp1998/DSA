package com.questions.strivers.practice.ll;

public class RemoNFromEnd {

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5};
        int k = 2;
        Node head = arrayToLinkedList(arr);
        printLL(head);
        head = removeNode2(head,k);
        printLL(head);

    }

    // approach 2 fast and slow pointers TC->O(N) SC->O(1)
    public static Node removeNode2(Node head,int n){
        if(head == null){
            return null;
        }
        Node slow = head, fast = head;
        int count =0;
        while (count != n){
            fast = fast.next;
            count++;
        }
        if(fast == null){
            return head.next;
        }
        while (fast.next!=null){
            slow = slow.next;
            fast = fast.next;
        }

            slow.next = slow.next.next;
        return head;

    }

    // appraoch 1 count - n TC->O(2N) SC->O(1)
    public static Node removeNode(Node head,int n){
        if(head == null ){
            return null;
        }
        // find length of LL
        int length = 0;
        Node temp = head;
        while (temp != null){
            length++;
            temp = temp.next;
        }
        if(n == length){
            Node newHead = head.next;
            return newHead;
        }
        int k = length - n;
        length=0;
        temp = head;
        while (temp != null  ){
            if(length == k-1){
                temp.next = temp.next.next;
                return head;
            }
            length++;
            temp = temp.next;

        }
        return head;

    }

    // 2 traverse into ll
    public static void printLL(Node head){
        if(head == null ){
            System.out.println("Empty LL");
            return;
        }
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + "->");
            temp = temp.next;
        }
        System.out.println();
    }

    // 1 convert array into linkedlist
    public static Node arrayToLinkedList(int arr[]){
        if(arr.length <= 0){
            System.out.println("Empty LL");
            return null;
        }
        Node head = new Node(arr[0]);
        Node temp = head;
        for(int i=1;i< arr.length;i++){
            Node newNode = new Node(arr[i]);
            temp.next = newNode;
            temp = newNode;
        }
        return head;
    }
}
