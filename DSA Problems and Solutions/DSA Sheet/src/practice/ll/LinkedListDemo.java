package com.questions.practice.ll;

public class LinkedListDemo {

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6};
        Node head = arrayToLinkedList(arr);
        printLL(head);
//        System.out.println(searchElement(head,2));
//        head = deleteFirst(head);
//        printLL(head);
//        head = deleteIndex(head,1);
//        printLL(head);
//        head = deleteLast(head);
//        printLL(head);
//        head = deleteValue(head,2);
//        printLL(head);
//        head = deleteAllOccValue(head,4);
//        printLL(head);
//        head = insertAtHead(head,10);
//        printLL(head);
//        head = insertAtIndex(head,2,33);
//        printLL(head);
//        head = insertLast(head,21);
//        printLL(head);
        head = reverse(head);
        printLL(head);
    }

    // 4 reverse LL
    private static Node reverse(Node head){
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

    // 3 insert at last
    private static Node insertLast(Node head,int value){
        if(head == null){
            return insertAtHead(head,value);
        }

        Node temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        Node newNode = new Node(value);
        temp.next = newNode;
        return head;
    }

    // 2 insert at index
    private static Node insertAtIndex(Node head,int index,int value){
        if(head == null && index != 0){
            return null;
        }
        if(index == 0){
            return insertAtHead(head,value);
        }
        Node temp = head;
        int count = 0;
        Node prev = null;
        while (index != count){
            if(count == value){
                Node newNode = new Node(value);
                prev.next = newNode;
                newNode.next = temp;
                break;
            }
            prev = temp;
            temp = temp.next;
        }
        return head;
    }

    // 1 insert at head
    private static Node insertAtHead(Node head, int value){
        if(head == null){
            Node newNode = new Node(value);
            return newNode;
        }
        Node newNode = new Node(value,head);
        head = newNode;
        return head;
    }

    // 5 delete all occ of value from LL
    private static Node deleteAllOccValue(Node head,int value){
        if(head == null){
            return null;
        }
        while (head != null && head.data == value){
            head = head.next;
        }
        Node temp = head;
        Node prev = null;
        while (temp != null){
            if(temp.data == value){
                prev.next = temp.next;
                temp = temp.next;
            }else {
            prev = temp;
            temp = temp.next;}
        }
        return head;
    }


    // 4 delete a value from LL
    private static Node deleteValue(Node head,int value){
        if(head == null){
            return null;
        }
        Node temp = head;
        Node prev = null;
        while (temp != null){
            if(temp.data == value && prev == null){
                head = head.next;
                break;
            }
            else if(temp.data == value){
                prev.next = temp.next;
                break;
            }
            prev = temp;
            temp = temp.next;
        }
        return head;
    }

    // 3 delete last
    private static Node deleteLast(Node head){
         if(head == null || head.next == null){
             return null;
         }

         Node temp = head;
         while (temp.next.next != null){
             temp = temp.next;
         }
         temp.next = null;
         return head;
    }

    // 2 delete a position or index
    private static Node deleteIndex(Node head, int index){
        if (head == null){
            return null;
        }
        if(index == 0){
            return deleteFirst(head);
        }
        Node temp = head;
        Node prev = null;
        int count = 0;
        while (temp != null){
            if(count == index){
                prev.next = temp.next;
                break;
            }
            count++;
            prev = temp;
            temp = temp.next;

        }
        return head;
    }

    // 1 delete head
    private static Node deleteFirst(Node head){
        if(head == null || head.next == null){
            System.out.println("Empty LL");
            return null;
        }
        head = head.next;
        return head;
    }

    // 3 search element in LL
    private static int searchElement(Node head,int target){
        if(head == null){
            System.out.println("Empty LL");
            return -1;
        }
        Node temp = head;
        int count = 0;
        while (temp != null){
            if(temp.data == target){
                return count;
            }else{
                temp = temp.next;
                count++;
            }
        }
        return -1;
    }

    // 2 traverse into ll
    private static void printLL(Node head){
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
    private static Node arrayToLinkedList(int arr[]){
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
