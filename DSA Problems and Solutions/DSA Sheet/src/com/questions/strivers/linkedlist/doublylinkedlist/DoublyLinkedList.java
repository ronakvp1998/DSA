package com.questions.strivers.linkedlist.doublylinkedlist;

public class DoublyLinkedList {

    public static void main(String[] args) {
        int arr[] = {12,5,8,7};
        Node head = convertArrToDLL(arr);
        traverseDLL(head);
//        head = deleteHead(head);
//        traverseDLL(head);
//        head = deleteTail(head);
//        head = deleteKElement(head,3);
//        head = insertHead(head,33);
//        head = insertTail(head,21);
//        head = insertBeforeKthElement(head,3,234);
        head = reverse(head);
        traverseDLL(head);
    }

    // reverse DDLL
    public static Node reverse(Node head){
        if(head == null || head.next == null){
            return head;
        }
        Node current = head;
        Node prev = null;

        while (current != null) {
            prev = current.prev;
            current.prev = current.next;
            current.next = prev;

            current = current.prev;
        }
        return prev.prev;
    }

    // 3 inserting before kth element
    public static Node insertBeforeKthElement(Node head,int k ,int data){
        if(k == 1){
            return insertHead(head,data);
        }
        Node temp = head;
        int count = 0;
        while (temp != null){
            count = count + 1;
            if(count == k){
                break;
            }
            temp = temp.next;
        }
        Node prev = temp.prev;
        Node newNode = new Node(data,temp,prev);
        prev.next = newNode;
        temp.prev = newNode;
        return head;
    }

    // 2 insertion of a node at tail
    public static Node insertTail(Node head, int data){
        if(head.next == null){
            return insertHead(head,data);
        }
        Node tail = head;
        while (tail.next != null){
            tail = tail.next;
        }
        Node prev = tail.prev;
        Node newNode = new Node(data,tail,prev);
        prev.next = newNode;
        tail.prev = newNode;
        return head;
    }

    // 1 insertion at head
    public static Node insertHead(Node head, int data){
        Node newNode = new Node(data,head,null);
        head.prev = newNode;
        return newNode;
    }

    // 4 deletion of a node
    public static void deleteNode(Node temp){
        Node prev = temp.prev;
        Node front = temp.next;
        if(front == null){
            prev.next = null;
            temp.prev = null;
            return;
        }
        prev.next = front;
        front.prev = prev;

        temp.next = null;
        temp.prev = null;
    }

    // 3 deletion kth element
    public static Node deleteKElement(Node head,int k){
        if(head == null || head.next == null){
            return null;
        }
        int count =0;
        Node KNode = head;
        while (KNode != null){
            count++;
            if(count != k && KNode.next == null){
                return head;
            }
            if(count == k){
                break;
            }
            KNode = KNode.next;
        }
        Node prev = KNode.prev;
        Node front = KNode.next;
        if(prev == null && front == null){
            return null;
        } else if (prev == null) {
            return deleteHead(head);
        } else if (front == null) {
            return deleteTail(head);
        }
        prev.next = front;
        front.prev = prev;

        KNode.next = null;
        KNode.prev = null;
        return head;
    }

    // 2 deletion of tail of LL
    public static Node deleteTail(Node head){
        if(head == null || head.next == null){
            return null;
        }
        Node tail = head;
        while (tail.next != null){
            tail = tail.next;
        }
        Node newTail = tail.prev;
        newTail.next = null;
        tail.prev = null;
        return head;
    }


    // 1 deletion head of node LL
    public static Node deleteHead(Node head){
        if (head == null || head.next == null) {
            return null; // Return null if the list is empty or contains only one element
        }

        Node prev = head;
        head = head.next;
        head.prev = null; // Set 'back' pointer of the new head to null
        prev.next = null;
        return head;
    }

    // traversal on DLL
    public static void traverseDLL(Node head){
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();

    }


    // convert arr to DLL
    public static Node convertArrToDLL(int arr[]){
        Node head = new Node(arr[0]); // Create the head node with the first element of the array
        Node prev = head; // Initialize 'prev' to the head node

        for (int i = 1; i < arr.length; i++) {
            // Create a new node with data from the array and set its 'back' pointer to the previous node
            Node temp = new Node(arr[i], null, prev);
            prev.next = temp; // Update the 'next' pointer of the previous node to point to the new node
            prev = temp; // Move 'prev' to the newly created node for the next iteration
        }
        return head; // Return the head of the doubly linked list
    }
}

class Node{
    int data;
    Node next;
    Node prev;

    Node(int data,Node next, Node prev){
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    Node(int data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }

}
