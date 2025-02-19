package com.questions.practice.ll;

public class Add2Num {

    public static void main(String[] args) {
        int arr1[] = {6,4,2};
        int arr2[] = {7,8,3};
        Node head1 = arrayToLinkedList(arr1);
        head1 = reverse(head1);
        Node head2 = arrayToLinkedList(arr2);
        head2 = reverse(head2);
        printLL(head1);
        printLL(head2);
        Node head = addTwoNum(head1,head2);
        printLL(head);
        printLL(reverse(head));
    }

    public static Node addTwoNum(Node head1, Node head2){
        Node t1 = head1, t2= head2,dummyNode = new Node(-1);
        Node curr = dummyNode;
        int carry = 0;
        while (t1 != null && t2 != null){
            int sum = carry;
            if(t1 != null) {
                sum = sum + t1.data;
            }
            if(t2 != null){
                sum = sum + t2.data;
            }
            Node node = new Node(sum%10);
            carry = sum/10;
            curr.next = node;
            curr = node;
            if(t1 != null){
                t1 = t1.next;
            }if(t2 != null){
                t2 = t2.next;
            }
        }
        if(carry > 0){
            Node node = new Node(carry);
            curr.next = node;
        }
        return dummyNode.next;
    }

    public static Node reverse(Node head){
        Node prev = null;
        Node curr = head;
        Node next = null;
        while (curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
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
