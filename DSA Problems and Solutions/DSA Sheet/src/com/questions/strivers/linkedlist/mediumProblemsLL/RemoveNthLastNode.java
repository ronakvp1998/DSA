package com.questions.strivers.linkedlist.mediumProblemsLL;

public class RemoveNthLastNode {
    public static void main(String[] args) {
        int []arr1 = {1,2,3,4,5,6,7};
        Node head = convertArrToDLL(arr1);
        traverseDLL(head);
        deleteNthNode2(head,4);
        traverseDLL(head);
    }

    // approach 2
    public static Node deleteNthNode2(Node head, int n){
        Node fast = head;
        Node slow = head;
        for(int i=0;i<n;i++){
            fast = fast.next;
        }
        if(fast == null) return head.next;
        while (fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }
        Node delNode = slow.next;
        slow.next = slow.next.next;
        return head;

    }

    // approach 1
    public static Node deleteNthNode(Node head,int n){
        int count=0;
        Node temp=head;
        while (temp != null){
            count++;
            temp = temp.next;
        }
        if(count == n){
            Node newHead = head.next;
            return newHead;
        }
        int res = count - n;
        temp = head;
        while (temp != null){
            res--;
            if(res == 0){
                break;
            }
            temp = temp.next;
        }
        Node deleteNode = temp.next;
        temp.next = temp.next.next;
        return head;
    }

    public static void traverseDLL(Node head){
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();

    }

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
