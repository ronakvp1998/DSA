package com.questions.strivers.linkedlist.mediumProblemsLL;

public class Sort0s1s2sLL {

    public static void main(String[] args) {
        int arr[] = {1,0,1,2,0,2,0,1};
        Node head = convertArrToDLL(arr);
        traverseDLL(head);
        Node head1 = sort012s2(head);
        traverseDLL(head1);

    }

    // approach 2 with only 1 loop
    public static Node sort012s2(Node head){
        if(head == null || head.next == null) return head;
        Node zeroHead = new Node(-1);
        Node oneHead = new Node(-1);
        Node twoHead = new Node(-1);

        Node zero = zeroHead;
        Node one = oneHead;
        Node two = twoHead;
        Node temp = head;

        while (temp != null){
            if(temp.data == 0){
                zero.next = temp;
                zero = zero.next;
            } else if (temp.data == 1) {
                one.next = temp;
                one = one.next;
            }else {
                two.next = temp;
                two = two.next;
            }
            temp = temp.next;
        }
        zero.next = (oneHead.next != null) ? (oneHead.next) : (twoHead.next);
        one.next = twoHead.next;
        two.next = null;
        Node newHead = zeroHead.next;
        return newHead;
    }

    // approach 1 with 2 loops
    public static Node sort012s1(Node head){
        Node temp = head;
        int cout0=0,cout1=0,cout2=0;
        while (temp != null){
            if(temp.data == 0) cout0++;
            else if (temp.data == 1) cout1++;
            else cout2++;
            temp = temp.next;
        }
        temp = head;
        while (temp != null){
            if(cout0 != 0) {
                temp.data = 0;
                cout0--;
            } else if (cout1 != 0) {
                temp.data = 1;
                cout1--;
            }else {
                temp.data = 2;
                cout2--;
            }
            temp = temp.next;
        }
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


