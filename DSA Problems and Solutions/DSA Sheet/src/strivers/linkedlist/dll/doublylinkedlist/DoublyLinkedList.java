/**
 * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * Objective: Provide a masterclass-level solution for Doubly Linked List Operations.
 *
 * ==========================================================================================
 * 1. Header & Problem Context
 * ==========================================================================================
 * Problem Statement:
 * Design and implement a Doubly Linked List (DLL) and its core operations.
 * A DLL is a linked data structure that consists of a set of sequentially linked records 
 * called nodes. Each node contains three fields: an integer data value, a 'prev' link to 
 * the previous node, and a 'next' link to the next node.
 *
 * Implement the following functionalities:
 * 1. Convert an Array to a DLL.
 * 2. Traverse and print the DLL.
 * 3. Reverse the DLL in-place.
 * 4. Insertions: At Head, At Tail, and Before the K-th element.
 * 5. Deletions: At Head, At Tail, the K-th element, and a specific given node.
 *
 * Examples:
 * Example 1 (Reversal):
 * Input: DLL = 12 <-> 5 <-> 8 <-> 7
 * Output: 7 <-> 8 <-> 5 <-> 12
 *
 * Example 2 (Insert Tail):
 * Input: DLL = 12 <-> 5 <-> 8 <-> 7, value = 21
 * Output: 12 <-> 5 <-> 8 <-> 7 <-> 21
 *
 * Constraints:
 * - 0 <= Number of nodes in the DLL <= 10^5
 * - -10^5 <= Node.data <= 10^5
 * - 1 <= K <= Length of DLL + 1
 *
 * ==========================================================================================
 *
 * ==========================================================================================
 */

package strivers.linkedlist.dll.doublylinkedlist;

public class DoublyLinkedList {

    /**
     * ==========================================================================================
     * 4. Testing Suite
     * ==========================================================================================
     */
    public static void main(String[] args) {
        int[] arr = {12, 5, 8, 7};

        System.out.println("--- Array to DLL & Traversal ---");
        Node head = convertArrToDLL(arr);
        traverseDLL(head);

        System.out.println("\n--- Reverse DLL ---");
        head = reverse(head);
        traverseDLL(head);

        System.out.println("\n--- Insertions ---");
        head = insertHead(head, 33);
        System.out.print("After Insert Head (33): ");
        traverseDLL(head);

        head = insertTail(head, 21);
        System.out.print("After Insert Tail (21): ");
        traverseDLL(head);

        head = insertBeforeKthElement(head, 3, 234);
        System.out.print("After Insert before 3rd Element (234): ");
        traverseDLL(head);

        System.out.println("\n--- Deletions ---");
        head = deleteHead(head);
        System.out.print("After Delete Head: ");
        traverseDLL(head);

        head = deleteTail(head);
        System.out.print("After Delete Tail: ");
        traverseDLL(head);

        head = deleteKElement(head, 3);
        System.out.print("After Delete 3rd Element: ");
        traverseDLL(head);
    }

    /**
     * ==========================================================================================
     * Phase 1: Reversal & Traversal Operations
     * ==========================================================================================
     *
     * Detailed Intuition (Reverse):
     * To reverse a DLL, we simply need to swap the `next` and `prev` pointers for every node 
     * in the list. By traversing linearly and swapping these pointers, the list naturally 
     * inverses. Finally, the old tail becomes the new head.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) to traverse the list once.
     * - Space Complexity: O(1) as we are manipulating pointers in-place.
     */
    private static Node reverse(Node head) {
        // Base case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }

        Node current = head;
        Node prev = null; // Will eventually hold the new head

        // Traverse the list and swap prev/next pointers for each node
        while (current != null) {
            prev = current.prev;             // Store reference to previous node
            current.prev = current.next;     // Swap prev to point to next
            current.next = prev;             // Swap next to point to prev

            current = current.prev;          // Move to the "next" node (which is now in prev)
        }

        // After the loop, current is null. prev points to the second-to-last node 
        // (which was the second node of the original list). The new head is prev.prev.
        return prev.prev;
    }

