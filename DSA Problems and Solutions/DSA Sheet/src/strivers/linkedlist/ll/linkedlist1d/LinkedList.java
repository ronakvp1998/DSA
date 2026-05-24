package strivers.linkedlist.ll.linkedlist1d;

/**
 * Node class representing a single element in the Linked List.
 */
class Node {
    int data;     // Stores the value of the node
    Node next;    // Reference pointer to the next node in the list

    // Constructor to initialize a node with both data and next pointer
    Node(int data1, Node next1){
        this.data = data1;
        this.next = next1;
    }

    // Constructor to initialize a node with only data (next defaults to null)
    Node(int data){
        this.data = data;
        this.next = null;
    }
}

public class LinkedList {

    public static void main(String[] args) {
        int arr[] = {2, 5, 4, 6, 2, 9, 8, 4, 4};

        // 1. Convert array to Linked List
        Node head = arrayToLinkedList(arr);
        System.out.print("Original List: ");
        printLL(head);

        // 2. Reverse the Linked List
        head = reverse(head);
        System.out.print("Reversed List: ");
        printLL(head);

        // Example of other operations (Uncomment to test):
        // System.out.println("Index of 9: " + searchElement(head, 9));
        // head = insertAtIndex(head, 2, 99);
        // printLL(head);
        // head = deleteAllOccValue(head, 4);
        // printLL(head);
    }

    /**
     * Reverses the Linked List iteratively.
     * Approach: Use three pointers (prev, curr, next) to reverse the links one by one.
     */
    private static Node reverse(Node head){
        Node prev = null;       // Points to the reversed portion
        Node curr = head;       // Current node being processed
        Node next = null;       // Temporarily stores the remaining list

        while (curr != null){
            next = curr.next;   // 1. Save the next node
            curr.next = prev;   // 2. Reverse the current node's pointer
            prev = curr;        // 3. Move prev one step forward
            curr = next;        // 4. Move curr one step forward
        }
        return prev;            // prev becomes the new head
    }

    /**
     * Inserts a new node at the end of the Linked List.
     */
    private static Node insertLast(Node head, int value){
        // Edge case: If the list is empty, inserting at last is same as inserting at head
        if(head == null){
            return insertAtHead(head, value);
        }

        Node temp = head;
        // Traverse until the last node (temp.next == null)
        while (temp.next != null){
            temp = temp.next;
        }

        // Create the new node and link the last node to it
        Node newNode = new Node(value);
        temp.next = newNode;
        return head;
    }

    /**
     * Inserts a new node at a specific zero-based index.
     * BUG FIX: Fixed infinite loop and incorrect conditional logic.
     */
    private static Node insertAtIndex(Node head, int index, int value){
        // Edge case: empty list but index is not 0
        if(head == null && index != 0){
            return null;
        }
        // If inserting at the very beginning
        if(index == 0){
            return insertAtHead(head, value);
        }

        Node temp = head;
        Node prev = null;
        int count = 0;

        // Traverse to find the exact index
        while (temp != null){
            if(count == index){
                Node newNode = new Node(value);
                prev.next = newNode;  // Connect previous node to new node
                newNode.next = temp;  // Connect new node to the current node
                return head;          // Return early since insertion is done
            }
            prev = temp;
            temp = temp.next;
            count++;                  // BUG FIX: Increment count!
        }

        // Edge case: Inserting exactly at the end of the list
        if (count == index) {
            prev.next = new Node(value);
        }

        return head;
    }

    /**
     * Inserts a new node at the beginning of the Linked List.
     */
    private static Node insertAtHead(Node head, int value){
        // Create new node pointing to the current head
        Node newNode = new Node(value, head);
        return newNode; // The new node becomes the new head
    }

    /**
     * Deletes all nodes that contain a specific value.
     */
    private static Node deleteAllOccValue(Node head, int value){
        if(head == null) return null;

        // 1. Handle cases where the target value is at the head
        while (head != null && head.data == value){
            head = head.next;
        }

        // 2. Handle the rest of the list
        Node temp = head;
        Node prev = null;
        while (temp != null){
            if(temp.data == value){
                prev.next = temp.next; // Bypass the node to delete it
                temp = temp.next;      // Move temp forward
            } else {
                prev = temp;           // Move prev forward
                temp = temp.next;      // Move temp forward
            }
        }
        return head;
    }

    /**
     * Deletes the first occurrence of a specific value.
     */
    private static Node deleteValue(Node head, int value){
        if(head == null) return null;

        Node temp = head;
        Node prev = null;

        while (temp != null){
            // Case 1: The value is at the head node
            if(temp.data == value && prev == null){
                head = head.next;
                break;
            }
            // Case 2: The value is somewhere in the middle or end
            else if(temp.data == value){
                prev.next = temp.next;
                break; // Stop after deleting the *first* occurrence
            }
            prev = temp;
            temp = temp.next;
        }
        return head;
    }

    /**
     * Deletes the last node of the Linked List.
     */
    private static Node deleteLast(Node head){
        // Edge case: Empty list or list with only one node
        if(head == null || head.next == null){
            return null;
        }

        Node temp = head;
        // Stop at the second-to-last node
        while (temp.next.next != null){
            temp = temp.next;
        }
        // Sever the link to the last node
        temp.next = null;
        return head;
    }

    /**
     * Deletes a node at a specific zero-based index.
     */
    private static Node deleteIndex(Node head, int index){
        if (head == null) return null;

        if(index == 0){
            return deleteFirst(head);
        }

        Node temp = head;
        Node prev = null;
        int count = 0;

        while (temp != null){
            if(count == index){
                prev.next = temp.next; // Bypass the node at the index
                break;
            }
            count++;
            prev = temp;
            temp = temp.next;
        }
        return head;
    }

    /**
     * Deletes the first node (head) of the Linked List.
     */
    private static Node deleteFirst(Node head){
        if(head == null || head.next == null){
            return null;
        }
        return head.next; // Simply return the second node
    }

    /**
     * Searches for a target value and returns its zero-based index.
     * Returns -1 if not found.
     */
    private static int searchElement(Node head, int target){
        if(head == null) return -1;

        Node temp = head;
        int count = 0;

        while (temp != null){
            if(temp.data == target){
                return count; // Target found
            }
            temp = temp.next;
            count++;
        }
        return -1; // Target not found
    }

    /**
     * Traverses and prints the Linked List.
     */
    private static void printLL(Node head){
        if(head == null ){
            System.out.println("Empty LL");
            return;
        }
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    /**
     * Converts an array into a Singly Linked List.
     */
    private static Node arrayToLinkedList(int arr[]){
        if(arr.length <= 0) return null;

        Node head = new Node(arr[0]);
        Node temp = head;

        // Loop through the array, creating nodes and linking them
        for(int i = 1; i < arr.length; i++){
            Node newNode = new Node(arr[i]);
            temp.next = newNode; // Link current node to the new node
            temp = newNode;      // Move the pointer forward
        }
        return head;
    }
}