    private static void traverseDLL(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + (temp.next != null ? " <-> " : ""));
            temp = temp.next;
        }
        System.out.println();
    }

    /**
     * Converts a primitive integer array into a Doubly Linked List.
     * Time: O(N) | Space: O(N) to create the N nodes.
     */
    private static Node convertArrToDLL(int[] arr) {
        if (arr.length == 0) return null;

        Node head = new Node(arr[0]);
        Node prev = head;

        for (int i = 1; i < arr.length; i++) {
            Node temp = new Node(arr[i], null, prev);
            prev.next = temp;
            prev = temp;
        }
        return head;
    }

    /**
     * ==========================================================================================
     * Phase 2: Insertion Operations
     * ==========================================================================================
     *
     * Detailed Intuition:
     * DLL insertions require carefully updating up to 4 pointers (prev and next for the new 
     * node, next for the previous node, and prev for the forward node). Order of operations 
     * matters to prevent losing the chain.
     *
     * Complexity Analysis:
     * - Head Insertion: Time O(1) | Space O(1)
     * - Tail Insertion: Time O(N) (needs traversal) | Space O(1)
     * - K-th Insertion: Time O(K) | Space O(1)
     */

    private static Node insertHead(Node head, int data) {
        if (head == null) return new Node(data);

        Node newNode = new Node(data, head, null);
        head.prev = newNode;
        return newNode; // The new node is the new head
    }

    private static Node insertTail(Node head, int data) {
        if (head == null) {
            return insertHead(head, data);
        }

        Node tail = head;
        // Traverse to find the exact last node
        while (tail.next != null) {
            tail = tail.next;
        }

        // BUG FIX: Properly append to the tail, instead of inserting before it
        Node newNode = new Node(data, null, tail);
        tail.next = newNode;
        return head;
    }

    private static Node insertBeforeKthElement(Node head, int k, int data) {
        if (k == 1) {
            return insertHead(head, data);
        }

        Node temp = head;
        int count = 0;

        // Traverse to find the K-th element
        while (temp != null) {
            count++;
            if (count == k) {
                break;
            }
            temp = temp.next;
        }

        // Edge Case Handling: If k is greater than list length
        if (temp == null) {
            System.out.println("Position out of bounds. Appending to tail.");
            return insertTail(head, data);
        }

        Node prev = temp.prev;
        Node newNode = new Node(data, temp, prev);

        // Re-wire the surrounding nodes
        prev.next = newNode;
        temp.prev = newNode;
        return head;
    }

    /**
     * ==========================================================================================
     * Phase 3: Deletion Operations
     * ==========================================================================================
     *
     * Detailed Intuition:
     * Deleting a node in a DLL is significantly easier than a Singly Linked List because 
     * we have the `prev` pointer. We just bypass the target node by linking `prev` directly 
     * to `next`, and sever the target node's links for Garbage Collection.
     *
     * Complexity Analysis:
     * - Head Deletion: Time O(1) | Space O(1)
     * - Tail Deletion: Time O(N) | Space O(1)
     * - K-th Deletion: Time O(K) | Space O(1)
     */

    public static Node deleteAllOccurrences(Node head, int value) {
        if (head == null) {
            return null;
        }

        Node temp = head;

        while (temp != null) {
            // Store the next node BEFORE we potentially delete 'temp'
            Node nextNode = temp.next;

            if (temp.data == value) {

                if (temp == head) {
                    // Case 1: The node to delete is the head
                    head = nextNode;
                    if (head != null) {
                        head.prev = null;
                    }
                } else {
                    // Case 2: The node is in the middle or at the tail
                    temp.prev.next = nextNode;
                    if (nextNode != null) {
                        nextNode.prev = temp.prev;
                    }
                }

                // Isolate the node completely for the Garbage Collector
                temp.next = null;
                temp.prev = null;
            }

            // Advance to the next node using our safely stored reference
            temp = nextNode;
        }

        return head;
    }
    private static Node deleteHead(Node head) {
        // Base case: empty list or single node list
        if (head == null || head.next == null) {
            return null;
        }

        Node prev = head;
        head = head.next; // Shift head forward
        head.prev = null; // Sever reverse tie
        prev.next = null; // Sever forward tie (allow GC to clean up)

        return head;
    }

    private static Node deleteTail(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        Node tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }

        Node newTail = tail.prev;
        newTail.next = null; // Disconnect the old tail
        tail.prev = null;    // Disconnect old tail from list

        return head;
    }

    private static Node deleteKElement(Node head, int k) {
        if (head == null) return null;

        int count = 0;
        Node KNode = head;

        // Traverse to find the K-th node
        while (KNode != null) {
            count++;
            if (count == k) {
                break;
            }
            KNode = KNode.next;
        }

        // If K is greater than the length of the list, do nothing
        if (KNode == null) return head;

        Node prev = KNode.prev;
        Node front = KNode.next;

        // Condition 1: Deleting a single-element list
        if (prev == null && front == null) {
            return null;
        }
        // Condition 2: Deleting the head
        else if (prev == null) {
            return deleteHead(head);
        }
        // Condition 3: Deleting the tail
        else if (front == null) {
            return deleteTail(head);
        }

        // Condition 4: Deleting a node in the middle
        prev.next = front;
        front.prev = prev;

        // Sever ties for Garbage Collection
        KNode.next = null;
        KNode.prev = null;

        return head;
    }

    /**
     * Deletes a specific node provided by reference. 
     * Useful when you have the direct pointer and want an O(1) deletion.
     */
    private static void deleteNode(Node temp) {
        if (temp == null) return;

        Node prev = temp.prev;
        Node front = temp.next;

        // If it's the tail node
        if (front == null) {
            if (prev != null) prev.next = null;
            temp.prev = null;
            return;
        }

        // Standard middle deletion
        if (prev != null) prev.next = front;
        front.prev = prev;

        temp.next = null;
        temp.prev = null;
    }
}

/**
 * Node class representing a single element in the Doubly Linked List.
 */
class Node {
    int data;     // Data stored in the node
    Node next;    // Pointer to the next node in the sequence
    Node prev;    // Pointer to the previous node in the sequence

    /**
     * Full constructor for initialization.
     * BUG FIX: Properly mapped the 'next' and 'prev' arguments to 'this.next' and 'this.prev'.
     */
    Node(int data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    /**
     * Simplified constructor for isolated nodes or the tail.
     */
    Node(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}